package com.dattabot.rerulo.config;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by alhamwa on 11/2/17.
 */

public class Helper {
    public static int generateId(){
        return (int) (System.currentTimeMillis());
    }

    public static String convertRupiahFormat(String nominal) {
        String rupiah;

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(otherSymbols);
        rupiah = decimalFormat.format(Integer.parseInt(nominal));
        return rupiah;
    }
}
