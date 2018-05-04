package main;

class Block {
	
	Integer nonce;	//entier unique
	String data;	//chainde de transaction integré au bloc separer par un pointeur
	String hash;	//hash du bloc courant
	
	Block(Integer nonce, String data, String hash) {
		this.nonce = nonce;
		this.data = data;
		this.hash = hash;
	}
	
	Integer GetNonce() {
		return this.nonce;
	}
	
	String GetData() {
		return this.data;
	}
	
	String GetHash() {
		return this.hash;
	}
	
}