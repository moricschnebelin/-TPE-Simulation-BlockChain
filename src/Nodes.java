import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Nodes {
	
	static class Node implements Runnable {
		
		BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>();
		String message;
		
		public void run() {
			while(true) {
				while((message = messageQueue.poll()) != null) {	//si il y a des message en attente
					switch(message) {
						case "sync" : {
							//envoyer bloc
						}
						case "data" : {
							//diffuser transaction
						}
						case "bloc" : {
							//diffuser bloc
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