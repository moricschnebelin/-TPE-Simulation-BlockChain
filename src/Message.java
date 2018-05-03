
class Message {
	
	String sender;	//id du noeud expediteur
	String flag;		//type de communication
	
	Message(String sender, String flag) {
		this.sender = sender;
		this.flag = flag;
	}
	
	String GetSender() {
		return this.sender;
	}
	
	String GetFlag() {
		return this.flag;
	}
	
}