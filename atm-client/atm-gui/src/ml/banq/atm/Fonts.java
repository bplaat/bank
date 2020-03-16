package ml.banq.atm;

import java.awt.Font;
import javax.swing.JLabel;

public class Fonts {
    public static final Font DEFAULT = new JLabel().getFont();
    public static final Font HEADER = new Font(DEFAULT.getName(), Font.BOLD, App.getInstance().getWindowWidth() / 100 * 4);
    public static final Font NORMAL = new Font(DEFAULT.getName(), Font.PLAIN, App.getInstance().getWindowWidth() / 100 * 2);
}
