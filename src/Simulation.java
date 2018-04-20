import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Simulation {
	
	Graph blockchain = Initialisation.GetBlockChain();
	
	void Disconnect(int numberOfNodes) {
		Node randomNode = Toolkit.randomNode(blockchain);
		for(Edge edge : randomNode) {
			Node neighborNode = edge.getOpposite(randomNode);
			Node randomNeighborNode;
			do {
				randomNeighborNode = Toolkit.randomNode(blockchain);
			} while(randomNeighborNode == randomNode);
			blockchain.addEdge(null, neighborNode, randomNeighborNode);
		}
		blockchain.removeNode(randomNode);
	}
	
}