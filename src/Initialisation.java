import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class Initialisation {
	
	static Graph blockchain = new SingleGraph("Blockchain Network");
	
	static String GenerateId() {
		return LocalDate.now(Clock.systemUTC()).toString().concat(":"+LocalTime.now(Clock.systemUTC()).toString()).replaceAll("[-.]", ":"); // annee:mois:jour:heure:minute:seconde:milliseconde
	}
	
	static void PrimeGraph(int numberOfNodes) throws InterruptedException {	//initialisation du graph avec un noeud primaire
		
		//création des noeuds
		for(int i = 0; i < numberOfNodes; i++) {

			blockchain.addNode(LocalDate.now(Clock.systemUTC()).toString().concat(":"+LocalTime.now(Clock.systemUTC()).toString()).replaceAll("[-.]", ":")); // annee:mois:jour:heure:minute:seconde:milliseconde
			
			TimeUnit.MILLISECONDS.sleep(1); // attends 1 ms pour avoir un id différent pour chaque noeuds
		}
		
		//liaisons random entre chaques noeuds
		for(Node n : blockchain){
			int i = 0;
			while(i != 2) {
				boolean isvoisin = false;
				Node nRand = Toolkit.randomNode(blockchain);
				for(Edge e : n) {
					Node voisin = e.getOpposite(n);
					if(nRand == voisin) {
						isvoisin = true;
					}
					if(isvoisin)
						break;
				}
				if(nRand != n && isvoisin == false) {
					blockchain.addEdge(LocalDate.now(Clock.systemUTC()).toString().concat(":"+LocalTime.now(Clock.systemUTC()).toString()).replaceAll("[-.]", ":"), n, nRand);
					i++;
					TimeUnit.MILLISECONDS.sleep(1); //millisecondes
				}
			}
		}
		
	}
	
	static Graph GetBlockChain() {	//recuperation du graph
		return blockchain;
	}
}