package model;

import java.util.List;

public class CurrencyExchangeResponse {
    private String table;
    private String no;
    private String effectiveDate;
    private List<Rate> rates;

    // Getters and setters

    public static class Rate {
        private String currency;
        private String code;
        private double mid;

        // Getters and setters

        @Override
        public String toString() {
            return "Rate{" +
                    "currency='" + currency + '\'' +
                    ", code='" + code + '\'' +
                    ", mid=" + mid +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CurrencyExchangeResponse{" +
                "table='" + table + '\'' +
                ", no='" + no + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", rates=" + rates +
                '}';
    }
}
