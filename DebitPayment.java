// Represents a payment made via debit card
public class DebitPayment extends Payment {
    private String debitCardNumber;

    public DebitPayment(double amount, String debitCardNumber) {
        super("debit", amount); // Use "debit" as the payment type
        this.debitCardNumber = debitCardNumber;
    }

    public String getDebitCardNumber() {
        return debitCardNumber;
    }

    public void setDebitCardNumber(String debitCardNumber) {
        this.debitCardNumber = debitCardNumber;
    }

    @Override
    public String toString() {
        return super.toString() + ", Debit Card: " + debitCardNumber;
    }
}
