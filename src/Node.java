import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import org.graphstream.algorithm.Toolkit;

class Node extends Thread {
	
	String thisNode = Thread.currentThread().getName();	//nom du thread courant
	List<Thread> neighbors = new ArrayList<Thread>();	//liste des voisin
	Queue<Message> messageQueue = new LinkedList<Message>();	//file d'attente FIFO de message
	Message message;	//message a traiter
	List<String> dataToIntegrate = new ArrayList<String>();	//transaction a integré dans le prochain bloc
	List<String> dataInIntegration = new ArrayList<String>();	//transaction en cours d'integration dans le bloc courant
	List<Block> blockchain = new ArrayList<Block>();	//chaine de bloc
	
	Thread getThread(String name) {	//retourne un thread correspondant au nom passer en parametre
		Thread[] threads = new Thread[Initialisation.nodesGroup.activeCount()];
		Initialisation.nodesGroup.enumerate(threads);
		for(Thread thread : threads) {
			if(thread.getName() == name) {
				return thread;
			}
		}
		return null;
	}
	
	Thread GetRandomNeighbor() {	//retourne un thread voisin aleatoire
		return neighbors.get(ThreadLocalRandom.current().nextInt(neighbors.size()));
	}
	
	void postMessage(Message message) {	//ajoute les message envoyer par les autre thread dans la file d'attente messageQueue
		messageQueue.add(message);
	}
	
	public void run() {
		blockchain.add(new Block(null, "", "0000000000000000000000000000000000000000000000000000000000000000"));	//genesis block
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
			while(DiffuseBlock.GetBlock() != null) {	//tant que le noeud ne possede pas tous les bloc de la blockchain, il les demande consecutivement parmis ses voisins de maniere aleatoire
				((Node) GetRandomNeighbor()).postMessage(new RequestBlock(thisNode, "sync", blockchain.size() - 1));
			}
		}
		while(true) {
			while((message = messageQueue.poll()) != null) {	//traite les message en file d'attente
				switch(message.GetFlag()) {
					case "sync" : {
						for(Thread neighbor : neighbors) {
							if(message.GetSender() == neighbor.getName()) {
								//envoyer bloc
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
				}
			}
			//miner bloc et generer transaction
		}
	}
}