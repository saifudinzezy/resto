package com.example.saifudin.morapos.helper;

import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatRp {
    public static String formatRp(String value){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        double price = Double.parseDouble((value));
        int x = (int) price;
        return formatRupiah.format((Double.valueOf("" + x)));
    }

    public static void formatRp2(int value, TextView textView){
        double harga = value;

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        textView.setText(kursIndonesia.format(harga));
    }
}