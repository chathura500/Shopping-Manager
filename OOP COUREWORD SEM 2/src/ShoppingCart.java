import java.util.ArrayList;

public class ShoppingCart {

    private static ArrayList<Product> products = new ArrayList<>();

    public static ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        ShoppingCart.products = products;
    }

    public void addProduct(Clothing clothing){
        products.add(clothing);


    }

    public void addProduct(Electronics electronics){
        products.add(electronics);

    }

    public void removeProduct(Product product){
        products.remove(product);
    }

    public double calculateTotalCost(ArrayList<Object> cart) {
        double totalCost = 0;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            totalCost += product.getPrice();
        }
        return totalCost;
    }

    public void printProducts() {
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                System.out.println("\nProduct " + (i + 1) + ":");
                System.out.println("============================================");
                System.out.println("Product Id: " + product.getProductID());
                System.out.println("Product name: " + product.getProductName());
                System.out.println("Number of available items: " + product.getNumberOfAvailableItems());
                System.out.println("Product price: " + product.getPrice());

                if (product instanceof Clothing clothing) {
                    System.out.println("Size: " + clothing.getSize());
                    System.out.println("Color: " + clothing.getColor());
                } else if (product instanceof Electronics electronics) {
                    System.out.println("Warranty period: " + electronics.getWarrantyPeriod());
                }
            }
    }



}
