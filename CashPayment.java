// Represents a payment made in cash
public class CashPayment extends Payment {
    public CashPayment(double amount) {
        super("cash", amount); // Use "cash" as the payment type
    }
}
