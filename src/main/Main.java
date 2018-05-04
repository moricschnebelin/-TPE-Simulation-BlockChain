package main;
import java.util.concurrent.TimeUnit;

import org.graphstream.algorithm.Toolkit;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		int i = 0;
		Initialisation.PrimeGraph(10);
		Initialisation.GetBlockChain().display(true);
		
		while(true){
			System.out.println(i + ":");
			System.out.println("noeuds = " + Simulation.blockchain.getNodeCount());
			System.out.println("edges = " + Simulation.blockchain.getEdgeCount());
			System.out.println("degré moyen = " + Toolkit.averageDegree(Simulation.blockchain) + "\n");
			i++;
			
			TimeUnit.MILLISECONDS.sleep(3000);
			Simulation.Connect(2);
			TimeUnit.MILLISECONDS.sleep(1000);
			Simulation.Disconnect(2);
			TimeUnit.MILLISECONDS.sleep(1000);
		}
	}

}
