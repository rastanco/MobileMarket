package ir.rastanco.mobilemarket.utility;

/**
 * Created by ShaisteS on 02/16/2016.
 */
public class DataFilter {

    private static DataFilter data = new DataFilter();

    public static DataFilter getConfig() {
        if (data != null) {
            return data;
        } else return new DataFilter();
    }
    public static String FilterCategory="";
}
