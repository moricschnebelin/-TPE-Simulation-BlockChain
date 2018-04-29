
public class RequestBlock extends Message {

	public Integer blockNumber;	//numero de bloc demander
	
	RequestBlock(String sender, String flag, Integer blockNumber) {
		super(sender, flag);
		this.blockNumber = blockNumber;
	}
	
	Integer GetBlockNumber() {
		return this.blockNumber;
	}
	
}