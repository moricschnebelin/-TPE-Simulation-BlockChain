package message;

public class Message {
	
	String sender;	//id du noeud expediteur
	String flag;		//type de communication
	
	Message(String sender, String flag) {
		this.sender = sender;
		this.flag = flag;
	}
	
	public String GetSender() {
		return this.sender;
	}
	
	public String GetFlag() {
		return this.flag;
	}
	
}