import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class Interface extends JFrame {
    public static ArrayList<Object> productList = new ArrayList<>();
    ShoppingCart shoppingCart = new ShoppingCart();
    JPanel productDetailsPanel;
    JButton AddToShoppingCartButton, ShoppingCartButton;
    JComboBox<String> categoryComboBox;
    JLabel label1;
    JTable table;
    DefaultTableModel model;

    public Interface() {
        fistUI();
        addListeners();
    }

    private void addListeners() {
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

        AddToShoppingCartButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                addToShoppingCart(row);
            }
        });

    }

    private void displayProductDetails(int row) {
        productDetailsPanel.removeAll();
        String productID = (String) table.getValueAt(row, 0);
        String productName = (String) table.getValueAt(row, 1);
        int numberOfAvailableItems = 0;
        for (Product product : ShoppingCart.getProducts()) {
            numberOfAvailableItems = product.getNumberOfAvailableItems();
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

    private void addToShoppingCart(int row) {
        String productID = (String) table.getValueAt(row, 0);
        String productName = (String) table.getValueAt(row, 1);

        int numberOfAvailableItems = 0;
        for (Product product : ShoppingCart.getProducts()) {
            numberOfAvailableItems = product.getNumberOfAvailableItems();
        }
        Double price = (Double) table.getValueAt(row, 3);
        String info = (String) table.getValueAt(row, 4);

        if (numberOfAvailableItems < 3) {
            table.setSelectionForeground(Color.RED);
        }

        if (info.equals("Size")) {
            int size = (int) table.getValueAt(row, 4);
            String color = (String) table.getValueAt(row, 4);
            Clothing clothing = new Clothing(productID, productName, numberOfAvailableItems, price, size, color);
            shoppingCart.addProduct(clothing);


        } else if (info.equals("Warranty Period")) {
            String brand = (String) table.getValueAt(row, 4);
            int warrantyPeriod = (int) table.getValueAt(row, 4);
            Electronics electronics = new Electronics(productID, productName, numberOfAvailableItems, price, brand, warrantyPeriod);
            shoppingCart.addProduct(electronics);
        }
    }



    public void fistUI() {
        JFrame frame = new JFrame("Westminster Shopping Center");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        ShoppingCartButton = new JButton("Shopping Cart");
        ShoppingCartButton.addActionListener(ActionListener -> displayShoppingCart());



        label1 = new JLabel("Select Product Category ");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setVerticalAlignment(JLabel.TOP);

        String[] categories = {"All", "Electronics", "Clothes"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setSelectedIndex(-1); // Set no selection by default

        String[] columnNames = {"Product ID", "Product Name", "category", "Price", "info"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

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

    public void displayShoppingCart() {
        // Code to display the shopping cart view
        JFrame frame = new JFrame("Shopping Cart");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        String[] columnNames1 = {"Product","Quantity","Price"};
        DefaultTableModel model1 = new DefaultTableModel(columnNames1, 0);
        JTable table1 = new JTable(model1);

        for (Product product : ShoppingCart.getProducts()) {
            if (product instanceof Electronics electronic) {
                String s1 = electronic.getProductID() + "\n" + electronic.getProductName() + "\n" + electronic.getBrand() + "\n" + electronic.getWarrantyPeriod();
                String s2 = electronic.getNumberOfAvailableItems() + "";
                String s3 = electronic.getPrice() + "";
                Object[] rowData = {s1, s2, s3};
                model1.addRow(rowData);
            } else if (product instanceof Clothing clothing) {
                String s1 = clothing.getProductID() + "\n" + clothing.getProductName() + "\n" + clothing.getSize() + "\n" + clothing.getColor();
                String s2 = clothing.getNumberOfAvailableItems() + "";
                String s3 = clothing.getPrice() + "";
                Object[] rowData = {s1, s2, s3};
                model1.addRow(rowData);
            }
        }

        JScrollPane scrollPane1 = new JScrollPane(table1);
        scrollPane1.setPreferredSize(new Dimension(500, 200));

        // Create a JPanel and add the JScrollPane to it
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(scrollPane1, BorderLayout.PAGE_START);

        // Add the JPanel to the JFrame
        frame.add(panel1);

        // Create another JPanel and add it to the bottom of the JFrame
        JPanel panel2 = new JPanel();
        frame.add(panel2 , BorderLayout.PAGE_END);
        frame.setVisible(true);

        // Implement the shopping cart functionality here
    }




    public static void main(String[] arg) {

        Interface c = new Interface();
        c.fistUI();
    }
}
