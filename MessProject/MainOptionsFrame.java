package MessProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Desktop;

public class MainOptionsFrame {
    JFrame optionsFrame;

    public MainOptionsFrame() {
        optionsFrame = new JFrame("GSV Options");
        optionsFrame.setSize(600, 500);
        optionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionsFrame.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("D:\\GSV_Logo.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
        logoLabel.setIcon(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel headingLabel = new JLabel("GSV Mess Management System", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 45));
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(logoLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(headingLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        optionsFrame.add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.CENTER;

        JButton messSkipButton = new JButton("Mess Skip");
        messSkipButton.setPreferredSize(new Dimension(200, 50));
        messSkipButton.setFont(new Font("Arial", Font.BOLD, 18));
        messSkipButton.addActionListener(e -> new GUIDesign());

        JButton complainButton = new JButton("Complaint/Suggestion");
        complainButton.setPreferredSize(new Dimension(250, 50));
        complainButton.setFont(new Font("Arial", Font.BOLD, 18));
        complainButton.addActionListener(e -> new ComplaintForm());

        JButton messMenuButton = new JButton("Mess Menu");
        messMenuButton.setPreferredSize(new Dimension(200, 50));
        messMenuButton.setFont(new Font("Arial", Font.BOLD, 18));
        messMenuButton.addActionListener(e -> openPDF());

        gbc.gridy = 0;
        buttonPanel.add(messSkipButton, gbc);
        gbc.gridy++;
        buttonPanel.add(complainButton, gbc);
        gbc.gridy++;
        buttonPanel.add(messMenuButton, gbc);

        optionsFrame.add(buttonPanel, BorderLayout.CENTER);

        optionsFrame.getContentPane().add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.WEST);
        optionsFrame.getContentPane().add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.EAST);

        optionsFrame.setVisible(true);
    }

    private void openPDF() {
        String filePath = "D:\\GSVMessMenu.pdf";
        File file = new File(filePath);
        if (file.exists()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error opening the file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new MainOptionsFrame();
    }
}
