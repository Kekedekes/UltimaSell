/*
 * Decompiled with CFR 0.151.
 */
package me.xd.ultimasell.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NumberFormat {
    private static final DecimalFormat numberFormat = new DecimalFormat("#,###.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
    private static final DecimalFormat numberFormat0 = new DecimalFormat("####.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
    private static final List<String> chars = Arrays.asList("", "K", "M", "B", "T", "Q", "QQ", "S", "SS", "O", "N", "D", "UN", "DD", "TR", "QT", "QN", "SD", "SPD", "OD", "ND", "VG", "UVG", "DVG", "TVG", "QTV");

    public static String formatNumber(double number) {
        return numberFormat.format(number);
    }

    public static String formatNumber0(double number) {
        return numberFormat0.format(number);
    }

    public static String formatNumber(int number) {
        return numberFormat.format(number);
    }

    public static String format(double number) {
        int index = 0;
        while (number / 1000.0 >= 1.0) {
            number /= 1000.0;
            ++index;
        }
        String character = index < chars.size() ? chars.get(index) : "";
        return String.valueOf(numberFormat.format(number)) + character;
    }

    public static Double unformatNumber(String number) {
        if (!(number = number.replace(",", ".")).matches("^\\d{1,3}([a-zA-Z]+)?([.]\\d{1,2}([\\D]+$)?)?")) {
            return 0.0;
        }
        String numberFormatted = number.replaceAll("([\\D]+$)", "");
        String last = number.replaceAll("(\\d+)([.])?(\\d+)?", "");
        try {
            int index = chars.contains(last) ? chars.indexOf(last) : 0;
            double value = Double.parseDouble(numberFormatted);
            return value * Math.pow(1000.0, index);
        }
        catch (Exception e) {
            return 0.0;
        }
    }
}

