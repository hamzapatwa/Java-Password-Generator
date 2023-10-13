package PasswordGen;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PasswordGenerator extends JFrame implements ActionListener, ChangeListener {
    // Define character sets for password generation
    private static final long serialVersionUID = 1L;
    private static final String CHARACTERS_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CHARACTERS_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHARACTERS_DIGITS = "0123456789";
    private static final String CHARACTERS_SPECIAL = "!@#$%^&*()_-+=<>";
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 20;

    // User interface components
    private JCheckBox upperCaseCheckbox;
    private JCheckBox lowerCaseCheckbox;
    private JCheckBox digitsCheckbox;
    private JCheckBox specialCheckbox;
    private JSlider lengthSlider;
    private JTextField lengthTextField;
    private JButton generateButton;
    private JButton copyButton;
    private JLabel passwordLabel;

    public PasswordGenerator() {
        setTitle("Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        // Initialize user interface components
        upperCaseCheckbox = new JCheckBox("Include Uppercase Letters");
        lowerCaseCheckbox = new JCheckBox("Include Lowercase Letters");
        digitsCheckbox = new JCheckBox("Include Digits");
        specialCheckbox = new JCheckBox("Include Special Characters");

        lengthSlider = new JSlider(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
        lengthSlider.setMajorTickSpacing(2);
        lengthSlider.setPaintTicks(true);
        lengthSlider.setPaintLabels(true);
        lengthSlider.addChangeListener(this);

        lengthTextField = new JTextField(2);
        lengthTextField.setText(Integer.toString(MIN_PASSWORD_LENGTH));

        generateButton = new JButton("Generate Password");
        generateButton.addActionListener(this);

        copyButton = new JButton("Copy to Clipboard");
        copyButton.addActionListener(this);

        passwordLabel = new JLabel();

        // Add components to the panel
        panel.add(upperCaseCheckbox);
        panel.add(lowerCaseCheckbox);
        panel.add(digitsCheckbox);
        panel.add(specialCheckbox);
        panel.add(new JLabel("Password Length:"));
        panel.add(lengthSlider);
        panel.add(lengthTextField);
        panel.add(generateButton);
        panel.add(copyButton);
        panel.add(passwordLabel);

        add(panel, BorderLayout.CENTER);
        pack();
        setVisible(true);
        setSize(300, 400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {

            // Get the desired password length from the user
        
            int passwordLength = Integer.parseInt(lengthTextField.getText());

            // Check if the password length is within the allowed range

            if (passwordLength < MIN_PASSWORD_LENGTH || passwordLength > MAX_PASSWORD_LENGTH) {
                JOptionPane.showMessageDialog(this, "Invalid password length. Length should be between " +
                        MIN_PASSWORD_LENGTH + " and " + MAX_PASSWORD_LENGTH + ".", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Determine which character sets to include in the password
            boolean includeUpperCase = upperCaseCheckbox.isSelected();
            boolean includeLowerCase = lowerCaseCheckbox.isSelected();
            boolean includeDigits = digitsCheckbox.isSelected();
            boolean includeSpecial = specialCheckbox.isSelected();

            if (!includeUpperCase && !includeLowerCase && !includeDigits && !includeSpecial) {
                JOptionPane.showMessageDialog(this, "Please select at least one character set.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate the password based on user preferences
            String password = generatePassword(passwordLength, includeUpperCase, includeLowerCase,            includeDigits, includeSpecial);

            // Display the generated password
            passwordLabel.setText("Generated Password: " + password);
        } else if (e.getSource() == copyButton) {

            // Copy the generated password to the system clipboard
            String password = passwordLabel.getText().replace("Generated Password: ", "");
            if (!password.isEmpty()) {
                copyToClipboard(password);
                JOptionPane.showMessageDialog(this, "Password copied to clipboard!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == lengthSlider) {
            // Update the password length text field based on the slider value
            int length = lengthSlider.getValue();
            lengthTextField.setText(Integer.toString(length));
        }
    }

    private String generatePassword(int length, boolean includeUpperCase, boolean includeLowerCase,
            boolean includeDigits, boolean includeSpecial) {
        StringBuilder characters = new StringBuilder();
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        
        // Build the character set based on user preferences
        if (includeUpperCase) {
            characters.append(CHARACTERS_UPPER);
        }
        if (includeLowerCase) {
            characters.append(CHARACTERS_LOWER);
        }
        if (includeDigits) {
            characters.append(CHARACTERS_DIGITS);
        }
        if (includeSpecial) {
            characters.append(CHARACTERS_SPECIAL);
        }

        // Generate the password by selecting random characters from the character set
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    private void copyToClipboard(String text) {
        // Copy the given text to the system clipboard
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
    }

    public static void main(String[] args) {
        // Create an instance of the PasswordGenerator class
        PasswordGenerator passwordGene = new PasswordGenerator();
    }
}




