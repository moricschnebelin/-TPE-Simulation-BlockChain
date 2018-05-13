package message;

public class Requ extends Message {
	
	Integer blockNumber;	//numero de bloc demander
	
	public Requ(String sender, String receiver, Integer blockNumber) {
		super(sender, receiver, "requ");
		this.blockNumber = blockNumber;
	}
	
	public Integer GetBlockNumber() {
		return this.blockNumber;
	}
	
}