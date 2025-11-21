package com.asset_management.dantruong.portfolio.sell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.asset_management.dantruong.helper.helpMethod;
import com.asset_management.dantruong.portfolio.Portfolio;
import com.asset_management.dantruong.trasaction.Asset;
import com.asset_management.dantruong.trasaction.TransactionType;

public class SellManager {
    private static String SALE_DATE = "src/com/asset_management/dantruong/portfolio/data_sale/";
    private Portfolio portfolio;
    private helpMethod helper;
    private String currentLoginName;


public SellManager(Portfolio portfolio, helpMethod helper, String currentLoginName){
    this.portfolio = portfolio;
    this.helper = helper;
    this.currentLoginName = currentLoginName;
}

public synchronized String getSaleLogPath(){
   File folder = new File(SALE_DATE);
   if (!folder.exists()) {
    folder.mkdirs();
   }
   return SALE_DATE + this.currentLoginName.replaceAll("\\s+", "").trim() + "_saleslog.txt";
}

public synchronized void saveSaleFile(String saleDetail){
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(getSaleLogPath(), true))) {
        writer.write(saleDetail);
        writer.newLine();
        System.out.println("Your transaction has been saved to file.");
    } catch (IOException e) {
       System.out.println("Database error unable to save transaction! ");
    }
}


public synchronized void handleSale(){
    System.out.println("Hello " + this.currentLoginName +  ", what do you want to sell today!");
    String symbol = helper.readString("please enter the stock/bond code here: ").toUpperCase();
    List<Asset> currnAssetsList = portfolio.getAssetsList();
    Asset assetToSell = helper.isExist(symbol, currnAssetsList);
    if (assetToSell == null) {
        System.out.println("The asset you want to sell with this code " + symbol + " was not found.");
        return;
    }

    int currentQty = assetToSell.calculateTotalQuantities();
    
    System.out.println(this.currentLoginName + ", you have " + currentQty + " of " + symbol);

    int qtyToSell = helper.readInt("Enter the number of shares/bonds you want to sell: ", 1, currentQty);
    double sellPrice = helper.readDouble("how much do you want to sell for: ", 0);
    Date saleTime = new Date();

    double averageCost = assetToSell.averagePrice();
    double profitLoss = (sellPrice - averageCost) * qtyToSell;
    assetToSell.addToTransaction(TransactionType.SELL, qtyToSell, sellPrice, saleTime);
    this.portfolio.saveAsset();

    String sellDetail = String.format("Sell Date: [%s] | Sell: %s | Quantities: %d | Price: %.2f | Profit: %.2f ", saleTime.toString(), symbol, qtyToSell, sellPrice, profitLoss);
    saveSaleFile(sellDetail);

    System.out.println("\n---------------------------------");
        System.out.println("SUCCESSFUL SALE TRANSACTION");
        System.out.printf("Closing profit: %.2f\n", profitLoss);
        System.out.println("Remaining quantity: " + assetToSell.calculateTotalQuantities());
        System.out.println("---------------------------------");

        if (assetToSell.calculateTotalQuantities() == 0) {
            currnAssetsList.remove(assetToSell);
            System.out.println(symbol + " has been removed from your portfolio.");
        }else{
            assetToSell.setCurrentMarketPrice(sellPrice);
        }
        this.portfolio.saveAsset();
    }

public synchronized void viewSaleHistory(){
    System.out.println("\n Hello " + this.currentLoginName + ", let's review your achievements! ");
    System.out.println("-------------------------------------------");
    String saleFile =  getSaleLogPath();
    File saleHistory = new File(saleFile);
    if (!saleHistory.exists()) {
        System.out.println(this.currentLoginName + "has no sales history!");
        return;
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(saleHistory))) {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    } catch (IOException e) {
       System.out.println("Database error cannot open sale transaction history now. Please try again later!");
    }
}
}
