import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.graphstream.algorithm.Toolkit;

public class Nodes {
	
	static class Node implements Runnable {
		
		BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();
		Message message;
		List<String> neighbors = new ArrayList<String>();
		List<Block> blockchain = new ArrayList<Block>();
		
		public void run() {
			blockchain.add(new Block("","0000000000000000000000000000000000000000000000000000000000000000"));	//genesis bloc
			if(neighbors.size() == 0) {	//partie de code ignorer par les noeuds initiaux
				while(neighbors.size() < 2) {	//tant que le noeud n'a pas 2 liaison il cree des lien aleatoire
					Toolkit.randomNode(Initialisation.blockchain);
				}
				while() {	//tant que le noeud na pas tous les bloc, demander bloc aux voisin
					
				}
			}
			while(true) {
				while((message = messageQueue.poll()) != null) {	//si il y a des message en attente
					switch(message.GetFlag()) {
						case "sync" : {
							for(String neighbor : neighbors) {
								if(message.GetSender() == neighbor) {
									//envoyer bloc
								}
							}
						}
						case "data" : {
							for(String neighbor : neighbors) {
								if(message.GetSender() == neighbor) {
									//diffuser transaction
								}
							}
						}
						case "bloc" : {
							for(String neighbor : neighbors) {
								if(message.GetSender() == neighbor) {
									//diffuser bloc
								}
							}
						}
						case "link" : {
							//cree lien
						}
					}
				}
				//miner et generer transaction
				
			}
		}
		
	}
	
}