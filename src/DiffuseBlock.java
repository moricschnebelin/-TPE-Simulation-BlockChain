
public class DiffuseBlock extends Message {

	Block block;	//block a transmettre
	
	DiffuseBlock(String sender, String flag, Block block) {
		super(sender, flag);
		this.block = block;
	}
	
	Block GetBlock() {
		return this.block;
	}
	
}