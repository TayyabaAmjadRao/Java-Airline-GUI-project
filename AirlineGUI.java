import javax.swing.*;
import java.awt.*;
import java.io.*;

public class AirlineGUI {

    private static String name, gender;
    private static int CID, age;
    private static String address;
    private static float charges;
    private static int destinationChoice;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AirlineGUI::createMainMenu);
    }

    public static void createMainMenu() {
        JFrame frame = new JFrame("Shaheen Airways");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));

        JButton btnDetails = new JButton("Add Customer Details");
        JButton btnFlight = new JButton("Flight Registration");
        JButton btnTicket = new JButton("Show Ticket and Charges");
        JButton btnExit = new JButton("Exit");

        btnDetails.addActionListener(e -> showDetailsForm());
        btnFlight.addActionListener(e -> showFlightOptions());
        btnTicket.addActionListener(e -> {
            generateTicket();
            showTicket();
        });
        btnExit.addActionListener(e -> System.exit(0));

        frame.add(new JLabel("Welcome to Shaheen Airways", SwingConstants.CENTER));
        frame.add(btnDetails);
        frame.add(btnFlight);
        frame.add(btnTicket);
        frame.add(btnExit);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showDetailsForm() {
        JFrame frame = new JFrame("Customer Details");
        frame.setSize(350, 300);
        frame.setLayout(new GridLayout(6, 2));

        JTextField tfCID = new JTextField();
        JTextField tfName = new JTextField();
        JTextField tfAge = new JTextField();
        JTextField tfAddress = new JTextField();
        JTextField tfGender = new JTextField();

        JButton btnSubmit = new JButton("Save");

        btnSubmit.addActionListener(e -> {
            try {
                CID = Integer.parseInt(tfCID.getText());
                name = tfName.getText();
                age = Integer.parseInt(tfAge.getText());
                address = tfAddress.getText();
                gender = tfGender.getText();
                JOptionPane.showMessageDialog(frame, "Details Saved Successfully!");
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input! Please enter valid values.");
            }
        });

        frame.add(new JLabel("Customer ID:"));
        frame.add(tfCID);
        frame.add(new JLabel("Name:"));
        frame.add(tfName);
        frame.add(new JLabel("Age:"));
        frame.add(tfAge);
        frame.add(new JLabel("Address:"));
        frame.add(tfAddress);
        frame.add(new JLabel("Gender:"));
        frame.add(tfGender);
        frame.add(new JLabel());
        frame.add(btnSubmit);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showFlightOptions() {
        JFrame frame = new JFrame("Flight Registration");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(8, 1));

        String[] flights = {"Dubai", "Canada", "UK", "USA", "Australia", "Europe"};
        JComboBox<String> flightBox = new JComboBox<>(flights);

        JTextArea flightDetails = new JTextArea();
        flightDetails.setEditable(false);

        JButton btnSelect = new JButton("Show Flights");
        JButton btnBook = new JButton("Book Selected Flight");

        btnSelect.addActionListener(e -> {
            destinationChoice = flightBox.getSelectedIndex() + 1;
            String code = getAirlineCode(destinationChoice);
            flightDetails.setText("1. " + code + " - 498   09-01-2025 8:00AM  PKR 14000\n"
                    + "2. " + code + " - 658   08-01-2025 4:00AM  PKR 1000\n"
                    + "3. " + code + " - 508   11-01-2025 11:00AM PKR 9000");
        });

        btnBook.addActionListener(e -> {
            String selected = JOptionPane.showInputDialog("Enter Flight number (1/2/3):");
            switch (selected) {
                case "1" -> charges = 14000;
                case "2" -> charges = 1000;
                case "3" -> charges = 9000;
                default -> {
                    JOptionPane.showMessageDialog(frame, "Invalid selection.");
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Flight Booked! Charges: PKR " + charges);
        });

        frame.add(new JLabel("Select Destination:"));
        frame.add(flightBox);
        frame.add(btnSelect);
        frame.add(new JScrollPane(flightDetails));
        frame.add(btnBook);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void generateTicket() {
        String destination = switch (destinationChoice) {
            case 1 -> "DUBAI";
            case 2 -> "CANADA";
            case 3 -> "UK";
            case 4 -> "USA";
            case 5 -> "AUSTRALIA";
            case 6 -> "EUROPE";
            default -> "UNKNOWN";
        };

        try (PrintWriter out = new PrintWriter("record.txt")) {
            out.println("__________SHAHEEN AIRWAYS__________");
            out.println("__________Ticket__________");
            out.println("____________________________");
            out.println("Customer ID: " + CID);
            out.println("Customer Name: " + name);
            out.println("Customer Gender: " + gender);
            out.println("\nDescription\n");
            out.println("Destination\t\t" + destination);
            out.println("Flight cost:\t\tPKR " + charges);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing ticket file.");
        }
    }

    public static void showTicket() {
        JFrame frame = new JFrame("Ticket");
        frame.setSize(400, 300);
        JTextArea ticketArea = new JTextArea();
        ticketArea.setEditable(false);

        try (BufferedReader br = new BufferedReader(new FileReader("record.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                ticketArea.append(line + "\n");
            }
        } catch (IOException e) {
            ticketArea.setText("Error reading ticket file.");
        }

        frame.add(new JScrollPane(ticketArea));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static String getAirlineCode(int choice) {
        return switch (choice) {
            case 1 -> "DXB";
            case 2 -> "AC";
            case 3 -> "UK";
            case 4 -> "US";
            case 5 -> "AS";
            case 6 -> "EU";
            default -> "XX";
        };
    }
}