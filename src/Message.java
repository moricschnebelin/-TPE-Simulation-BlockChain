
public class Message {
	
	public String sender;	//id du noeud expediteur
	public String flag;		//type de communication
	
	Message(String sender, String flag) {
		this.sender = sender;
		this.flag = flag;
	}
}