public class Clothing extends Product {
    public Product clothing;
    private int size;
    private String color;

    public Clothing(String productID, String productName, int numberOfAvailableItems, Double price, int size, String  color) {
        super(productID, productName, numberOfAvailableItems, price);
        this.size = size;
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



}
