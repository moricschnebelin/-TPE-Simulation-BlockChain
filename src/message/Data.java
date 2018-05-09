package message;

public class Data extends Message {

	String data;	//transaction a transmettre
	
	public Data(String sender, String data) {
		super(sender, "data");
		this.data = data;
	}
	
	public String GetData() {
		return this.data;
	}
	
}