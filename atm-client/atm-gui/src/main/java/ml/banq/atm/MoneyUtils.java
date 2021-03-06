package ml.banq.atm;

import java.util.ArrayList;
import java.util.HashMap;

// The static money utils class
public class MoneyUtils {
    private MoneyUtils() {}

    // Get ruble money symbol if the font support it
    public static String getMoneySymbol() {
        return Fonts.DEFAULT.canDisplay('₽') ? "₽" : "P";
    }

    // A function the generates the diffrent money pares
    public static ArrayList<HashMap<String, Integer>> getMoneyPares(int amount) {
        ArrayList<HashMap<String, Integer>> moneyPares = new ArrayList<HashMap<String, Integer>>();

        int valuesCount = Config.ISSUE_AMOUNTS.length;

        // Create rounds
        int[][] rounds = new int[valuesCount][valuesCount];
        for (int i = 0; i < valuesCount; i++) {
            int[] newRound = new int[valuesCount];
            for (int j = 0; j < valuesCount; j++) {
                newRound[(i + j) % valuesCount] = Config.ISSUE_AMOUNTS[j];
            }
            rounds[i] = newRound;
        }

        // Run diffrent rounds
        for (int[] round : rounds) {
            int new_amount = amount;
            HashMap<String, Integer> moneyPare = new HashMap<String, Integer>();
            for (int i = valuesCount - 1; i >= 0; i--) {
                int count = 0;
                while (new_amount >= round[i]) {
                    new_amount -= round[i];
                    count++;
                }
                moneyPare.put(String.valueOf(round[i]), count);
            }
            moneyPares.add(moneyPare);
        }

        // Remove all the same money pares
        for (int i = 1; i < moneyPares.size(); i++) {
            int same = 0;

            HashMap<String, Integer> prevMoneyPare = moneyPares.get(i - 1);
            HashMap<String, Integer> moneyPare = moneyPares.get(i);
            for (int j = 0; j < Config.ISSUE_AMOUNTS.length; j++) {
                int prev = prevMoneyPare.get(String.valueOf(Config.ISSUE_AMOUNTS[j]));
                int count = moneyPare.get(String.valueOf(Config.ISSUE_AMOUNTS[j]));
                if (prev == count) {
                    same++;
                }
            }

            if (same == Config.ISSUE_AMOUNTS.length) {
                moneyPares.remove(i);
                i--;
            }
        }

        // Check all money pares if the money bills are enough
        for (int i = 0; i < moneyPares.size(); i++) {
            HashMap<String, Integer> moneyPare = moneyPares.get(i);
            for (int j = 0; j < Config.ISSUE_AMOUNTS.length; j++) {
                int count = moneyPare.get(String.valueOf(Config.ISSUE_AMOUNTS[j]));
                if (count > Settings.getInstance().getItem("bills_" + Config.ISSUE_AMOUNTS[j], 0)) {
                    moneyPares.remove(i);
                    i--;
                    break;
                }
            }
        }

        return moneyPares;
    }
}
