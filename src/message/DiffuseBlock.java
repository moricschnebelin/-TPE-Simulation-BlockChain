package message;

class DiffuseBlock extends Message {

	static Block block;	//block a diffuser
	
	DiffuseBlock(String sender, String flag, Block block) {
		super(sender, flag);
		DiffuseBlock.block = block;
	}
	
	static Block GetBlock() {
		return DiffuseBlock.block;
	}
	
}