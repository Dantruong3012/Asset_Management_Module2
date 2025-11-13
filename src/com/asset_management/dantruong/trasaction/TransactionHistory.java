package com.asset_management.dantruong.trasaction;

import java.io.Serializable;
import java.util.Date;

public class TransactionHistory implements Serializable {
    private TransactionType type;
    private int quantities;
    private double transactionPrice;
    private Date transactionDate;

    public TransactionHistory(TransactionType type, int quantities, double transactionPrice, Date transactionDate) {
        this.type = type;
        this.quantities = quantities;
        this.transactionPrice = transactionPrice;
        this.transactionDate = transactionDate;
    }

    

    public int getQuantities() {
        return quantities;
    }

    public double getTransactionPrice() {
        return transactionPrice;
    }

    public double getTotalTransactionValue() {
        return this.quantities * this.transactionPrice;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }



    public TransactionType getType() {
        return type;
    }
}
