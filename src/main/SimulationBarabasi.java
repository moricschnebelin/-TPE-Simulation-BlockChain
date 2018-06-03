package main;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class SimulationBarabasi {

	static Graph blockchain = InitBarabasi.GetBlockChain();
	
	static String GenerateId() {
		return LocalDate.now(Clock.systemUTC()).toString().concat(":"+LocalTime.now(Clock.systemUTC()).toString()).replaceAll("[-.]", ":"); // annee:mois:jour:heure:minute:seconde:milliseconde
	}
	
	static void Disconnect(int numberOfNodes) throws InterruptedException {
		Node randomNode = Toolkit.randomNode(blockchain);
		
		for(Edge edge : randomNode) {
			Node neighborNode = edge.getOpposite(randomNode);
			Node randomNeighborNode;
			do {
				// selectionne un voisin autre que le noeud deco et voisin
				randomNeighborNode = Toolkit.randomNode(blockchain);
			} while(randomNeighborNode == randomNode || randomNeighborNode == neighborNode);
			
			// reconnecte deux voisins random  du noeud deco entre eux
			if(!neighborNode.hasEdgeFrom(randomNeighborNode)){
				blockchain.addEdge(GenerateId(), neighborNode, randomNeighborNode);
				TimeUnit.MILLISECONDS.sleep(1); // attends 1 ms pour avoir un id différent pour chaque noeuds
			}
		}
		blockchain.removeNode(randomNode);
	}
	
	static void Connect(int numberOfNodes) throws InterruptedException {
		
		String id_newNode = GenerateId();
		Node randomNode1 = Toolkit.randomNode(blockchain);
		
		blockchain.addNode(id_newNode).addAttribute("ui.label", "(" + id_newNode + ")");
		Node newNode = blockchain.getNode(id_newNode);
		
		blockchain.addEdge(GenerateId(), newNode, randomNode1);
		TimeUnit.MILLISECONDS.sleep(1);
		
	}
}