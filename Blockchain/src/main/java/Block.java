import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    private String data; // Aqui será apenas uma string, isso pode ser qualquer coisa
    private long timeStamp; //
    private int nonce;

    public Block(String data, String previousHash) {
        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash(){
        String calculatedHash = StringUtil.applySha256(this.previousHash+Long.toString(this.timeStamp)+this.data);
        return calculatedHash;
    }

    public void mineBlock(int difficulty){
        String target = new String(new char[difficulty]).replace('\0','0');
        while(!hash.substring( 0,difficulty).equals(target)){
            nonce++;
            hash = calculateHash();
        }

        System.out.println("Block mined!: " + hash);
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("{").append("\n");
        str.append("    Hash: " + this.hash).append("\n");
        str.append("    PreviousHash: " + this.previousHash).append("\n");
        str.append("    data: " + this.data).append("\n");
        str.append("    timeStamo: " + this.timeStamp).append("\n");
        str.append("    nonce: " + this.nonce).append("\n");
        str.append("}");
        str.append("\n");

        return str.toString();
    }
}
