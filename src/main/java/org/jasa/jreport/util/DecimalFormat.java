package org.jasa.jreport.util;

import java.text.DecimalFormatSymbols;

/**
 * Utility class for creating decimal formatting for {@link Float},
 * {@link Double}, {@link java.math.BigDecimal} data types.
 * 
 * @author Leonardo Sanchez J.
 */
public class DecimalFormat {
    private String pattern;
    private DecimalFormatSymbols symbols;

    private DecimalFormat() {
        this.pattern = "";
        this.symbols = new DecimalFormatSymbols();
    }

    public String getPattern() {
        return this.pattern;
    }

    public java.text.DecimalFormat getTextDecimalFormat() {
        try {
            java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(this.pattern, this.symbols);
            return decimalFormat;
        } catch (Exception e) {
            return null;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private DecimalFormat decimalFormat;

        private Builder() {
            decimalFormat = new DecimalFormat();
        }

        /**
         * Set the formatting pattern. Example: "¤#,##0.00"
         * 
         * @param pattern
         * @return
         */
        public Builder pattern(String pattern) {
            decimalFormat.pattern = pattern;
            return this;
        }

        /**
         * Set the decimal separator. Example: '.'
         * 
         * @param decimalSeparator
         * @return
         */
        public Builder decimalSeparator(char decimalSeparator) {
            decimalFormat.symbols.setDecimalSeparator(decimalSeparator);
            return this;
        }

        /**
         * Set the thousands separator. Example: ','
         * 
         * @param groupingSeparator
         * @return
         */
        public Builder groupingSeparator(char groupingSeparator) {
            decimalFormat.symbols.setGroupingSeparator(groupingSeparator);
            return this;
        }

        /**
         * Set the currency symbol. Example: "€", "$", "₡", "¥"
         * 
         * @param currencySymbol
         * @return
         */
        public Builder currencySymbol(String currencySymbol) {
            decimalFormat.symbols.setCurrencySymbol(currencySymbol);
            return this;
        }

        public DecimalFormat build() {
            return decimalFormat;
        }
    }
}