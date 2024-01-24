import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Store {
    public static void main(String[] args) {
        Store store = new Store();
        store.run();
    }

    private void run() {
        JFrame frame = new JFrame("Store Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        placeComponents(panel);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel idLabel = new JLabel("Product ID:");
        idLabel.setBounds(10, 20, 80, 25);
        panel.add(idLabel);

        JTextField idText = new JTextField(20);
        idText.setBounds(100, 20, 165, 25);
        panel.add(idText);

        JButton deleteButton = new JButton("Delete Product");
        deleteButton.setBounds(10, 50, 165, 25);
        panel.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String delete = idText.getText();
                deleteProduct(delete);
            }
        });

        // Add other components like addProductButton, saveButton, etc.
    }

    private void deleteProduct(String delete) {
        // Code to delete the product with the given ID
    }

    // Other methods like addProduct, saveInFile, etc.
}