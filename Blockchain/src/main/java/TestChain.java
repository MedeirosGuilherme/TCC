import java.util.ArrayList;

public class TestChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficult = 0;

    public static void main(String[] args) {
        Block genesisBlock = new Block("Hello, I'm genesis block", "0");
        System.out.println("Hash for block 1: " + genesisBlock.hash);
        blockchain.add(genesisBlock);
        System.out.println("Mining for block 1...");
        blockchain.get(0).mineBlock(difficult);

        Block secondBlock = new Block("Hello, I'm the second block", blockchain.get(blockchain.size()-1).hash);
        System.out.println("Hash for block 2: "+ secondBlock.hash);
        blockchain.add(secondBlock);
        System.out.println("Mining for block 2...");
        blockchain.get(1).mineBlock(difficult);

        Block thirdBlock = new Block("Hello, I'm the third one", blockchain.get(blockchain.size()-1).hash);
        System.out.println("Hash for block 3: "+ thirdBlock.hash);
        blockchain.add(thirdBlock);
        System.out.println("Mining for block 3...");
        blockchain.get(2).mineBlock(difficult);

        System.out.println("\nBlockchain is valid: " + isChainValid() + "\n");

        System.out.println(blockchain.toString());
    }


    public void addBlock(Block block){
        this.blockchain.add(block);
    }

    public void addNewBlock(String data){
        if(this.blockchain.size() == 0){
            blockchain.add(new Block(data, "0"));
        }
        else{
            this.blockchain.add(new Block(data, this.blockchain.get(blockchain.size()-1).hash));
        }
    }

    public static Boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficult]).replace('\0', '0');

        for(int i=1; i <blockchain.size();i++){
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);

            if(!currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("Current hashes not equal");
                return false;
            }

            if(!previousBlock.hash.equals(currentBlock.previousHash)){
                System.out.println("Previous hashes not equal");
                return false;
            }

            if(!currentBlock.hash.substring(0,difficult).equals(hashTarget)){
                System.out.println("Block not mined");
                return false;
            }
        }
        return true;
    }
}
