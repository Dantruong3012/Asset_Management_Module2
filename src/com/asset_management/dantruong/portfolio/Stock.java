package com.asset_management.dantruong.portfolio;

import com.asset_management.dantruong.trasaction.Asset;

public class Stock extends Asset {
    private String major;

    public Stock(String major, String companyName, String symBol, double currentPrice) {
        super(companyName, symBol, currentPrice);
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    

    @Override
    public String getType() {
        return "STOCK";
    }

    @Override
    public String toString() {
        return "-----------------------------" +
               "\n  Stock Code: " + getSymBol() +
               "\n  Company Name: " + getCompanyName() +
               "\n  Sector: " + getMajor() +
               "\n  Quantities: " + calculateTotalQuantities() +
               "\n  Last Transaction Price: " + getLastTransactionPrice() +
               "\n  Market Price: " + getCurrentMarketPrice() +
               "\n  Average Price: " + averagePrice() +
               "\n  Total Cost: " + calculateTotalTransactionValue() +
               "\n  Market Value: " + totalValueWithMarketPrice() +
               "\n  Profit/Loss: " + calculateProfitLoss() +
               "\n---------------------------";
    }

}
