// Represents a payment made via credit card
public class CreditPayment extends Payment {
    private String creditCardNumber;

    public CreditPayment(double amount, String creditCardNumber) {
        super("credit", amount); // Use "credit" as the payment type
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public String toString() {
        return super.toString() + ", Credit Card: " + creditCardNumber;
    }
}
