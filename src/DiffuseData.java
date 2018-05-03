
class DiffuseData extends Message {

	String data;	//transaction a transmettre
	
	DiffuseData(String sender, String flag, String data) {
		super(sender, flag);
		this.data = data;
	}
	
	String GetData() {
		return this.data;
	}
	
}