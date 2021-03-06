import java.util.Date;
import java.security.MessageDigest;

class StringUtil {
	//Applies Sha256 to a string and returns the result. 
	public static String applySha256(String input){		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}	
}

class Block {

	public String hash;
	public String previousHash;
	private String data; //our data will be a simple message.
	private long timeStamp; //as number of milliseconds since 1/1/1970.

	//Block Constructor.
	public Block(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256( 
                previousHash +
                Long.toString(timeStamp) +
                data 
                );
        return calculatedhash;
    }
}

public class App {

	public static void main(String[] args) {
		
		Block genesisBlock = new Block("FIRST BLOCK", "0");
		System.out.println("Hash for block 1 : " + genesisBlock.hash);
		
		Block secondBlock = new Block("SECOND BLOCK",genesisBlock.hash);
		System.out.println("Hash for block 2 : " + secondBlock.hash);
		
		Block thirdBlock = new Block("THIRD BLOCK",secondBlock.hash);
		System.out.println("Hash for block 3 : " + thirdBlock.hash);

        System.out.println("\nThe block chain:");
        System.out.println("[");

        System.out.println("\t{");
        System.out.println("\t  " + "'hash': " + "'" + genesisBlock.hash + "',");
        System.out.println("\t  " + "'previousHash': " + "'" + genesisBlock.previousHash + "',");
        System.out.println("\t},");

        System.out.println("\t{");
        System.out.println("\t  " + "'hash': " + "'" + secondBlock.hash + "',");
        System.out.println("\t  " + "'previousHash': " + "'" + secondBlock.previousHash + "',");
        System.out.println("\t},");

        System.out.println("\t{");
        System.out.println("\t  " + "'hash': " + "'" + thirdBlock.hash + "',");
        System.out.println("\t  " + "'previousHash': " + "'" + thirdBlock.previousHash + "',");
        System.out.println("\t},");

        System.out.println("]\n");
    }
	
}