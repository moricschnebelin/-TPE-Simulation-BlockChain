
public class DiffuseData extends Message {

	public String data;	//transaction a transmettre
	
	DiffuseData(String sender, String flag, String data) {
		super(sender, flag);
		this.data = data;
	}

}
