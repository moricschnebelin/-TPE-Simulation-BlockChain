package main;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Simulation {
	
	static Graph blockchain = Initialisation.GetBlockChain();
	
	static String GenerateId() {
		return LocalDate.now(Clock.systemUTC()).toString().concat(":"+LocalTime.now(Clock.systemUTC()).toString()).replaceAll("[-.]", ":"); // annee:mois:jour:heure:minute:seconde:milliseconde
	}
	
	/*static void Disconnect(int numberOfNodes) throws InterruptedException {
		Node randomNode = Toolkit.randomNode(blockchain);
		for(Edge edge : randomNode) {
			Node neighborNode = edge.getOpposite(randomNode);
			Node randomNeighborNode;
			do {
				randomNeighborNode = Toolkit.randomNode(blockchain);
			} while(randomNeighborNode == randomNode || randomNeighborNode == neighborNode);
			blockchain.addEdge(GenerateId(), neighborNode, randomNeighborNode);
			TimeUnit.MILLISECONDS.sleep(1000); // attends 1 ms pour avoir un id différent pour chaque noeuds
		}
		blockchain.removeNode(randomNode);
	}*/
	
	static void Disconnect(int numberOfNodes) throws InterruptedException {
		Node randomNode = Toolkit.randomNode(blockchain);
		for(Edge edge : randomNode) {
			Node neighborNode = edge.getOpposite(randomNode);
			Node randomNeighborNode;
			do {
				randomNeighborNode = Toolkit.randomNode(blockchain);
			} while(randomNeighborNode == randomNode || randomNeighborNode == neighborNode);

			// a faire : evolution constante du nb d'edges
			if(!neighborNode.hasEdgeFrom(randomNeighborNode)){
				blockchain.addEdge(GenerateId(), neighborNode, randomNeighborNode);
				TimeUnit.MILLISECONDS.sleep(1); // attends 1 ms pour avoir un id différent pour chaque noeuds
			}
		}
		blockchain.removeNode(randomNode);
	}
	
	static void Connect(int numberOfNodes) throws InterruptedException {
		Node randomNode1 = Toolkit.randomNode(blockchain);
		Node randomNode2 = Toolkit.randomNode(blockchain);
		blockchain.addNode(GenerateId()).addAttribute("ui.label", "(" + GenerateId() + ")");
		Node newNode = blockchain.getNode(GenerateId());

		blockchain.addEdge(GenerateId(), newNode, randomNode1);
		TimeUnit.MILLISECONDS.sleep(1);
		blockchain.addEdge(GenerateId(), newNode, randomNode2);
		
	}
}