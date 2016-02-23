package com.orderfood.model;

import java.security.PublicKey;

public class Calculation {

    public static final double PRICE_SOUP = 2;
    public static final double PRICE_DISH = 4.5;
    public static final double PRICE_DESERT = 1.5;
    public static final double PRICE_DRINK = 2;
    public static final double PRICE_DELIVERY = 10;
    public static final int MAX = 10;

    static final double CURRENCY_EUR_TO_USD = 1.1026;
    static final double CURRENCY_EUR_TO_BGN = 1.9558;

    public static int addProduct(int number) {
        if (number >= MAX )
            return MAX;
        return ++number;
    }

    public static int removeProduct(int number) {
        if (number <= 0 )
            return 0;
        return --number;
    }

    public static double calculateTotal(int soupQunat, int dishQuant, int desertQuant, double dringQuant, boolean isForHome) {
        double cost = soupQunat * PRICE_SOUP + dishQuant * PRICE_DISH + desertQuant * PRICE_DESERT + dringQuant * PRICE_DRINK;
        if (isForHome)
            cost += PRICE_DELIVERY;
        return cost;
    }

    public static double converter(String currency, double total) {
        switch (currency) {
            case "USD":
                return total * CURRENCY_EUR_TO_USD;
            case "BGN":
                return total * CURRENCY_EUR_TO_BGN;
            default:
                return total;
        }
    }
}
