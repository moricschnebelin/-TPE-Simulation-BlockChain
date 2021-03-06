package main;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class InitRandom {
	
	static Graph blockchain = new SingleGraph("Blockchain Network");
	static ThreadGroup nodesGroup = new ThreadGroup("Nodes Group");
	
	int sommets = blockchain.getNodeCount();
	static ArrayList<Double> dist = new ArrayList<Double>();
	double distMoy = 0.0;
	double distTotale = 0.0;
	
	static String GenerateId() {
		return LocalDate.now(Clock.systemUTC()).toString().concat(":"+LocalTime.now(Clock.systemUTC()).toString()).replaceAll("[-.]", ":"); // annee:mois:jour:heure:minute:seconde:milliseconde
	}
	
	static void PrimeGraph(int numberOfNodes) throws InterruptedException {	//initialisation du graph avec un noeud primaire
		//création des noeuds
		for(int i = 0; i < numberOfNodes; i++) {

			blockchain.addNode(GenerateId()).addAttribute("ui.label", i + "(" + GenerateId() + ")"); // annee:mois:jour:heure:minute:seconde:milliseconde
			
			TimeUnit.MILLISECONDS.sleep(1); // attends 1 ms pour avoir un label différent pour chaque noeuds
		}
		
		//liaisons random entre chaque noeud
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
					blockchain.addEdge(GenerateId(), n, nRand);
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