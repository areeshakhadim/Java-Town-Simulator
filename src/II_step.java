import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class II_step {

    public static void showGraph(boolean highlightPath, List<Integer[]> edgesToHighlight, String shortestPathLabel) {
        JFrame graphFrame = new JFrame(" ");
        graphFrame.setSize(900, 550);
        graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        graphFrame.setLocationRelativeTo(null);

        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Circular layout configuration
                int centerX = 450, centerY = 250, radius = 200;
                String[] nodes = {"House", "F1: House", "F2: House", "Stationary Shop", "Ice-Cream Shop", "Library"};
                int[][] edges = {
                    {0, 4, 3}, {4, 0, 6}, {4, 3, 3}, {3, 5, 2},
                    {0, 1, 4}, {1, 0, 3}, {1, 2, 2}, {2, 0, 7},
                    {2, 3, 11}, {5, 4, 1}
                };
                Point[] nodePositions = new Point[nodes.length];

                // Draw nodes as squares in a circular layout
                for (int i = 0; i < nodes.length; i++) {
                    double angle = 2 * Math.PI * i / nodes.length;
                    int x = (int) (centerX + radius * Math.cos(angle));
                    int y = (int) (centerY + radius * Math.sin(angle));
                    nodePositions[i] = new Point(x, y);

                    g.setColor(Theme.NODE);
                    g.fillRoundRect(x - 25, y - 25, 50, 50, 18, 18); // Draw rounded node
                    g.setColor(Theme.PRIMARY);
                    g2.setStroke(new BasicStroke(2f));
                    g.drawRoundRect(x - 25, y - 25, 50, 50, 18, 18); // Draw node border
                    g.setColor(Theme.TEXT);
                    g.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    g.drawString(nodes[i], x - g.getFontMetrics().stringWidth(nodes[i]) / 2, y - 33); // Node name, centred above the box
                }

                // Draw edges with offset for bidirectional lines
                for (int[] edge : edges) {
                    int from = edge[0], to = edge[1], cost = edge[2];
                    Point p1 = nodePositions[from];
                    Point p2 = nodePositions[to];

                    int dx = p2.x - p1.x;
                    int dy = p2.y - p1.y;
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    int offsetX = (int) (-dy * 10 / distance);
                    int offsetY = (int) (dx * 10 / distance);

                    // Check if the current edge should be highlighted
                    boolean isHighlighted = highlightPath && edgesToHighlight != null && containsEdge(edgesToHighlight, from, to);

                    Color edgeColor = isHighlighted ? Theme.ACCENT : new Color(0x868E96);
                    g.setColor(edgeColor);
                    g2.setStroke(new BasicStroke(isHighlighted ? 3.5f : 1.5f));

                    // Stop the line at the edge of each box so the arrowhead is visible
                    int sx = (int) (dx * 32 / distance);
                    int sy = (int) (dy * 32 / distance);

                    g.drawLine(p1.x + offsetX + sx, p1.y + offsetY + sy, p2.x + offsetX - sx, p2.y + offsetY - sy);
                    g.setColor(Theme.PRIMARY);
                    g.drawString(String.valueOf(cost), (p1.x + p2.x) / 2 + offsetX / 2 + 10, (p1.y + p2.y) / 2 + offsetY / 2 + 10);

                    // Draw arrowhead
                    g.setColor(edgeColor);
                    drawArrow(g, p1.x + offsetX + sx, p1.y + offsetY + sy, p2.x + offsetX - sx, p2.y + offsetY - sy);
                }
                
                // Draw the shortest path label at the top
                g.setColor(Color.BLACK);
            }
        };

        // Create label to show shortest path
        JLabel pathLabel = new JLabel(shortestPathLabel);
        pathLabel.setBounds(10, 10, 400, 20);
        pathLabel.setForeground(Theme.PRIMARY);
        pathLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        graphFrame.add(pathLabel);

        // Add close button at the top-right corner
        JButton closeButton = new JButton("Next");
        closeButton.setBounds(730, 30, 80, 30);
        closeButton.addActionListener((ActionEvent e) -> {
            // Close the graph page and navigate to "Stationary_shop.java"
            graphFrame.dispose();
            TreeVisualization.main(null);
            
            // Here you would load your Stationary Shop page, for example:
            // new StationaryShop().setVisible(true);
        });
        
        graphFrame.add(closeButton);
        graphFrame.add(graphPanel);
        graphPanel.setBackground(Theme.BACKGROUND);
        graphFrame.setVisible(true);
    }

    private static boolean containsEdge(List<Integer[]> edges, int from, int to) {
        for (Integer[] edge : edges) {
            if (edge[0] == from && edge[1] == to) {
                return true;
            }
        }
        return false;
    }

    private static void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int arrowSize = 10;
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int xArrow1 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int yArrow1 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
        int xArrow2 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int yArrow2 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));
        g.drawLine(x2, y2, xArrow1, yArrow1);
        g.drawLine(x2, y2, xArrow2, yArrow2);
    }

    public static List<Integer[]> findShortestPath(int[][] graph, int start, int end) {
        int n = graph.length;
        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            if (visited[node]) continue;
            visited[node] = true;

            for (int i = 0; i < n; i++) {
                if (graph[node][i] > 0 && !visited[i]) {
                    int newDist = dist[node] + graph[node][i];
                    if (newDist < dist[i]) {
                        dist[i] = newDist;
                        prev[i] = node;
                        pq.add(new int[]{i, newDist});
                    }
                }
            }
        }

        // Backtrack to find the path
        List<Integer[]> path = new ArrayList<>();
        for (int at = end; at != -1; at = prev[at]) {
            if (prev[at] != -1) {
                path.add(0, new Integer[]{prev[at], at});
            }
        }
        return path;
    }

    public static void main(String[] args) {
        Theme.apply();
        JFrame mainFrame = new JFrame("Main Window");
        mainFrame.setSize(900, 550);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setBackground(Theme.BACKGROUND);

        // Create button to show graph
        JButton showGraphButton = new JButton("Show Shortest Path");
        showGraphButton.setBounds(400, 450, 150, 30);

        // Add action listener to button
        showGraphButton.addActionListener(e -> {
            // Graph adjacency matrix
            int[][] graph = {
                {0, 4, 0, 0, 3, 0},  // from House
                {3, 0, 2, 0, 0, 0},  // from F1: House
                {7, 0, 0, 11, 0, 0}, // from F2: House
                {0, 0, 0, 0, 0, 2},  // from Stationary Shop
                {6, 0, 0, 3, 0, 0},  // from Ice-Cream Shop
                {0, 0, 0, 0, 1, 0}   // from Library
            };

            // Find shortest path from "House" (0) to "Stationary Shop" (3)
            List<Integer[]> shortestPath = findShortestPath(graph, 0, 3);

            // Create a label for the shortest path
            String shortestPathLabel = "Shortest path from House to Stationary Shop";

            // Highlight the shortest path and show the graph with the label
            showGraph(true, shortestPath, shortestPathLabel);

            // Close the main frame
            mainFrame.dispose();
        });

        // Add button to the main frame
        mainFrame.add(showGraphButton);
        mainFrame.setVisible(true);
    }
}
