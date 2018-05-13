package message;

import main.Block;

public class Bloc extends Message {

	Block block;	//block a diffuser
	
	public Bloc(String sender, String receiver, Block block) {
		super(sender, receiver, "bloc");
		this.block = block;
	}
	
	public Block GetBlock() {
		return this.block;
	}
	
}