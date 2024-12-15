import java.util.*;

// Receipt class representing individual receipts
public class Receipt {
    private static int idCounter = 1; // Static counter to generate unique IDs
    private static final double TAX_RATE = 0.13; // 13% tax rate
    private int id;
    private Store store;
    private Customer customer;
    private ArrayList<Item> items = new ArrayList<>();
    private double totalAmount; // Total before tax
    private double totalWithTax; // Total including tax
    private Payment paymentMethod;

    public Receipt() {
        this.id = idCounter++; // Assign unique ID
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getTotalWithTax() {
        return totalWithTax;
    }

    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void calculateTotal() {
        totalAmount = 0;
        for (Item item : items) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        totalWithTax = totalAmount + (totalAmount * TAX_RATE);
    }

    public void setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        StringBuilder receiptDetails = new StringBuilder();
        receiptDetails.append("Receipt ID: ").append(id).append("\n")
                .append("Store: ").append(store != null ? store.getName() : "N/A").append("\n")
                .append("Customer: ").append(customer != null ? customer.getName() : "N/A").append("\n")
                .append("Items Purchased:\n");

        // Add details for each item
        for (Item item : items) {
            receiptDetails.append(" - ").append(item.getName())
                    .append(" (Price: $").append(String.format("%.2f", item.getPrice()))
                    .append(", Quantity: ").append(item.getQuantity())
                    .append(", Subtotal: $").append(String.format("%.2f", item.getPrice() * item.getQuantity()))
                    .append(")\n");
        }

        receiptDetails.append("Total (Before Tax): $").append(String.format("%.2f", totalAmount)).append("\n")
                .append("Tax: $").append(String.format("%.2f", totalAmount * TAX_RATE)).append("\n")
                .append("Total (With Tax): $").append(String.format("%.2f", totalWithTax)).append("\n")
                .append("Payment: ").append(paymentMethod != null ? paymentMethod : "N/A").append("\n");

        return receiptDetails.toString();
    }

}
