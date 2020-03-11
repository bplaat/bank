package ml.banq.atm;

import java.awt.Component;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class WithdrawPincodePage extends Page {
    private static final long serialVersionUID = 1;

    private JLabel messageLabel;
    private JPasswordField pincodeInput;

    public WithdrawPincodePage() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(Box.createVerticalGlue());

        JLabel titleLabel = new JLabel("Enter your pincode");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(Fonts.HEADER);
        add(titleLabel);

        add(Box.createVerticalStrut(24));

        messageLabel = new JLabel("Enter your pincode press '#' when you are finished");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setFont(Fonts.NORMAL);
        add(messageLabel);

        add(Box.createVerticalStrut(24));

        JLabel pincodeLabel = new JLabel("Pincode: ");
        pincodeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pincodeLabel.setFont(Fonts.NORMAL);
        add(pincodeLabel);

        add(Box.createVerticalStrut(16));

        pincodeInput = new JPasswordField(4);
        pincodeInput.setFont(Fonts.NORMAL);
        pincodeInput.setHorizontalAlignment(JPasswordField.CENTER);
        pincodeInput.setMaximumSize(pincodeInput.getPreferredSize());
        add(pincodeInput);

        add(Box.createVerticalGlue());
    }

    public void onKeypad(String key) {
        String pincode = new String(pincodeInput.getPassword());

        if (key.matches("[0-9]") && pincode.length() < 4) {
            pincodeInput.setText(pincode + key);
        }

        if (key.equals("*")) {
            pincodeInput.setText("");
        }

        if (pincode.length() == 4 && key.equals("#")) {
            BanqAPI.setPincode(pincode);
            String message = BanqAPI.loadActiveAccount();
            if (message.equals("success")) {
                App.sendBeeper(880, 500);
                Navigator.changePage(new WithdrawAccountPage());
            } else {
                App.sendBeeper(110, 500);
                messageLabel.setText("Error: " + message);
                pincodeInput.setText("");
            }
        }
    }
}