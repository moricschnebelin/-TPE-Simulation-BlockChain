package main;

import message.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import org.graphstream.algorithm.Toolkit;

class Node extends Thread {
	
	String thisNode = Thread.currentThread().getName();	//nom du thread courant
	List<Node> neighbors = new ArrayList<Node>();	//liste des voisins
	Queue<Message> messageQueue = new LinkedList<Message>();	//file d'attente FIFO de message
	Message message;	//message a traiter
	List<Message> awaitReply = new ArrayList<Message>();	//liste de message qui attende une reponse "sync"
	List<String> dataToArchive = new ArrayList<String>();	//transaction a integrer dans le prochain bloc
	List<String> dataInArchiving = new ArrayList<String>();	//transaction en cours d'integration dans le bloc courant
	List<Block> blockchain = new ArrayList<Block>();	//chaine de bloc
	
	Node GetNode(String name) {	//retourne un noeud correspondant au nom passé en parametre
		Thread[] threads = new Thread[InitRandom.nodesGroup.activeCount()];
		InitRandom.nodesGroup.enumerate(threads);
		for(Thread thread : threads) {
			if(thread.getName() == name) {
				return (Node)(thread);
			}
		}
		return null;
	}
	
	Node GetRandomNeighbor() {	//retourne un noeud voisin aleatoire
		return (Node)(neighbors.get(ThreadLocalRandom.current().nextInt(neighbors.size())));
	}
	
	void PostMessage(Message message) {	//ajoute les message envoyer par les autres threads dans la file d'attente messageQueue
		messageQueue.add(message);
	}
	
	boolean ValidHash(String blocDigest) {	//verifie la validiter du hash, retourne true si valide, false sinon
		String hash = null;
		try {
			hash = MessageDigest.getInstance("MD5").digest(blocDigest.getBytes("UTF-8")).toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int header = 0; header < 4; header ++) {
			if(hash.toCharArray()[header] != 0) {
				return false;
			}
		}
		return true;
	}
	
	public void run() {
		blockchain.add(new Block(null, "", "00000000000000000000000000000000"));	//genesis block
		if(neighbors.size() == 0) {	//partie de code ignorer par les noeuds initiaux
			while(neighbors.size() < 2) {	//tant que le noeud ne possede pas n liaison, il les cree avec d'autre noeud aleatoire du reseau
				String randomNode = Toolkit.randomNode(InitRandom.blockchain).getId();
				if(randomNode != thisNode) {
					boolean validNode = true;
					for(Thread neighbor : neighbors) {
						if(randomNode == neighbor.getName()) {
							validNode = false;
							break;
						}
					}
					if(validNode == true) {
						neighbors.add(GetNode(randomNode));
						InitRandom.blockchain.addEdge(InitRandom.GenerateId(), thisNode, randomNode);
					}
				}
			}
			while(true) {	//tant que le noeud ne possedent pas tous les blocs de la blockchain, il les demande consecutivement parmis ses voisins de maniere aleatoire
				Node randomNeighbor = GetRandomNeighbor();
				randomNeighbor.PostMessage(new Requ(thisNode, randomNeighbor.getName(), blockchain.size()));
				awaitReply.add(new Requ(thisNode, randomNeighbor.getName(), blockchain.size()));
				while((message = messageQueue.poll()) != null) {	//traite les message en file d'attente
					switch(message.GetFlag()) {
						case "sync" : {
							for(Node neighbor : neighbors) {	//aquitter le message ayant une reponse
								if(((Sync)(message)).GetSender() == neighbor.getName()) {
									for(Message waitReply : awaitReply) {
										if(waitReply.GetReceiver() == ((Sync)(message)).GetSender()) {
											awaitReply.remove(waitReply);
										}
									}
								}
							}
							break;
						}
						case "data" : {	//diffuser la transaction a ces voisin sauf celui qui en est l'emeteur
							for(Node neighbor : neighbors) {
								if(((Data)(message)).GetSender() == neighbor.getName()) {
									for(Node neighbour : neighbors) {
										if(neighbour != neighbor) {
											neighbour.PostMessage(new Data(thisNode, neighbour.getName(), ((Data)(message)).GetData()));
											awaitReply.add(new Data(thisNode, neighbour.getName(), ((Data)(message)).GetData()));
										}
									}
									GetNode(((Data)(message)).GetSender()).PostMessage(new Sync(thisNode, ((Data)(message)).GetSender()));	//envoi de la reponse au message
								}
							}
							break;
						}
						case "bloc" : {	//diffuser le bloc a ces voisin sauf celui qui en est l'emeteur
							for(Node neighbor : neighbors) {
								if(((Bloc)(message)).GetSender() == neighbor.getName()) {	//si le message provient d'un noeud voisin alors on le traite
									String blocDigest = "";
									blocDigest.concat(((Bloc)(message)).GetBlock().nonce.toString());
									blocDigest.concat(((Bloc)(message)).GetBlock().data);
									blocDigest.concat(blockchain.get(blockchain.size() - 1).hash);
									if(ValidHash(blocDigest) == true) {	//validation du bloc
										for(Node neighbour : neighbors) {
											if(neighbour != neighbor) {
												neighbour.PostMessage(new Bloc(thisNode, neighbour.getName(), ((Bloc)(message)).GetBlock()));	//transfer le bloc aux noeud voisins sauf celui qui en est l'emetteur
												awaitReply.add(new Bloc(thisNode, neighbour.getName(), ((Bloc)(message)).GetBlock()));	//ajout du message a la liste d'attente de reponse
											}
										}
										GetNode(((Bloc)(message)).GetSender()).PostMessage(new Sync(thisNode, ((Bloc)(message)).GetSender()));	//envoi de la reponse message au noeud emetteur
									}
								}
							}
							break;
						}
						case "link" : {	//ignorer ce type de message
							//ne rien faire
							break;
						}
						case "give" : {	//ajouter le bloc a la blockchain
							String blocDigest = "";
							for(Node neighbor : neighbors) {
								if(((Give)(message)).GetSender() == neighbor.getName()) {
									blocDigest.concat(((Give)(message)).GetBlock().nonce.toString());
									blocDigest.concat(((Give)(message)).GetBlock().data);
									blocDigest.concat(blockchain.get(blockchain.size() - 1).hash);
									if(ValidHash(blocDigest) == true) {
										blockchain.add(((Give)(message)).GetBlock());
										for(Message waitReply : awaitReply) {
											if(waitReply.GetReceiver() == ((Give)(message)).GetSender() && waitReply.GetFlag() == "requ") {
												awaitReply.remove(waitReply);
											}
										}
									}
								}
							}
							break;
						}
						case "requ" : {	//ignorer ce type de message
							//ne rien faire
							break;
						}
					}
				}
				//verifier liste d'attente reponse et suprimmer les lien avec les noeud qui ne reponde plus
			}
		}
		while(true) {
			while((message = messageQueue.poll()) != null) {	//traite les message en file d'attente
				switch(message.GetFlag()) {
					case "sync" : {
						for(Thread neighbor : neighbors) {
							if(message.GetSender() == neighbor.getName()) {
								//aquitter message
							}
						}
					}
					case "data" : {
						for(Thread neighbor : neighbors) {
							if(message.GetSender() == neighbor.getName()) {
								//diffuser transaction
							}
						}
					}
					case "bloc" : {
						for(Thread neighbor : neighbors) {
							if(message.GetSender() == neighbor.getName()) {
								//diffuser bloc
							}
						}
					}
					case "link" : {
						//cree lien
					}
					case "init" : {
						//transferer bloc demander
					}
				}
			}
			//miner bloc et generer transaction
		}
	}
}