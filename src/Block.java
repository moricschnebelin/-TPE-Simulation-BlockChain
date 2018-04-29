
public class Block {
	
	public Integer nonce;
	public String data;
	public String previousHash;
	public String currentHash;
	
	Block(String data, String previousHash) {
		this.nonce = 0;
		this.data = data;
		this.previousHash = previousHash;
		this.currentHash = "";
	}
	
	void SetNonce(Integer nonce) {
		this.nonce = nonce;
	}
	
	Integer GetNonce() {
		return this.nonce;
	}
	
	String GetData() {
		return this.data;
	}
	
	String GetPreviousHash() {
		return this.previousHash;
	}
	
	void SetCurrentHash(String currentHash) {
		this.currentHash = currentHash;
	}
	
	String GetCurrentHash() {
		return this.currentHash;
	}
	
}