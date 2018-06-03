package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import org.w3c.dom.Node;
import org.graphstream.ui.view.View;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		int i = 0;
		int choix = 1;

		if(choix == 0) {
			
			InitRandom.PrimeGraph(10);
			System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
			SimulationRandom.blockchain.addAttribute("ui.quality");
			SimulationRandom.blockchain.addAttribute("ui.antialias");
			SimulationRandom.blockchain.addAttribute("ui.stylesheet", "url('data/style.css')");
			InitRandom.GetBlockChain().display(true);
			
			while(true){
				
				System.out.println(i + ":");
				System.out.println("noeuds = " + SimulationRandom.blockchain.getNodeCount());
				System.out.println("edges = " + SimulationRandom.blockchain.getEdgeCount());
				System.out.println("diamètre = " + Toolkit.diameter(SimulationRandom.blockchain));
				System.out.println("degré moyen = " + Toolkit.averageDegree(SimulationRandom.blockchain) + "\n");
				System.out.println("distance moyenne = " + java.lang.Math.log(SimulationBarabasi.blockchain.getNodeCount()));
				System.out.println("coef de cluster moyen = " + Toolkit.averageClusteringCoefficient(SimulationRandom.blockchain) + "\n");
				i++;
				
				TimeUnit.MILLISECONDS.sleep(500);
				SimulationRandom.Connect(2);
				TimeUnit.MILLISECONDS.sleep(100);
				SimulationRandom.Disconnect(2);
				TimeUnit.MILLISECONDS.sleep(100);
			}
		}
		else if(choix == 1) {
			

			InitBarabasi.PrimeGraph(30);
			System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
			//SimulationBarabasi.blockchain.addAttribute("ui.quality");
			//SimulationBarabasi.blockchain.addAttribute("ui.antialias");
			SimulationBarabasi.blockchain.addAttribute("ui.stylesheet", "url('data/styleBara.css')");
			InitBarabasi.GetBlockChain().display(true);
			
			
			while(true){
			    for(org.graphstream.graph.Node n:SimulationBarabasi.blockchain.getEachNode()) {
			        if(n.getDegree() >= 6) {
			        	n.addAttribute("ui.class", "hub");
			        }else {
			        	n.removeAttribute("ui.class");
			        }
			    }

				
				System.out.println(i + ":");
				System.out.println("noeuds = " + SimulationBarabasi.blockchain.getNodeCount());
				System.out.println("edges = " + SimulationBarabasi.blockchain.getEdgeCount());
				System.out.println("diamètre = " + Toolkit.diameter(SimulationBarabasi.blockchain));
				System.out.println("degré moyen = " + Toolkit.averageDegree(SimulationBarabasi.blockchain) + "\n");
				System.out.println("distance moyenne = " + java.lang.Math.log(SimulationBarabasi.blockchain.getNodeCount()));
				System.out.println("coef de cluster moyen = " + Toolkit.averageClusteringCoefficient(SimulationBarabasi.blockchain) + "\n");
				i++;
				
				TimeUnit.MILLISECONDS.sleep(500);
				SimulationBarabasi.Connect(1);
				TimeUnit.MILLISECONDS.sleep(100);
				SimulationBarabasi.Disconnect(1);
				TimeUnit.MILLISECONDS.sleep(100);
			}
		}
	}

}
