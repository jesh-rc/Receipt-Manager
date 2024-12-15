import java.util.ArrayList;

public class Customer {
    private String name;
    private ArrayList<Receipt> receipts = new ArrayList<Receipt>();

    public Customer(String name, ArrayList<Receipt> receipts) {
        this.name = name;
        this.receipts = receipts;
    }

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Receipt> getReceipts() {
        return receipts;
    }

    public void addReceipt(Receipt receipt) {
        receipts.add(receipt);
    }
    
    public void viewReceipts() {
        for (Receipt receipt : receipts) {
            System.out.println(receipt);
        }
    }
    
}
