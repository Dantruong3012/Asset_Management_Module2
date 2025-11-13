package com.asset_management.dantruong.portfolio;

import java.util.Date;

import com.asset_management.dantruong.trasaction.Asset;

public class Bonds extends Asset {
    private double interestRate;
    private Date maturityDate;

    public Bonds(double interestRate, Date maturityDate, String companyName, String symBol, double currentPrice) {
        super(companyName, symBol, currentPrice);
        this.interestRate = interestRate;
        this.maturityDate = maturityDate;
    }

    public double getInterestRate() { return interestRate; }
    public Date getMaturityDate() { return maturityDate; }

    @Override
    public String getType() {
        return "BOND";
    }

    @Override
    public String toString() {
        return "------------------------------" +
               "\n  Bond Code: " + getSymBol() +
               "\n  Company/Organization: " + getCompanyName() +
               "\n  Maturity Date: " + getMaturityDate() +
               "\n  Interest Rate: " + getInterestRate() +
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
