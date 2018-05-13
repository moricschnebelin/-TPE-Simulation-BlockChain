package message;

public class Sync extends Message {

	public Sync(String sender, String receiver) {
		super(sender, receiver, "sync");
	}
	
}
