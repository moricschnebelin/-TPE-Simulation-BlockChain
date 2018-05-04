package message;

public class TransferBlock extends Message {

	static Block block;	//block a transferer
	
	TransferBlock(String sender, String flag, Block block) {
		super(sender, flag);
		TransferBlock.block = block;
	}

	static Block GetBlock() {
		return TransferBlock.block;
	}
	
}
