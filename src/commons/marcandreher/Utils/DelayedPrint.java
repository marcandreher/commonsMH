package marcandreher.commons.Utils;

import marcandreher.commons.Commons.Flogger;

public class DelayedPrint {

    public DelayedPrint(String title) {
        System.out.print(Flogger.Prefix.INFO + title + " ");
    }

    public void FinishPrint(boolean status) {
        Color cl;
        String st;
        if (status) {
            cl = Color.GREEN;
            st = "✓";
        } else {
            cl = Color.RED;
            st = "X";
        }
        System.out.print("[" + cl + st + Color.RESET + "]\n");
    }

    public static void PrintValue(String type, String value, boolean status) {
        Color cl;
        if (status) {
            cl = Color.GREEN;
        } else {
            cl = Color.RED;
        }
        System.out.print(" -> " + type + " (" + cl + value + Color.RESET + ") \n");
    }



}
