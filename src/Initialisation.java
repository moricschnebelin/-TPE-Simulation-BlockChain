import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class Initialisation {
	
	static Graph blockchain = new SingleGraph("Blockchain Network");
	
	static void PrimeNode() {	//initialisation du graph avec un noeud primaire
		blockchain.addNode(LocalDate.now(Clock.systemUTC()).toString().concat(":"+LocalTime.now(Clock.systemUTC()).toString()));	//creation du noeud primaire avec un id definit par le temps UTC de la forme annee:mois:jour:heure:minute:seconde:milliseconde
	}
	
	static Graph GetBlockChain() {	//recuperation du graph
		return blockchain;
	}
}