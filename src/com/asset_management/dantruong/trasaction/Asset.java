package com.asset_management.dantruong.trasaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Asset implements Serializable {
    private String companyName;
    private String symBol;
    private List<TransactionHistory> transaction;
    private double currentMarketPrice; // giá thị trường hiện tại

    public Asset(String companyName, String symBol, double currentMarketPrice) {
        this.companyName = companyName;
        this.symBol = symBol;
        this.transaction = new ArrayList<>();
        this.currentMarketPrice = currentMarketPrice;
    }

    public void addToTransaction(TransactionType TYPE, int quantities, double transactionPrice, Date date) {
        this.transaction.add(new TransactionHistory(TYPE, quantities, transactionPrice, date));
    }

    public int calculateTotalQuantities() {
        int total = 0;
        for (TransactionHistory t : transaction) {
            if (t.getType() == TransactionType.BUY) {
                 total += t.getQuantities();
            }else{
                total -= t.getQuantities(); 
            }

        }
        return total;
    }

    public double calculateTotalTransactionValue() {
        double totalCost = 0;
        int totalQuantities = 0;
        for (TransactionHistory t : transaction) {
           if (t.getType() == TransactionType.BUY) {
            totalCost += t.getTotalTransactionValue();
            totalQuantities += t.getQuantities();
           }else{
                if (totalQuantities == 0) continue;
                double avgCostSaleTime = totalCost / totalQuantities;
                totalCost -= (avgCostSaleTime * t.getQuantities());
                totalQuantities -= t.getQuantities();
           }
        }
        return totalCost;
    }

    public double getLastTransactionPrice() {
        if (transaction.isEmpty()) return 0;
        return transaction.get(transaction.size() - 1).getTransactionPrice();
    }

    public double averagePrice() {
        int totalQuantities = calculateTotalQuantities();
        if (totalQuantities == 0) return 0;
        double totalCost = calculateTotalTransactionValue();
        return totalCost / totalQuantities;
    }

    public double totalValueWithMarketPrice() {
        return calculateTotalQuantities() * this.currentMarketPrice;
    }

    public double calculateProfitLoss() {
        return totalValueWithMarketPrice() - calculateTotalTransactionValue();
    }

   
    public String getCompanyName() { return companyName; }
    public String getSymBol() { return symBol; }
    public double getCurrentMarketPrice() { return currentMarketPrice; }
    public void setCurrentMarketPrice(double price) { this.currentMarketPrice = price; }

    public abstract String getType();
}
