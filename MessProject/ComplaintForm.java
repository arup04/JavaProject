package MessProject;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ComplaintForm {
    JFrame complaintFrame;
    JTextArea complaintArea;
    JTextField nameField, rollNumberField;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/messmanagement";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Arup@2004";

    public ComplaintForm() {
        complaintFrame = new JFrame("Complaint/Suggestion Form");
        complaintFrame.setSize(500, 600);
        complaintFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        complaintFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Complaint/Suggestion");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        complaintFrame.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Name Label and Field
        gbc.gridy++;
        complaintFrame.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        complaintFrame.add(nameField, gbc);

        // Roll Number Label and Field
        gbc.gridy++;
        gbc.gridx = 0;
        complaintFrame.add(new JLabel("Roll Number:"), gbc);

        gbc.gridx = 1;
        rollNumberField = new JTextField(20);
        rollNumberField.setFont(new Font("Arial", Font.PLAIN, 16));
        complaintFrame.add(rollNumberField, gbc);

        // Complaint/Suggestion TextArea
        gbc.gridy++;
        gbc.gridx = 0;
        complaintFrame.add(new JLabel("Your Complaint/Suggestion:"), gbc);

        gbc.gridx = 1;
        complaintArea = new JTextArea(5, 30);
        complaintArea.setLineWrap(true);
        complaintArea.setWrapStyleWord(true);
        complaintArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(complaintArea);
        complaintFrame.add(scrollPane, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(150, 50));
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.addActionListener(e -> submitComplaint());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(150, 50));
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.addActionListener(e -> complaintFrame.dispose());

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        complaintFrame.add(buttonPanel, gbc);

        complaintFrame.setVisible(true);
    }

    private void submitComplaint() {
        String name = nameField.getText().trim(); // Trim spaces to prevent empty inputs
        String rollNumber = rollNumberField.getText().trim();
        String complaint = complaintArea.getText().trim();

        // Validate each field individually for more specific error messages
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(complaintFrame, "Name is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return;
        }

        if (rollNumber.isEmpty()) {
            JOptionPane.showMessageDialog(complaintFrame, "Roll Number is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            rollNumberField.requestFocus();
            return;
        }

        if (complaint.isEmpty()) {
            JOptionPane.showMessageDialog(complaintFrame, "Complaint/Suggestion is required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            complaintArea.requestFocus();
            return;
        }

        // Insert the data into the database
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO complaints_suggestions (name, roll_number, complaint_or_suggestion) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, rollNumber);
            preparedStatement.setString(3, complaint);

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(complaintFrame, "Complaint/Suggestion Submitted Successfully!");
                // Clear fields after submission
                nameField.setText("");
                rollNumberField.setText("");
                complaintArea.setText("");
            } else {
                JOptionPane.showMessageDialog(complaintFrame, "Submission Failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(complaintFrame, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ComplaintForm();
    }
}