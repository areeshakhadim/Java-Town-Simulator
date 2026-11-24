import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TreeVisualization {

    // Tree Node Class
    static class TreeNode {
        String data;
        int price;
        TreeNode left, right;

        public TreeNode(String data, int price) {
            this.data = data;
            this.price = price;
            this.left = null;
            this.right = null;
        }
    }

    // Method to show the tree
    private static void showTree() {
        // Create the tree nodes
        TreeNode root = new TreeNode("Pencil", 1);
        root.left = new TreeNode("Book", 3);
        root.right = new TreeNode("Pen", 2);

        root.left.left = new TreeNode("Pointer", 2);
        root.left.right = new TreeNode("Marker", 1);

        root.right.left = new TreeNode("Copy", 2);
        root.right.right = new TreeNode("Scale", 2);

        // Show tree in a new frame
        JFrame treeFrame = new JFrame("");
        treeFrame.setSize(900, 550); // Set the frame size to 900x550
        treeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        treeFrame.setLayout(new BorderLayout());

        // Add the button in the top-right corner
        JButton topRightButton = new JButton("Next");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Right-aligned layout
        topPanel.add(topRightButton);
        treeFrame.add(topPanel, BorderLayout.NORTH);

        // Action listener to open the "Stationary_shop" page when the button is clicked
        topRightButton.addActionListener((ActionEvent e) -> {
            // Call to open the Stationary_shop page
            // Assuming Stationary_shop has a frame setup
            treeFrame.dispose(); // Close the current TreeVisualization frame
            Stationary_shop.main(null);
        });

        // Create and add the tree panel
        JPanel treePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the tree starting from the root
                drawTree(g, root, 450, 150, 150); // Adjusted position for smaller frame size
            }
        };

        treeFrame.add(treePanel, BorderLayout.CENTER);

        // Center the frame on the screen
        treeFrame.setLocationRelativeTo(null);

        treeFrame.setVisible(true);
    }

    // Method to draw the tree
    private static void drawTree(Graphics g, TreeNode node, int x, int y, int xOffset) {
        if (node == null) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw the lines to the children first, so the node boxes sit on top of them
        if (node.left != null) {
            g2.setColor(new Color(0x868E96));
            g2.setStroke(new BasicStroke(2f));
            g.drawLine(x, y, x - xOffset, y + 80);
            drawTree(g, node.left, x - xOffset, y + 80, xOffset / 2);
        }
        if (node.right != null) {
            g2.setColor(new Color(0x868E96));
            g2.setStroke(new BasicStroke(2f));
            g.drawLine(x, y, x + xOffset, y + 80);
            drawTree(g, node.right, x + xOffset, y + 80, xOffset / 2);
        }

        // Draw the current node as a rounded box with its name and price
        String label = node.data + " ($" + node.price + ")";
        g.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(label) + 24, h = 36;
        g.setColor(Theme.NODE);
        g.fillRoundRect(x - w / 2, y - h / 2, w, h, 18, 18);
        g.setColor(Theme.PRIMARY);
        g2.setStroke(new BasicStroke(2f));
        g.drawRoundRect(x - w / 2, y - h / 2, w, h, 18, 18);
        g.setColor(Theme.TEXT);
        g.drawString(label, x - fm.stringWidth(label) / 2, y + fm.getAscent() / 2 - 2);
    }


    // Main method
    public static void main(String[] args) {
        Theme.apply();
        showTree(); // Call the method to display the tree
    }
}
