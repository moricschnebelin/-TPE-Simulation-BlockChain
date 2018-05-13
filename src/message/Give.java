package message;

import main.Block;

public class Give extends Message {

	Block block;	//block a diffuser
	
	public Give(String sender, String receiver, Block block) {
		super(sender, receiver, "give");
		this.block = block;
	}
	
	public Block GetBlock() {
		return this.block;
	}
	
}