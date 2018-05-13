package message;

public class Data extends Message {

	String data;	//transaction a transmettre
	
	public Data(String sender, String receiver, String data) {
		super(sender, receiver, "data");
		this.data = data;
	}
	
	public String GetData() {
		return this.data;
	}
	
}