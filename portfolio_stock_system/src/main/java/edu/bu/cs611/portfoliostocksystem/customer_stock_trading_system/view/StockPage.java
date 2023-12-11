import javax.swing.*;
import java.awt.*;

public class StockPage extends JFrame {

    private JLabel customerIdLabel;
    private JTextField customerIdField;
    private JLabel customerNameLabel;
    private JTextField customerNameField;
    private JLabel accountNumberLabel;
    private JTextField accountNumberField;
    private JLabel totalFundsLabel;
    private JTextField totalFundsField;
    private JLabel realizedPnLLabel;
    private JTextField realizedPnLField;
    private JLabel unrealizedPnLLabel;
    private JTextField unrealizedPnLField;
    private JTable purchaseHistoryTable;
    private DefaultTableModel purchaseHistoryModel;

    public StockPage() {
        setTitle("Stock Page UI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        customerIdLabel = new JLabel("Customer ID:");
        customerIdField = new JTextField(20);
        customerIdField.setEditable(false);  // Assuming the customer ID is not editable

        customerNameLabel = new JLabel("Customer Name:");
        customerNameField = new JTextField(20);
        customerNameField.setEditable(false);  // Assuming the customer name is not editable

        accountNumberLabel = new JLabel("Account Number:");
        accountNumberField = new JTextField(20);
        accountNumberField.setEditable(false);  // Assuming the account number is not editable

        totalFundsLabel = new JLabel("Total Funds:");
        totalFundsField = new JTextField(20);
        totalFundsField.setEditable(false);  // Assuming total funds is a calculated field

        realizedPnLLabel = new JLabel("Realized P&L:");
        realizedPnLField = new JTextField(20);
        realizedPnLField.setEditable(false);  // Assuming P&L is a calculated field

        unrealizedPnLLabel = new JLabel("Unrealized P&L:");
        unrealizedPnLField = new JTextField(20);
        unrealizedPnLField.setEditable(false);  // Assuming P&L is a calculated field

        // Set up the purchase history table
        String[] columnNames = {"Stock ID", "Buy ($)", "Sell ($)", "P/L"};
        Object[][] data = {};  // Placeholder for actual data
        purchaseHistoryModel = new DefaultTableModel(data, columnNames);
        purchaseHistoryTable = new JTable(purchaseHistoryModel) {
            // Override table cell renderer to color code profit/loss
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (column == 3) {  // Assuming 3rd column is P/L
                    Double value = (Double) getValueAt(row, column);
                    if (value < 0) {
                        c.setForeground(Color.RED);
                    } else {
                        c.setForeground(new Color(0, 128, 0));  // Dark green for profit
                    }
                } else {
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        };

        // Add components to the pane
        add(customerIdLabel);
        add(customerIdField);
        add(customerNameLabel);
        add(customerNameField);
        add(accountNumberLabel);
        add(accountNumberField);
        add(totalFundsLabel);
        add(totalFundsField);
        add(realizedPnLLabel);
        add(realizedPnLField);
        add(unrealizedPnLLabel);
        add(unrealizedPnLField);
        add(new JScrollPane(purchaseHistoryTable));  // Scroll pane for table
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StockPage());
    }
}
