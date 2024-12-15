// Payment class representing the base class for payment methods
public class Payment {
    protected String type; // Changed to protected for subclass access
    protected double amount;

    public Payment(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return type + " ($" + String.format("%.2f", amount) + ")";
    }
}
