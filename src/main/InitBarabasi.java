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
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;

public class InitBarabasi {
	
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

		// entre 1 et 3 liens par noeuds ajoutes
		Generator gen = new BarabasiAlbertGenerator(3);
		gen.addSink(blockchain); 
		gen.begin();
		for(int i=0; i<numberOfNodes; i++) {
			gen.nextEvents();
		}
		gen.end();
		
	    for(int i = 0; i <= numberOfNodes+1; i++) {
	    	Node n = blockchain.getNode(i);
	    	n.addAttribute("ui.label", "(" + GenerateId() + ")");
	    	TimeUnit.MILLISECONDS.sleep(1);
	    }
		
	}
	
	static Graph GetBlockChain() {	//recuperation du graph
		return blockchain;
	}
}