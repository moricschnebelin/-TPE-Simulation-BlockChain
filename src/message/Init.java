package message;

public class Init extends Message {

	Integer blockNumber;	//numero de bloc demander
	
	public Init(String sender, Integer blockNumber) {
		super(sender, "init");
		this.blockNumber = blockNumber;
	}
	
	public Integer GetBlockNumber() {
		return this.blockNumber;
	}
	
}