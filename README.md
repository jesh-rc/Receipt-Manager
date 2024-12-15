# Receipt-Manager

Description of each file:

CashPayment.java
Purpose: Represents payments made in cash.
Details: Inherits from the Payment class. Sets the payment type to "cash".

CreditPayment.java
Purpose: Represents payments made via credit card.
Details: Inherits from the Payment class. Stores and manages the credit card number. Overrides toString() to include the credit card number in the description.

Customer.java
Purpose: Represents a customer in the receipt system.
Details: Stores the customer’s name and a list of their associated receipts. Provides methods to add and view receipts.

DebitPayment.java
Purpose: Represents payments made via debit card.
Details: Inherits from the Payment class. Stores and manages the debit card number. Overrides toString() to include the debit card number in the description.

Item.java
Purpose: Represents individual items in a receipt.
Details: Stores the item’s name, price, and quantity. Provides getters and setters for these attributes.

Payment.java
Purpose: Serves as the base class for all payment types.
Details: Contains attributes for the payment type and amount. Provides a toString() method for standardized output. Designed for extension by specific payment methods like CashPayment, CreditPayment, and DebitPayment.

Receipt.java
Purpose: Represents an individual receipt.
Details: Contains attributes such as store, customer, items, and payment method. Manages receipt-specific data like totals (before and after tax) and unique ID generation. Provides a detailed toString() for receipt output, including item details and totals.

ReceiptSystem.java
Purpose: The main class that implements the receipt management system.
Details: Handles user input for adding receipts, viewing receipts, and generating reports. Provides helper methods for finding customers, stores, and managing reports.

Store.java
Purpose: Represents a store in the receipt system.
Details: Stores the store’s name and a list of associated receipts. Provides methods to add and view receipts.
