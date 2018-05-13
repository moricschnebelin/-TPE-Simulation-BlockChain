package message;

public class Message {
	
	String sender;	//id du noeud expediteur
	String receiver;	//id du noeud destinataire
	String flag;		//type de communication
	
	Message(String sender, String receiver, String flag) {
		this.sender = sender;
		this.receiver = receiver;
		this.flag = flag;
	}
	
	public String GetSender() {
		return this.sender;
	}
	
	public String GetReceiver() {
		return this.receiver;
	}
	
	public String GetFlag() {
		return this.flag;
	}
	
}