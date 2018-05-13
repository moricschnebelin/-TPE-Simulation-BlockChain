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

import javax.xml.bind.DatatypeConverter;

import org.graphstream.algorithm.Toolkit;

class Node extends Thread {
	
	String thisNode = Thread.currentThread().getName();	//nom du thread courant
	List<Node> neighbors = new ArrayList<Node>();	//liste des voisin
	Queue<Message> messageQueue = new LinkedList<Message>();	//file d'attente FIFO de message
	Message message;	//message a traiter
	List<Message> waitReply = new ArrayList<Message>();	//liste de message qui attende une reponse "sync"
	List<String> dataToArchive = new ArrayList<String>();	//transaction a integré dans le prochain bloc
	List<String> dataInArchiving = new ArrayList<String>();	//transaction en cours d'integration dans le bloc courant
	List<Block> blockchain = new ArrayList<Block>();	//chaine de bloc
	
	Node getThread(String name) {	//retourne un thread correspondant au nom passer en parametre
		Thread[] threads = new Thread[Initialisation.nodesGroup.activeCount()];
		Initialisation.nodesGroup.enumerate(threads);
		for(Thread thread : threads) {
			if(thread.getName() == name) {
				return (Node)(thread);
			}
		}
		return null;
	}
	
	Node GetRandomNeighbor() {	//retourne un thread voisin aleatoire
		return (Node)(neighbors.get(ThreadLocalRandom.current().nextInt(neighbors.size())));
	}
	
	void postMessage(Message message) {	//ajoute les message envoyer par les autre thread dans la file d'attente messageQueue
		messageQueue.add(message);
	}
	
	public void run() {
		blockchain.add(new Block(null, "", "00000000000000000000000000000000"));	//genesis block
		if(neighbors.size() == 0) {	//partie de code ignorer par les noeuds initiaux
			while(neighbors.size() < 2) {	//tant que le noeud ne possede pas n liaison, il les cree avec d'autre noeud aleatoire du reseau
				String randomNode = Toolkit.randomNode(Initialisation.blockchain).getId();
				if(randomNode != thisNode) {
					boolean validNode = true;
					for(Thread neighbor : neighbors) {
						if(randomNode == neighbor.getName()) {
							validNode = false;
							break;
						}
					}
					if(validNode == true) {
						neighbors.add(getThread(randomNode));
						Initialisation.blockchain.addEdge(Initialisation.GenerateId(), thisNode, randomNode);
					}
				}
			}
			while(true) {	//tant que le noeud ne possede pas tous les bloc de la blockchain, il les demande consecutivement parmis ses voisins de maniere aleatoire
				Node randomNeighbor = GetRandomNeighbor();
				randomNeighbor.postMessage(new Requ(thisNode, randomNeighbor.getName(), blockchain.size()));
				while((message = messageQueue.poll()) != null) {	//traite les message en file d'attente
					switch(message.GetFlag()) {
						case "sync" : {
							for(Node neighbor : neighbors) {
								if(message.GetSender() == neighbor.getName()) {
									//aquitter message
								}
							}
							break;
						}
						case "data" : {	//diffuser la transaction a ces voisin sauf celui qui en est l'emeteur
							for(Node neighbor : neighbors) {
								if(((Data)(message)).GetSender() == neighbor.getName()) {
									for(Node neighbour : neighbors) {
										if(neighbour != neighbor) {
											neighbour.postMessage(new Data(thisNode, neighbour.getName(), ((Data)(message)).GetData()));
										}
									}
								}
							}
							break;
						}
						case "bloc" : {	//diffuser le bloc a ces voisin sauf celui qui en est l'emeteur
							for(Node neighbor : neighbors) {
								if(((Bloc)(message)).GetSender() == neighbor.getName()) {
									for(Node neighbour : neighbors) {
										if(neighbour != neighbor) {
											neighbour.postMessage(new Bloc(thisNode, neighbour.getName(), ((Bloc)(message)).GetBlock()));
										}
									}
								}
							}
							break;
						}
						case "link" : {	//ignorer ces message
							//ne rien faire
							break;
						}
						case "give" : {	//ajouter le bloc a la blockchain
							String blocDigest = "";
							String hash = null;
							boolean validHash = true;
							for(Node neighbor : neighbors) {
								if(((Give)(message)).GetSender() == neighbor.getName()) {
									blocDigest.concat(((Give)(message)).GetBlock().nonce.toString());
									blocDigest.concat(((Give)(message)).GetBlock().data);
									blocDigest.concat(blockchain.get(blockchain.size() - 1).hash);
									try {
										hash = MessageDigest.getInstance("MD5").digest(blocDigest.getBytes("UTF-8")).toString();
									} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									for(int header = 0; header < 4; header ++) {
										if(hash.toCharArray()[header] != 0) {
											validHash = false;
											break;
										}
									}
									if(validHash == true) {
										blockchain.add(((Give)(message)).GetBlock());
									}
								}
							}
							break;
						}
						case "requ" : {	//ignorer ces message
							//ne rien faire
							break;
						}
					}
				}
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