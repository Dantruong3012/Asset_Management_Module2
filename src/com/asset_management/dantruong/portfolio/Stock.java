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
               "\n  Last Transaction Price: " + String.format("%.2f", getLastTransactionPrice()) +
               "\n  Market Price: " + String.format("%.2f", getCurrentMarketPrice()) +
               "\n  Average Price: " + String.format("%.2f", averagePrice()) +
               "\n  Total Cost: " + String.format("%.2f", calculateTotalTransactionValue()) +
               "\n  Market Value: " + String.format("%.2f", totalValueWithMarketPrice()) +
               "\n  Profit/Loss: " + String.format("%.2f", calculateProfitLoss()) +
               "\n---------------------------";
    }

}
