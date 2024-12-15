import java.util.*;

// Main class to manage the receipt system
public class ReceiptSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Create a list to store receipts
        ArrayList<Receipt> receipts = new ArrayList<>();
        // Create a list to store customers
        ArrayList<Customer> customers = new ArrayList<>();
        // Create a list to store stores
        ArrayList<Store> stores = new ArrayList<>();
        // Main loop to interact with the user
        while (true) {
            int choice = 0;
            String input = "";
            do {
                System.out.println("1. Add Receipt");
                System.out.println("2. View Receipts");
                System.out.println("3. Generate Reports");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                input = scanner.nextLine();

                if (integerValidation(input)) {
                    choice = Integer.parseInt(input);
                }

                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.\n\n");
                }
            } while (!integerValidation(input) || choice < 1 || choice > 4);

            switch (choice) {
                case 1:
                    // Add a receipt
                    Receipt receipt = new Receipt();

                    // Validate store name
                    String storeName = "";
                    do {
                        System.out.println("Enter store name: ");
                        storeName = scanner.nextLine().trim();
                        if (storeName.isEmpty()) {
                            System.out.println("Store name cannot be empty. Please enter a valid store name.");
                        }
                    } while (storeName.isEmpty());

                    Store store = findStoreByName(stores, storeName);
                    if (store == null) {
                        store = new Store(storeName);
                        stores.add(store);
                    }
                    receipt.setStore(store);

                    // Validate customer name
                    String customerName = "";
                    do {
                        System.out.println("Enter customer name: ");
                        customerName = scanner.nextLine().trim();
                        if (customerName.isEmpty()) {
                            System.out.println("Customer name cannot be empty. Please enter a valid customer name.");
                        }
                    } while (customerName.isEmpty());

                    Customer customer = findCustomerByName(customers, customerName);
                    if (customer == null) {
                        customer = new Customer(customerName);
                        customers.add(customer);
                    }
                    receipt.setCustomer(customer);

                    // Add items to the receipt
                    boolean hasItems = false;
                    while (true) {
                        System.out.println("Enter item name (or 'done' to finish):");
                        String itemName = scanner.nextLine().trim();
                        if (itemName.equalsIgnoreCase("done")) {
                            break;
                        }
                        if (itemName.isEmpty()) {
                            System.out.println("Item name cannot be empty. Please enter a valid item name.");
                            continue;
                        }

                        double price = -1;
                        do {
                            System.out.println("Enter item price: ");
                            String priceInput = scanner.nextLine();
                            if (doubleValidation(priceInput)) {
                                price = Double.parseDouble(priceInput);
                            }
                            if (price < 0) {
                                System.out.println("Invalid input. Please enter a positive number.\n\n");
                            }
                        } while (price < 0);

                        int quantity = -1;
                        do {
                            System.out.println("Enter item quantity: ");
                            String quantityInput = scanner.nextLine();
                            if (integerValidation(quantityInput)) {
                                quantity = Integer.parseInt(quantityInput);
                            }
                            if (quantity < 0) {
                                System.out.println("Invalid input. Please enter a positive integer.\n\n");
                            }
                        } while (quantity < 0);

                        Item item = new Item(itemName, price, quantity);
                        receipt.addItem(item);
                        hasItems = true;
                    }

                    if (!hasItems) {
                        System.out.println("A receipt must have at least one item. Receipt creation canceled.");
                        break;
                    }

                    // Calculate totals
                    receipt.calculateTotal();

                    // Automatically calculate payment amount including tax
                    double paymentAmount = receipt.getTotalWithTax();
                    Payment payment = null;
                    do {
                        System.out.println("Enter payment method (cash, credit, debit): ");
                        String paymentType = scanner.nextLine().trim().toLowerCase();

                        if (paymentType.equals("cash")) {
                            payment = new CashPayment(paymentAmount);
                        } else if (paymentType.equals("credit")) {
                            String creditCardNumber = "";
                            do {
                                System.out.print("Enter credit card number (numeric only, 16 digits): ");
                                creditCardNumber = scanner.nextLine();
                                if (!creditCardNumber.matches("\\d{16}")) { // Regex to match exactly 16 digits
                                    System.out.println(
                                            "Invalid input. Please enter a numeric value that is exactly 16 digits long.");
                                }
                            } while (!creditCardNumber.matches("\\d{16}"));
                            payment = new CreditPayment(paymentAmount, creditCardNumber);

                            payment = new CreditPayment(paymentAmount, creditCardNumber);
                        } else if (paymentType.equals("debit")) {
                            String debitCardNumber = "";
                            do {
                                System.out.print("Enter debit card number (numeric only, 16 digits): ");
                                debitCardNumber = scanner.nextLine();
                                if (!debitCardNumber.matches("\\d{16}")) { // Regex to match exactly 16 digits
                                    System.out.println(
                                            "Invalid input. Please enter a numeric value that is exactly 16 digits long.");
                                }
                            } while (!debitCardNumber.matches("\\d{16}"));
                            payment = new DebitPayment(paymentAmount, debitCardNumber);

                        } else {
                            System.out.println("Invalid payment method. Please enter 'cash', 'credit', or 'debit'.");
                        }
                    } while (payment == null);

                    receipt.setPaymentMethod(payment);

                    // Add receipt to lists
                    receipts.add(receipt);
                    store.addReceipt(receipt);
                    customer.addReceipt(receipt);
                    System.out.println("Receipt added successfully!");
                    break;

                case 2:
                    // View receipts
                    int viewChoice = 0;
                    do {
                        System.out.println("View receipts by:");
                        System.out.println("1. Customer");
                        System.out.println("2. Store");
                        System.out.print("Enter your choice: ");
                        String viewChoiceInput = scanner.nextLine();

                        if (integerValidation(viewChoiceInput)) {
                            viewChoice = Integer.parseInt(viewChoiceInput);
                        }

                        if (viewChoice < 1 || viewChoice > 2) {
                            System.out.println("Invalid input. Please enter 1 or 2.\n");
                        }
                    } while (viewChoice < 1 || viewChoice > 2);

                    if (viewChoice == 1) {
                        // Validate customer name input
                        String customerNameToView;
                        do {
                            System.out.print("Enter customer name: ");
                            customerNameToView = scanner.nextLine().trim();
                            if (customerNameToView.isEmpty()) {
                                System.out
                                        .println("Customer name cannot be empty. Please enter a valid customer name.");
                            }
                        } while (customerNameToView.isEmpty());

                        Customer customerToView = findCustomerByName(customers, customerNameToView);
                        if (customerToView != null) {
                            if (customerToView.getReceipts().isEmpty()) {
                                System.out.println("No receipts found for customer: " + customerNameToView);
                            } else {
                                System.out.println();
                                customerToView.viewReceipts();
                            }
                        } else {
                            System.out.println("Customer not found.");
                        }
                    } else if (viewChoice == 2) {
                        // Validate store name input
                        String storeNameToView;
                        do {
                            System.out.print("Enter store name: ");
                            storeNameToView = scanner.nextLine().trim();
                            if (storeNameToView.isEmpty()) {
                                System.out.println("Store name cannot be empty. Please enter a valid store name.");
                            }
                        } while (storeNameToView.isEmpty());

                        Store storeToView = findStoreByName(stores, storeNameToView);
                        if (storeToView != null) {
                            if (storeToView.getReceipts().isEmpty()) {
                                System.out.println("No receipts found for store: " + storeNameToView);
                            } else {
                                System.out.println();
                                storeToView.viewReceipts();
                            }
                        } else {
                            System.out.println("Store not found.");
                        }
                    }
                    break;

                case 3:
                    int reportChoice = 0;
                    do {
                        System.out.println("Generate reports by:");
                        System.out.println("1. Customer Name");
                        System.out.println("2. Store");
                        System.out.println("3. Item");
                        System.out.print("Enter your choice: ");
                        String reportChoiceInput = scanner.nextLine();

                        if (integerValidation(reportChoiceInput)) {
                            reportChoice = Integer.parseInt(reportChoiceInput);
                        }

                        if (reportChoice < 1 || reportChoice > 3) {
                            System.out.println("Invalid input. Please enter a number between 1 and 3.\n");
                        }
                    } while (reportChoice < 1 || reportChoice > 3);

                    switch (reportChoice) {
                        case 1:
                            System.out.println("\n----------Customer Report----------");
                            generateReportByCustomerName(receipts, customers);
                            break;

                        case 2:
                            System.out.println("\n----------Store Report----------");
                            generateReportByStore(receipts, stores);
                            break;

                        case 3:
                            System.out.println("\n----------Item Report----------");
                            generateReportByItem(receipts);
                            break;
                    }
                    break;

                case 4:
                    // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
            }
        }
    }

    // Helper methods
    private static Customer findCustomerByName(ArrayList<Customer> customers, String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }

    private static Store findStoreByName(ArrayList<Store> stores, String name) {
        for (Store store : stores) {
            if (store.getName().equalsIgnoreCase(name)) {
                return store;
            }
        }
        return null;
    }

    private static boolean integerValidation(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean doubleValidation(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void generateReportByCustomerName(ArrayList<Receipt> receipts, ArrayList<Customer> customers) {
        System.out.println("Receipts categorized by customers:\n");

        // Loop through each customer
        for (Customer customer : customers) {
            System.out.println("Receipts for Customer: " + customer.getName());

            // Find and print receipts for the current customer
            for (Receipt receipt : receipts) {
                if (receipt.getCustomer().getName().equalsIgnoreCase(customer.getName())) {
                    System.out.println(receipt); // Use Receipt's `toString()` for output
                }
            }

            System.out.println(); // Add a blank line for better readability
        }

        // Handle case when there are no customers
        if (customers.isEmpty()) {
            System.out.println("No customers found in the system.");
        }
    }

    private static void generateReportByStore(ArrayList<Receipt> receipts, ArrayList<Store> stores) {
        System.out.println("Receipts categorized by stores:\n");

        // Loop through each store
        for (Store store : stores) {
            System.out.println("Receipts for Store: " + store.getName());

            // Find and print receipts for the current store
            for (Receipt receipt : receipts) {
                if (receipt.getStore().getName().equalsIgnoreCase(store.getName())) {
                    System.out.println(receipt); // Use Receipt's `toString()` for output
                }
            }

            System.out.println(); // Add a blank line for better readability
        }

        // Handle case when there are no stores
        if (stores.isEmpty()) {
            System.out.println("No stores found in the system.");
        }
    }

    private static void generateReportByItem(ArrayList<Receipt> receipts) {
        ArrayList<String> processedItems = new ArrayList<>(); // Track already processed items (case-insensitive)

        System.out.println("Receipts categorized by items:\n");

        // Loop through all receipts and their items
        for (Receipt receipt : receipts) {
            for (Item item : receipt.getItems()) {
                // Check if the item (case-insensitive) has already been processed
                if (processedItems.contains(item.getName().toLowerCase())) {
                    continue;
                }

                System.out.println("Receipts containing Item: " + item.getName());

                // Find and print receipts containing this item
                for (Receipt r : receipts) {
                    for (Item i : r.getItems()) {
                        if (i.getName().equalsIgnoreCase(item.getName())) {
                            System.out.println("Receipt ID: " + r.getId() +
                                    ", Store: " + r.getStore().getName() +
                                    ", Customer: " + r.getCustomer().getName() +
                                    ", Item Total: $" + String.format("%.2f", i.getPrice() * i.getQuantity()));
                        }
                    }
                }

                // Mark the item as processed (in lowercase)
                processedItems.add(item.getName().toLowerCase());
                System.out.println(); // Blank line for better readability
            }
        }

        // Handle case when no items exist in any receipts
        if (processedItems.isEmpty()) {
            System.out.println("No items found in any receipts.");
        }
    }
}