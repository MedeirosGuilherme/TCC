import com.sun.xml.internal.ws.util.StringUtils;

import java.security.*;
import java.util.ArrayList;

public class Transaction {

    public String transactionId; //hash da transação
    public PublicKey sender;
    public PublicKey reciepient;
    public float value;
    public byte[] singature;

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0; //contador de transações

    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs){
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
        this.transactionId = calculateHash();
    }

    private String calculateHash(){
        sequence++;
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender)+
                        StringUtil.getStringFromKey(reciepient)+
                        Float.toString(value) + sequence);
    }

    public void generateSignature(PrivateKey privateKey){
        String data = StringUtil.getStringFromKey(this.sender)+StringUtil.getStringFromKey(this.reciepient)+Float.toString(value);
        this.singature = StringUtil.applyECDSASig(privateKey,data);
    }

    public boolean verifySignature(){
        String data = StringUtil.getStringFromKey(this.sender)+StringUtil.getStringFromKey(this.reciepient)+Float.toString(value);
        return StringUtil.verifyECDSASig(this.sender,data, this.singature);
    }
}
