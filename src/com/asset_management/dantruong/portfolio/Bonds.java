package com.asset_management.dantruong.portfolio;

import java.util.Date;

import com.asset_management.dantruong.trasaction.Asset;

public class Bonds extends Asset {
    private double interestRate;
    private Date maturityDate;

    public Bonds(double interestRate, Date maturityDate, String symBol, String companyName, double currentPrice) {
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
               "\n  Interest Rate: " + String.format("%.2f", getInterestRate()) + "%" + 
               "\n  Quantities: " + calculateTotalQuantities() +
               "\n  Last Transaction Price: " + String.format("%.2f", getLastTransactionPrice()) +
               "\n  Market Price: " + String.format("%.2f", getCurrentMarketPrice()) +
               "\n  Average Price: " + String.format("%.2f", averagePrice()) +
               "\n  Total Cost: " + String.format("%.2f", calculateTotalTransactionValue()) +
               "\n  Market Value: " + String.format("%.2f", totalValueWithMarketPrice()) +
               "\n  Profit/Loss: " + String.format("%.2f", calculateProfitLoss()) +
               "\n---------------------------";
    }

public void setInterestRate(double interestRate) {
    this.interestRate = interestRate;
}

public void setMaturityDate(Date maturityDate) {
    this.maturityDate = maturityDate;
}

    
}
