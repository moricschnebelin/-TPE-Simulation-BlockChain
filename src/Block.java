
public class Block {
	
	public Integer nonce;
	public String data;
	public String prev;
	public String hash;
	
	Block(Integer nonce, String data, String prev, String hash) {
		this.nonce = nonce;
		this.data = data;
		this.prev = prev;
		this.hash = hash;
	}
}