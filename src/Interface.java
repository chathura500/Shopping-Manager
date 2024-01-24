import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Interface extends JFrame {
    public static ArrayList<Object> productList = new ArrayList<>();


    ShoppingCart shoppingCart = new ShoppingCart();
    JPanel productDetailsPanel;
    JButton AddToShoppingCartButton, ShoppingCartButton;
    JComboBox<String> categoryComboBox;
    JLabel label1;
    JTable table;
    DefaultTableModel DefaultTableModel;

    JFrame AddToCartFrame; // Variable to hold the shopping cart window

    /**
     * This is the constructor of the Interface class
     */

    public Interface() {
        fistUI();
        addListeners();
        productList = new ArrayList<>();
    }

    private void addListeners() {

        /**
         * This add item listener will display the products according to the selected category
         */

        categoryComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String category = (String) e.getItem();
                switch (category) {
                    case "All" -> DisplayAllProducts();
                    case "Electronics" -> DisplayElectronics();
                    case "Clothes" -> DisplayClothes();
                }
            }
        });


        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                displayProductDetails(row);
            }
        });

        /**
         * This add action listener will add the selected product to the shopping cart
         */



        AddToShoppingCartButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String productName = (String) table.getValueAt(selectedRow, 1);
                productList.add(productName);
                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.calculateTotalCost(productList);
                AddToShoppingCart();
            }
        });




    }
    /**
     * This method will display the details of a product
     */


    private void displayProductDetails(int row) {
        productDetailsPanel.removeAll();
        String productID = (String) table.getValueAt(row, 0);
        String productName = (String) table.getValueAt(row, 1);
        int numberOfAvailableItems = 0;
        for (Product product : ShoppingCart.getProducts()) {
            if (product.getProductID().equals(productID)) {
                numberOfAvailableItems = product.getNumberOfAvailableItems();
                break; // Stop the loop once the correct product is found
            }
        }


        String category = (String) table.getValueAt(row, 2);
        Double price = (Double) table.getValueAt(row, 3);
        String info = (String) table.getValueAt(row, 4);


        JLabel detailsLabel = new JLabel("<html><b>Product Details:</b><br><br>"
                + "Product ID: " + productID + "<br>"
                + "Product Name: " + productName + "<br>"
                + "Category: " + category + "<br>"
                + "Price: " + price + "<br>"
                + "Info: " + info + "<br>"
                + "items: " + numberOfAvailableItems + "</html>"
        );

        productDetailsPanel.add(detailsLabel);
        productDetailsPanel.revalidate();
        productDetailsPanel.repaint();
    }






    private void addToShoppingCart() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String productID = (String) table.getValueAt(selectedRow, 0);
            String productName = (String) table.getValueAt(selectedRow, 1);

            int numberOfAvailableItems = 0;
            for (Product product : ShoppingCart.getProducts()) {
                if (product.getNumberOfAvailableItems()>3) {
                    setBackground(Color.red);
                    break; // Stop the loop once the correct product is found
                }
            }

            Double price = (Double) table.getValueAt(selectedRow, 3);
            String info = (String) table.getValueAt(selectedRow, 4);

            if ("Size".equals(info)) {
                int size = (int) table.getValueAt(selectedRow, 5);
                String color = (String) table.getValueAt(selectedRow, 6);
                Clothing clothing = new Clothing(productID, productName, numberOfAvailableItems, price, size, color);
                shoppingCart.addProduct(clothing);
            } else if ("Warranty Period".equals(info)) {
                String brand = (String) table.getValueAt(selectedRow, 5);
                int warrantyPeriod = (int) table.getValueAt(selectedRow, 6);
                Electronics electronics = new Electronics(productID, productName, numberOfAvailableItems, price, brand, warrantyPeriod);
                shoppingCart.addProduct(electronics);
            }
        }
    }




    /**
     * This method will create the first window of the application
     */

    public void fistUI() {
        JFrame frame = new JFrame("Westminster Shopping Center");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        ShoppingCartButton = new JButton("Shopping Cart");
//        ShoppingCartButton.addActionListener(ActionListener -> displayShoppingCart());



        label1 = new JLabel("Select Product Category ");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setVerticalAlignment(JLabel.TOP);

        String[] categories = {"All", "Electronics", "Clothes"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setSelectedIndex(-1); // Set no selection by default

        String[] columnNames = {"Product ID", "Product Name", "category", "Price", "info"};
        DefaultTableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(DefaultTableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 200));

        JPanel labelAndComboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelAndComboBoxPanel.add(label1);
        labelAndComboBoxPanel.add(categoryComboBox);

        productDetailsPanel = new JPanel();
        productDetailsPanel.setLayout(new BoxLayout(productDetailsPanel, BoxLayout.Y_AXIS));

        AddToShoppingCartButton = new JButton("Add to Shopping Cart");


        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(AddToShoppingCartButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        frame.add(ShoppingCartButton, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        frame.add(labelAndComboBoxPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(scrollPane, gbc);

        gbc.gridy = 6;
        gbc.weighty = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;
        frame.add(panel1, gbc);

        gbc.gridy = 6;
        gbc.weighty = 3;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(productDetailsPanel, gbc);

        frame.setVisible(true);
    }

    /**
     * This method will display all the products in the cart
     */

    public void DisplayAllProducts() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Product product : ShoppingCart.getProducts()) {
            String category = product.getProductID().startsWith("E") ? "Electronics" : "Clothing";
            String details = "";
            if (product instanceof Electronics electronic) {
                details = "Brand: " + electronic.getBrand() + ", Warranty Period: " + electronic.getWarrantyPeriod();
            } else if (product instanceof Clothing clothing) {
                details = "Size: " + clothing.getSize() + ", Color: " + clothing.getColor();
            }
            Object[] rowData = {product.getProductID(), product.getProductName(), category, product.getPrice(), details};
            model.addRow(rowData);


        }
    }
    /**
     * This method will display all the electronics in the cart
     */

    public void DisplayElectronics() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Product product : ShoppingCart.getProducts()) {
            if (product instanceof Electronics electronic) {
                Object[] rowData = {electronic.getProductID(), electronic.getProductName(), "Electronics", electronic.getPrice(), "Brand: " + electronic.getBrand() + ", Warranty Period: " + electronic.getWarrantyPeriod()};
                model.addRow(rowData);
            }
        }

    }
    /**
     * This method will display all the clothes in the cart
     */
    public void DisplayClothes() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Product product : ShoppingCart.getProducts()) {
            if (product instanceof Clothing clothing) {
                Object[] rowData = {clothing.getProductID(), clothing.getProductName(), "Clothing", clothing.getPrice(), "Size: " + clothing.getSize() + ", Color: " + clothing.getColor()};
                model.addRow(rowData);
            }
        }
    }

    /**
     * This method will update the quantity of products in the cart
     */

    private void updateCartData(Object[][] cart, int qty) {
        for (int i = 0; i < productList.size(); i++) {
            Object prodName = productList.get(i);

            for (Product prod : ShoppingCart.getProducts()) {
                if (prod.getProductName().equals(prodName)) {
                    String prodDetails = prod.getProductID() + "," + prod.getProductName();

                    // Check if the product is already in the cart
                    int j = findProdInCart(cart, prodDetails);
                    if (j != -1) {
                        updateProdInCart(cart, j, prod);
                    } else {
                        addProdToCart(cart, i, prodDetails, prod);
                    }

                    break;
                }
            }
        }
    }

    /**
     * This method will find the index of a product in the cart
     */

    private int findProdInCart(Object[][] cart, String prodDetails) {
        for (int j = 0; j < cart.length; j++) {
            if (cart[j][0] != null && cart[j][0].equals(prodDetails)) {
                return j;
            }
        }
        return -1;
    }

    /**
     * This method will update the quantity of an existing product in the cart
     */

    private void updateProdInCart(Object[][] cart, int index, Product prod) {
        int existingQty = (int) cart[index][1];
        int qty = existingQty + 1;
        double totalCost = qty * prod.getPrice();
        cart[index][1] = qty;
        cart[index][2] = totalCost;
    }

    /**
     * This method will add a new product to the cart
     */

    private void addProdToCart(Object[][] cart, int index, String prodDetails, Product prod) {
        int qty = 1;
        double totalCost = qty * prod.getPrice(); // Calculate total price
        cart[index][0] = prodDetails;
        cart[index][1] = qty;
        cart[index][2] = totalCost;
    }


    /**
     * This method will create a new window to display the shopping cart
     */

    private void AddToShoppingCart() {
        AddToCartFrame = new JFrame("Shopping Cart");
        AddToCartFrame.setLayout(new BorderLayout());

        Object[][] cartData = getCartData();
        JTable AddToCartTable = new JTable(cartData, new String[]{"Product", "Quantity", "Price"});
        JScrollPane cartScrollPane = new JScrollPane(AddToCartTable);

        JPanel lowerpanel = lowerPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, cartScrollPane, lowerpanel);
        splitPane.setResizeWeight(0.5); // This will make the top component take up half the height

        AddToCartFrame.add(splitPane, BorderLayout.CENTER);
        AddToCartFrame.pack();
        AddToCartFrame.setVisible(true);
    }

    private Object[][] getCartData() {
        Object[][] cartData = new Object[productList.size()][3];
        updateCartData(cartData, 1);
        return cartData;
    }

    private JPanel lowerPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        String[] labels = {"Total:", "First Purchase Discount(10%):", "Three Items in the same category Discount(20%):", "Final Total:"};
        JLabel[] values = new JLabel[labels.length];

        double totalCost = calculateTotalCost(productList);
        double[] calculations = {totalCost, totalCost * 0.1, totalCost * 0.2, totalCost - (totalCost * 0.1 + totalCost * 0.2)};

        for (int i = 0; i < labels.length; i++) {
            p.add(new JLabel(labels[i]));
            values[i] = new JLabel(String.valueOf(calculations[i]));
            p.add(values[i]);
        }

        return p;
    }



    private double calculateTotalCost(ArrayList<Object> cart) {
        double totalCost=0.0;

        for (Object productName : cart) {
            for (Product product : ShoppingCart.getProducts()) {
                if (product.getProductName().equals(productName)) {
                    double productPrice = product.getPrice();
                    totalCost += productPrice;
                    break;
                }
            }
        }
        return totalCost;

    }





    public static void main(String[] arg) {

        Interface c = new Interface();
        c.fistUI();
    }
}
