import javax.swing.*;
import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager{
    private ArrayList<Product> products = new ArrayList<>();

    boolean running = true;



    public void displayMenu() {
        while (running) {
            System.out.println("\n==================== MENU ==================");
            System.out.println("1. Add new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print the list products");
            System.out.println("4. Save products to a file");
            System.out.println("5. load products from file");
            System.out.println("6. Open GUI");
            System.out.println("0. Exit");
            System.out.println("============================================");
            System.out.print("Select option: ");

            Scanner sc = new Scanner(System.in);
            try {
                int option = sc.nextInt();

                switch (option) {
                    case 1 -> addNewProduct();
                    case 2 -> deleteAProduct();
                    case 3 -> printTheListOfProduct();
                    case 4 -> saveInFile();
                    case 5 -> readBackAllInformation();
                    case 6 -> {
                        System.out.println("Opening GUI");
                        new Interface();
                    }
                    case 0 -> {
                        System.out.println("You have Chosen to exit the program");
                        running = false;
                    }
                    default -> {
                        System.out.println("Invalid option. Please enter a number between 0 and 6.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); // discard the invalid input
            }
        }
    }




    @Override
    public void addNewProduct() {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        try {
            System.out.print("Select (clothing or electronics)\n");
            System.out.print("Press C for cloths and E for electronics --> ");
            char option = scanner.nextLine().charAt(0);

            if (option == 'c' || option == 'C') {
                System.out.print("\nYou have chosen Clothing\n");
                int randomNum = rand.nextInt(9000)+1000;
                String productId = "C" + Integer.toString(randomNum);
                System.out.println("Your product id is: " + productId);
                try {
                    System.out.print("Enter product name: ");
                    String productName = scanner.next();
                    System.out.print("Enter number of available items: ");
                    int numberOfAvailableItems = scanner.nextInt();
                    System.out.print("Enter Price: ");
                    Double price = scanner.nextDouble();
                    System.out.print("Enter size: ");
                    int size = scanner.nextInt();
                    System.out.print("Enter Color: ");
                    String color = scanner.next();

                    Clothing clothing = new Clothing(productId, productName, numberOfAvailableItems, price, size, color);
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.addProduct(clothing);

                    if (products.size() >= 50) {
                        System.out.println("products reached it's limits");
                        displayMenu();
                    } else if (products.add(clothing)) {
                        System.out.println("Products successfully added");

                    } else {
                        System.out.println("An error occurred try again");
                        addNewProduct();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please try again.");
                    addNewProduct();
                }

            } else if (option == 'e' || option == 'E') {
                System.out.println("\n You have chosen electronics\n");
                int randomNum = rand.nextInt(9000)+1000;
                String productId = "E" + Integer.toString(randomNum);
                System.out.println("Your product id is: " + productId);
                try {
                    System.out.print("Enter product name: ");
                    String productName = scanner.next();
                    System.out.print("Enter number of available items: ");
                    int numberOfAvailableItems = scanner.nextInt();
                    System.out.print("Enter Price: ");
                    Double price = scanner.nextDouble();
                    System.out.print("Enter the brand: ");
                    String brand = scanner.next();
                    System.out.print("Enter the warranty period example(In years Like 1 or 2 years): ");
                    int warrantyPeriod = scanner.nextInt();

                    Electronics electronics = new Electronics(productId, productName, numberOfAvailableItems, price, brand, warrantyPeriod);
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.addProduct(electronics);

                    if (products.size() >= 50) {
                        System.out.println("products reached it's limits");
                    } else if (products.add(electronics)) {
                        System.out.println("Products successfully added");
                    } else {
                        System.out.println("An error occurred try again");
                        addNewProduct();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please try again.");
                    addNewProduct();
                }
            } else {
                System.out.println("invalid option.try again");
                addNewProduct();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            addNewProduct();
        }
    }

    @Override
    public void deleteAProduct() {
        ShoppingCart cart = new ShoppingCart();
        Scanner sc = new Scanner(System.in);
        System.out.println("List of all the product IDs --> ");
        for (int i = 0; i < products.size(); i++){
            System.out.println(products.get(i).getProductID());
        }

        System.out.print("Choose your id to delete: ");
        String delete = sc.next();

        ArrayList<Product> deletedProducts = new ArrayList<>();
        for(int i = 0 ;i < products.size(); i++){
            if (products.get(i).getProductID().equals(delete)){
                deletedProducts.add(products.get(i));
                products.remove(i);
                System.out.println("removed Successfully: " + delete);

            }else{
                System.out.println("No product ID found: " + delete );
            }

        }

        //print the arraylist after deleting
        System.out.println("List of remaining the product IDs --> ");
        for (Product product : products) {
            System.out.println(product.getProductID());
        }
    }

    @Override
    public void printTheListOfProduct() {

        for (int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            System.out.println("\nProduct " + (i+1) + ":");
            System.out.println("============================================");
            System.out.println("Product Id: " + product.getProductID());
            System.out.println("Product name: " + product.getProductName());
            System.out.println("Number of available items: " + product.getNumberOfAvailableItems());
            System.out.println("Product price: " + product.getPrice());

            if (product instanceof Clothing clothing){

                System.out.println("Size: " + clothing.getSize());
                System.out.println("color: "+ clothing.getColor());

            } else if (product instanceof Electronics electronic){
                System.out.println("Brand: " + electronic.getBrand());
                System.out.println("Warranty period: " + electronic.getWarrantyPeriod());
            }
        }
        System.out.println("============================================");
    }

    @Override
    public void saveInFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Products.txt"))) {
            for (int i = 0; i < products.size(); i++){
                Product product = products.get(i);
                System.out.println("\nProduct " + (i+1) + ":");
                System.out.println("============================================");
                writer.write("Product Id: " + product.getProductID());
                writer.newLine();
                writer.write("Product name: " + product.getProductName());
                writer.newLine();
                writer.write("Number of available items: " + product.getNumberOfAvailableItems());
                writer.newLine();
                writer.write("Product price: " + product.getPrice());
                writer.newLine();

                if (product instanceof Clothing clothing){
                    writer.write("Size: " + clothing.getSize());
                    writer.newLine();
                    writer.write("Color: "+ clothing.getColor());
                    writer.newLine();
                } else if (product instanceof Electronics electronic){
                    writer.write("Brand: " + electronic.getBrand());
                    writer.newLine();
                    writer.write("Warranty period: " + electronic.getWarrantyPeriod());
                    writer.newLine();
                }

                writer.newLine(); // Add an empty line between products
            }
            System.out.println("============================================");
            System.out.println("Successfully saved to file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void readBackAllInformation() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Products.txt"))) {
            products = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line starts with "Product Id: ", if so, create a new product
                if (line.startsWith("Product Id: ")) {
                    String productId = line.substring("Product Id: ".length());
                    String productName = reader.readLine().substring("Product name: ".length());
                    int numberOfAvailableItems = Integer.parseInt(reader.readLine().substring("Number of available items: ".length()));
                    Double price = Double.parseDouble(reader.readLine().substring("Product price: ".length()));

                    // Check if the next line is a clothing or electronics, if so, create a new product
                    String nextLine = reader.readLine();
                    if (nextLine.startsWith("Size: ")) {
                        int size = Integer.parseInt(nextLine.substring("Size: ".length()));
                        String color = reader.readLine().substring("Color: ".length());
                        Clothing clothing = new Clothing(productId, productName, numberOfAvailableItems, price, size, color);
                        products.add(clothing);
                        ShoppingCart shoppingCart = new ShoppingCart();
                        shoppingCart.addProduct(clothing);
                    } else if (nextLine.startsWith("Brand: ")) {
                        String brand = nextLine.substring("Brand: ".length());
                        int warrantyPeriod = Integer.parseInt(reader.readLine().substring("Warranty period: ".length()));
                        Electronics electronics = new Electronics(productId, productName, numberOfAvailableItems, price, brand, warrantyPeriod);
                        products.add(electronics);
                        ShoppingCart shoppingCart = new ShoppingCart();
                        shoppingCart.addProduct(electronics);
                    }
                }
            }
            System.out.println("File successfully loaded!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {
        WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
        westminsterShoppingManager.displayMenu();


    }

}

