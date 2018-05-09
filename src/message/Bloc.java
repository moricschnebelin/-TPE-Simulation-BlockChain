package message;

import main.Block;

public class Bloc extends Message {

	static Block block;	//block a diffuser
	
	public Bloc(String sender, Block block) {
		super(sender, "bloc");
		Bloc.block = block;
	}
	
	public static Block GetBlock() {
		return Bloc.block;
	}
	
}