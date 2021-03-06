package ml.banq.atm;

import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

// The admin write pincode page
public class AdminWritePincodePage extends Page {
    private static final long serialVersionUID = 1;

    private String accountId;
    private JLabel messageLabel;
    private JPasswordField pincodeInput;
    private JPasswordField pincodeConfirmationInput;

    public AdminWritePincodePage(String accountId) {
        this.accountId = accountId;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(Box.createVerticalGlue());

        // Create the page title
        JLabel titleLabel = new JLabel(Language.getString("admin_write_pincode_page_title"));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(Fonts.HEADER);
        add(titleLabel);

        add(Box.createVerticalStrut(Paddings.LARGE));

        // Create the page message label
        JLabel messageLabel = new JLabel(Language.getString("admin_write_pincode_page_message"));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setFont(Fonts.NORMAL);
        add(messageLabel);

        add(Box.createVerticalStrut(Paddings.LARGE));

        // Create the page new pincode input label
        JLabel pincodeLabel = new JLabel(Language.getString("admin_write_pincode_page_pincode_input"));
        pincodeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pincodeLabel.setFont(Fonts.NORMAL);
        add(pincodeLabel);

        add(Box.createVerticalStrut(Paddings.NORMAL));

        // Create the page new pincode input field
        pincodeInput = new JPasswordField(4);
        pincodeInput.setFont(Fonts.NORMAL);
        pincodeInput.setHorizontalAlignment(JPasswordField.CENTER);
        pincodeInput.setMaximumSize(pincodeInput.getPreferredSize());
        add(pincodeInput);

        add(Box.createVerticalStrut(Paddings.LARGE));

        // Create the page new pincode confirmation input label
        JLabel pincodeConfirmationLabel = new JLabel(Language.getString("admin_write_pincode_page_pincode_confirmation_input"));
        pincodeConfirmationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pincodeConfirmationLabel.setFont(Fonts.NORMAL);
        add(pincodeConfirmationLabel);

        add(Box.createVerticalStrut(Paddings.NORMAL));

        // Create the page new pincode cofirmation input field
        pincodeConfirmationInput = new JPasswordField(4);
        pincodeConfirmationInput.setFont(Fonts.NORMAL);
        pincodeConfirmationInput.setHorizontalAlignment(JPasswordField.CENTER);
        pincodeConfirmationInput.setMaximumSize(pincodeConfirmationInput.getPreferredSize());
        add(pincodeConfirmationInput);

        add(Box.createVerticalStrut(Paddings.LARGE));

        // Create the input button box
        JPanel buttonsBox = new JPanel(new FlowLayout(FlowLayout.CENTER, Paddings.NORMAL, 0));
        buttonsBox.setMaximumSize(new Dimension(App.getInstance().getWindowWidth() / 2, 0));
        add(buttonsBox);

        // Create the continue button
        JButton continueButton = new JButton(Language.getString("admin_write_pincode_page_continue_button"));
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setFont(Fonts.NORMAL);
        continueButton.addActionListener((ActionEvent event) -> {
            checkPincode();
        });
        buttonsBox.add(continueButton);

        // Create the back button
        JButton backButton = new JButton(Language.getString("admin_write_pincode_page_back_button"));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setFont(Fonts.NORMAL);
        backButton.addActionListener((ActionEvent event) -> {
            // Go back to previous page
            Navigator.getInstance().changePage(new AdminWriteAccountsPage(false));
        });
        buttonsBox.add(backButton);

        add(Box.createVerticalGlue());
    }

    // Check the pincode then go to the next page
    public void checkPincode() {
        String pincode = new String(pincodeInput.getPassword());
        if (pincode.matches("[0-9]{4}")) {
            if (pincode.equals(new String(pincodeConfirmationInput.getPassword()))) {
                Navigator.getInstance().changePage(new AdminWriteRFIDPage(accountId, pincode));
            } else {
                messageLabel.setFont(Fonts.NORMAL_BOLD);
                messageLabel.setText(Language.getString("admin_write_pincode_page_error"));
            }
        } else {
            messageLabel.setFont(Fonts.NORMAL_BOLD);
            messageLabel.setText(Language.getString("admin_write_pincode_page_message"));
        }
    }

    public void onKeypad(String key) {
        // When pressed continue to write page
        if (key.equals("#")) {
            checkPincode();
        }

        // Go back to previous page
        if (key.equals("D")) {
            Navigator.getInstance().changePage(new AdminWriteAccountsPage(false));
        }
    }
}
