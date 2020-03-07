package ml.banq.atm;

import java.awt.Component;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AuthPage extends Page {
    private static final long serialVersionUID = 1;

    public AuthPage() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(Box.createVerticalGlue());

        JLabel titleLabel = new JLabel("Authenticate yourself");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(Fonts.HEADER);
        add(titleLabel);

        add(Box.createVerticalStrut(24));

        JLabel messageLabel = new JLabel("Scan your Banq card...");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setFont(Fonts.NORMAL);
        add(messageLabel);

        add(Box.createVerticalGlue());
    }

    public void onRFID(String rfid_uid) {
        Navigator.changePage(new AmountPage());
    }
}
