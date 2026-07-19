import java.util.*;

/*
    ==========================================
          STOCK TRADING PLATFORM
          Part 1 of 4
    ==========================================
*/

public class trading {

    // ==========================
    // Stock Class
    // ==========================
    static class Stock {

        private String symbol;
        private String companyName;
        private double price;

        public Stock(String symbol, String companyName, double price) {
            this.symbol = symbol;
            this.companyName = companyName;
            this.price = price;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getCompanyName() {
            return companyName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return String.format("%-8s %-20s Rs. %.2f",
                    symbol,
                    companyName,
                    price);
        }
    }

    // ==========================
    // Transaction Class
    // ==========================
    static class Transaction {

        private String type;
        private String symbol;
        private int quantity;
        private double price;
        private Date date;

        public Transaction(String type,
                           String symbol,
                           int quantity,
                           double price) {

            this.type = type;
            this.symbol = symbol;
            this.quantity = quantity;
            this.price = price;
            this.date = new Date();
        }

        @Override
        public String toString() {

            return String.format(
                    "%-5s | %-6s | Qty:%3d | Price: Rs. %.2f | %s",
                    type,
                    symbol,
                    quantity,
                    price,
                    date.toString()
            );
        }
    }

    // ==========================
    // Portfolio Class
    // ==========================
    static class Portfolio {

        private HashMap<String, Integer> holdings =
                new HashMap<>();

        public void buy(String symbol,
                        int qty) {

            holdings.put(
                    symbol,
                    holdings.getOrDefault(symbol, 0) + qty
            );
        }

        public boolean sell(String symbol,
                            int qty) {

            if (!holdings.containsKey(symbol))
                return false;

            int owned = holdings.get(symbol);

            if (owned < qty)
                return false;

            owned -= qty;

            if (owned == 0)
                holdings.remove(symbol);
            else
                holdings.put(symbol, owned);

            return true;
        }

        public int getQuantity(String symbol) {

            return holdings.getOrDefault(symbol, 0);
        }

        public HashMap<String, Integer> getHoldings() {
            return holdings;
        }

        public boolean isEmpty() {
            return holdings.isEmpty();
        }
    }

    // ==========================
    // User Class
    // ==========================
    static class User {

        private String name;

        private double balance;

        private Portfolio portfolio;

        private ArrayList<Transaction> history;

        public User(String name,
                    double balance) {

            this.name = name;

            this.balance = balance;

            portfolio = new Portfolio();

            history = new ArrayList<>();
        }

        public double getBalance() {
            return balance;
        }

        public Portfolio getPortfolio() {
            return portfolio;
        }

        public ArrayList<Transaction> getHistory() {
            return history;
        }

        public void buyStock(Stock stock,
                             int qty) {

            double total = stock.getPrice() * qty;

            if (balance >= total) {

                balance -= total;

                portfolio.buy(stock.getSymbol(), qty);

                history.add(
                        new Transaction(
                                "BUY",
                                stock.getSymbol(),
                                qty,
                                stock.getPrice()
                        )
                );

                System.out.println("\nStock Purchased Successfully!");

            } else {

                System.out.println("\nInsufficient Balance!");
            }
        }

        public void sellStock(Stock stock,
                              int qty) {

            if (portfolio.sell(stock.getSymbol(), qty)) {

                double total = stock.getPrice() * qty;

                balance += total;

                history.add(
                        new Transaction(
                                "SELL",
                                stock.getSymbol(),
                                qty,
                                stock.getPrice()
                        )
                );

                System.out.println("\nStock Sold Successfully!");

            } else {

                System.out.println("\nNot Enough Shares!");
            }
        }
    }
        // ==========================
    // Stock Market Class
    // ==========================
    static class StockMarket {

        private HashMap<String, Stock> market;
        private Random random;

        public StockMarket() {

            random = new Random();

            market = new HashMap<>();

            market.put("AAPL",
                    new Stock("AAPL",
                            "Apple",
                            185.50));

            market.put("GOOG",
                    new Stock("GOOG",
                            "Google",
                            2745.75));

            market.put("MSFT",
                    new Stock("MSFT",
                            "Microsoft",
                            338.20));

            market.put("AMZN",
                    new Stock("AMZN",
                            "Amazon",
                            142.80));

            market.put("TSLA",
                    new Stock("TSLA",
                            "Tesla",
                            248.60));

            market.put("META",
                    new Stock("META",
                            "Meta",
                            321.40));

            market.put("NFLX",
                    new Stock("NFLX",
                            "Netflix",
                            445.75));
        }

        public Stock getStock(String symbol) {
            return market.get(symbol.toUpperCase());
        }

        public void displayMarket() {

            System.out.println("\n==============================================");
            System.out.println("              LIVE STOCK MARKET");
            System.out.println("==============================================");

            System.out.printf("%-10s %-20s %10s\n",
                    "Symbol",
                    "Company",
                    "Price");

            System.out.println("----------------------------------------------");

            for (Stock stock : market.values()) {

                System.out.printf("%-10s %-20s Rs. %.2f\n",
                        stock.getSymbol(),
                        stock.getCompanyName(),
                        stock.getPrice());
            }

            System.out.println("----------------------------------------------");
        }

        // Random Market Simulation
        public void updatePrices() {

            for (Stock stock : market.values()) {

                double change = random.nextDouble() * 20 - 10;

                double newPrice = stock.getPrice() + change;

                if (newPrice < 20)
                    newPrice = 20;

                stock.setPrice(newPrice);
            }

            System.out.println("\nMarket prices updated successfully!");
        }

        public double calculatePortfolioValue(Portfolio portfolio) {

            double total = 0;

            for (Map.Entry<String, Integer> entry :
                    portfolio.getHoldings().entrySet()) {

                Stock stock = market.get(entry.getKey());

                if (stock != null) {

                    total += stock.getPrice()
                            * entry.getValue();
                }
            }

            return total;
        }

        public void displayPortfolio(User user) {

            Portfolio portfolio = user.getPortfolio();

            if (portfolio.isEmpty()) {

                System.out.println("\nPortfolio is Empty.");
                return;
            }

            System.out.println("\n==============================================");
            System.out.println("              YOUR PORTFOLIO");
            System.out.println("==============================================");

            System.out.printf("%-10s %-10s %-12s %-12s\n",
                    "Symbol",
                    "Shares",
                    "Price",
                    "Value");

            System.out.println("----------------------------------------------");

            double total = 0;

            for (Map.Entry<String, Integer> entry :
                    portfolio.getHoldings().entrySet()) {

                Stock stock = market.get(entry.getKey());

                double value =
                        stock.getPrice() * entry.getValue();

                total += value;

                System.out.printf("%-10s %-10d Rs. %-11.2f Rs. %.2f\n",
                        stock.getSymbol(),
                        entry.getValue(),
                        stock.getPrice(),
                        value);
            }

            System.out.println("----------------------------------------------");

            System.out.printf("Cash Balance : Rs. %.2f\n",
                    user.getBalance());

            System.out.printf("Portfolio Value : Rs. %.2f\n",
                    total);

            System.out.printf("Total Assets : Rs. %.2f\n",
                    total + user.getBalance());
        }

        public void displayTransactions(User user) {

            System.out.println("\n==============================================");
            System.out.println("          TRANSACTION HISTORY");
            System.out.println("==============================================");

            if (user.getHistory().isEmpty()) {

                System.out.println("No Transactions Found.");
                return;
            }

            for (Transaction t : user.getHistory()) {

                System.out.println(t);
            }
        }
    }
        // ==========================
    // Main Method
    // ==========================
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StockMarket market = new StockMarket();

        User user = new User("Investor", 100000);

        while (true) {

            System.out.println("\n=======================================");
            System.out.println("      STOCK TRADING PLATFORM");
            System.out.println("=======================================");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Transaction History");
            System.out.println("6. Update Market Prices");
            System.out.println("7. Exit");
            System.out.println("=======================================");

            System.out.print("Enter Choice : ");

            int choice;

            try {

                choice = Integer.parseInt(sc.nextLine());

            } catch (Exception e) {

                System.out.println("Invalid Input!");
                continue;
            }

            switch (choice) {

                case 1:

                    market.displayMarket();

                    break;

                case 2:

                    market.displayMarket();

                    System.out.print("\nEnter Stock Symbol : ");

                    String buySymbol =
                            sc.nextLine().toUpperCase();

                    Stock buyStock =
                            market.getStock(buySymbol);

                    if (buyStock == null) {

                        System.out.println("Invalid Stock.");

                        break;
                    }

                    System.out.print("Enter Quantity : ");

                    int buyQty;

                    try {

                        buyQty =
                                Integer.parseInt(sc.nextLine());

                    } catch (Exception e) {

                        System.out.println("Invalid Quantity.");

                        break;
                    }

                    if (buyQty <= 0) {

                        System.out.println("Quantity must be greater than zero.");

                        break;
                    }

                    user.buyStock(buyStock, buyQty);

                    break;

                case 3:

                    market.displayPortfolio(user);

                    System.out.print("\nEnter Stock Symbol : ");

                    String sellSymbol =
                            sc.nextLine().toUpperCase();

                    Stock sellStock =
                            market.getStock(sellSymbol);

                    if (sellStock == null) {

                        System.out.println("Invalid Stock.");

                        break;
                    }

                    System.out.print("Enter Quantity : ");

                    int sellQty;

                    try {

                        sellQty =
                                Integer.parseInt(sc.nextLine());

                    } catch (Exception e) {

                        System.out.println("Invalid Quantity.");

                        break;
                    }

                    if (sellQty <= 0) {

                        System.out.println("Quantity must be greater than zero.");

                        break;
                    }

                    user.sellStock(sellStock, sellQty);

                    break;

                case 4:

                    market.displayPortfolio(user);

                    break;

                case 5:

                    market.displayTransactions(user);

                    break;

                case 6:

                    market.updatePrices();

                    break;

                case 7:

                    System.out.println("\n=======================================");
                    System.out.println("Thank You For Using");
                    System.out.println("Stock Trading Platform");
                    System.out.println("=======================================");

                    sc.close();

                    return;

                default:

                    System.out.println("Invalid Choice.");

            }

        }
    }
}