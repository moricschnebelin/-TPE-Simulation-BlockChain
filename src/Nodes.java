import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import org.graphstream.algorithm.Toolkit;

public class Nodes {
	
	static class Node implements Runnable {
		
		String thisNode = Thread.currentThread().getName();
		BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();
		Message message;
		List<String> neighbors = new ArrayList<String>();
		List<Block> blockchain = new ArrayList<Block>();
		
		String GetRandomNeighbor() {
			return neighbors.get(ThreadLocalRandom.current().nextInt(neighbors.size()));
		}
		
		
		
		public void run() {
			blockchain.add(new Block("","0000000000000000000000000000000000000000000000000000000000000000"));	//genesis block
			if(neighbors.size() == 0) {	//partie du code ignorer par les noeuds initiaux
				while(neighbors.size() < 2) {	//tant que le noeud ne possede pas n liaison, il les cree avec d'autre noeud aleatoire du reseau
					String randomNode = Toolkit.randomNode(Initialisation.blockchain).getId();
					if(randomNode != thisNode) {
						boolean validNode = true;
						for(String neighbor : neighbors) {
							if(randomNode == neighbor) {
								validNode = false;
								break;
							}
						}
						if(validNode == true) {
							neighbors.add(randomNode);
							Initialisation.blockchain.addEdge(Initialisation.GenerateId(), thisNode, randomNode);
						}
					}
				}
				/*while(DiffuseBlock.GetBlock() != null) {	//tant que le noeud ne possede pas tous les bloc de la blockchain, il les demande consecutivement parmis ses voisins de maniere aleatoire
					for(Thread thread :)
						thread(GetRandomNeighbor()).messageQueue.put(new RequestBlock(thisNode, "sync", blockchain.size() - 1));
						
				}*/
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