package com.asset_management.dantruong.portfolio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asset_management.dantruong.helper.helpMethod;
import com.asset_management.dantruong.trasaction.Asset;
import com.asset_management.dantruong.trasaction.TransactionType;
public class Portfolio implements Serializable {
    private static final String PORTFOLIO_DATA_FOLDER = "src/com/asset_management/dantruong/portfolio/data_buy";
    private List<Asset> assetsList;
    private transient helpMethod helper;
    private String currentLoginUser;

public Portfolio(){}

public Portfolio(helpMethod helper, String userName){
    this.currentLoginUser = userName;
    this.helper = helper;
    this.assetsList = new ArrayList<>();
}
 
public List<Asset> getAssetsList() {
    return this.assetsList;
}

public String getDynamicPath(){
    File folder = new File(PORTFOLIO_DATA_FOLDER);
    if (!folder.exists()) {
        folder.mkdirs();
    }
    return PORTFOLIO_DATA_FOLDER +"/"+ this.currentLoginUser.replaceAll("\\s+", "") + "_asset.dat";
}

public void saveAsset(){
    String userFile = getDynamicPath();
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile))) {
        oos.writeObject(assetsList);
        System.out.println("Saved To Database Successfully!");
    } catch (IOException e) {
        System.out.println("Error Saving To Database!" + e.getMessage());
    }
}



@SuppressWarnings("unchecked")
public void loadAsset(){
    String userFile = getDynamicPath();
    File myFile = new File(userFile);
    if (myFile.exists()) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(myFile))) {
            this.assetsList = (List<Asset>) ois.readObject();
            System.out.println("\nList for '" + this.currentLoginUser + "' loaded successfully. Found " + assetsList.size() + " assets.");
        } catch (IOException | ClassNotFoundException e) {
           System.out.println("\nLoad List Failed Please Check Your Database Again");
        }
    }else{
       System.out.println("\nDatabase not found for '" + this.currentLoginUser + "'. Starting new portfolio.");
    }
}



public void diplayPortfolio() {
    if (this.assetsList == null || this.assetsList.isEmpty()) {
        System.out.println("\nYour category is currently empty.");
        return;
    }

    Map<String, List<Asset>> assetMap = new LinkedHashMap<>();

    for (Asset Assets : assetsList) {
        assetMap.computeIfAbsent(Assets.getType(), AssetsList -> new ArrayList<>()).add(Assets);
    }

   for (Map.Entry<String, List<Asset>> entry : assetMap.entrySet()) {
        System.out.println("\n----------- " + entry.getKey() + " -----------");
        for (Asset a : entry.getValue()) {
            System.out.println(a);
        }
        System.out.println();
   }
   
}


public void addStockToAsset(){
    System.out.println("\n----------Please Enter Stock Information----------");
    String stockCode = helper.readString("\nPlease enter the company's stock code: ").toUpperCase();
    Asset existingAsset = helper.isExist(stockCode, assetsList);
    if (existingAsset != null && existingAsset instanceof Stock) {
        System.out.println("\nStocks found: " + existingAsset.getCompanyName());
        System.out.println("\nYou are purchasing in addition to this code...");
        int quantites = helper.readInt("\nPlease enter the quantites (số lượng mua thêm): ", 1, Integer.MAX_VALUE);
        double currentTransactionPrice = helper.readDouble("\nPlease enter the current transaction price: ", 0);
        Date transactionTime = helper.readDate("\nPlease enter the transaction time: "); 
        existingAsset.addToTransaction(TransactionType.BUY ,quantites, currentTransactionPrice, transactionTime);
        double newMarketPrice = helper.readDouble("\nPlease update the CURRENT market price of " + stockCode + ": ", 0);
        existingAsset.setCurrentMarketPrice(newMarketPrice);
        System.out.println("\nThe stock " + stockCode + " has been updated.");
    }else if (existingAsset != null && !(existingAsset instanceof Stock)) {
         System.out.println("\nError: Code " + stockCode + " existed but was not a Stock.");
         return; 
    }else{
    String sector = helper.readString("\nPlease enter your company's business sector: ");
    String companyName = helper.readString("\nPlease enter the company name: ");
    double currentPrice = helper.readDouble("\nPlease enter the stock price on the market: ", 0);
    Asset newStock = new Stock(sector, companyName, stockCode.toUpperCase(),  currentPrice);
    int quantites = helper.readInt("\nPlease enter the quantites (số lượng mua): ", 1, Integer.MAX_VALUE);
    double currentTransactionPrice = helper.readDouble("\nPlease enter the current transaction price (giá vốn): ", 0);
    Date transactionTime = helper.readDate("\nPlease enter the transaction time: ");
    newStock.addToTransaction(TransactionType.BUY, quantites, currentTransactionPrice, transactionTime);
    this.assetsList.add(newStock);
    System.out.println("\nThe stock " + stockCode.toUpperCase() + " has been added to the portfolio.");
    saveAsset();
    }
}


public void addBonToAsset(){
    System.out.println("\n----------Please Enter Bon Information----------");
    String  bondCode = helper.readString("\nPlease enter the bond code: ").toUpperCase();
    Asset existingBonds = helper.isExist(bondCode, assetsList);
    if (existingBonds != null && existingBonds instanceof Bonds) {
        System.out.println("\nBonds found: " + existingBonds.getCompanyName());
        System.out.println("\nYou are purchasing in addition to this bond...");
        int quantites = helper.readInt("\nPlease enter the number of bonds held: ", 1, Integer.MAX_VALUE);
        double currentTransactionPrice = helper.readDouble("\nPlease enter the current transaction price: ", 1);
        Date transactionTime = helper.readDate("\nPlease enter the transaction time: ");
        existingBonds.addToTransaction(TransactionType.BUY ,quantites, currentTransactionPrice, transactionTime);
        double newMarketPrice = helper.readDouble("\nPlease update the CURRENT market price of " + bondCode + ": ", 0);
        existingBonds.setCurrentMarketPrice(newMarketPrice);
        System.out.println("\nThe bond " + bondCode + " has been updated.");
    }else if (existingBonds != null && !(existingBonds instanceof Bonds)) {
        System.out.println("\nError: Bond " + bondCode + " existed but was not a bond.");
         return; 
    }else{
          String organization = helper.readString("\nPlease enter the name of the bond issuer: ");
          double interestRate = helper.readDouble("\nPlease enter the bond interest rate: ", 0);
          Date maturityDate = helper.readDate("\nPlease enter the bond maturity date: ");
          double currentPrice = helper.readDouble("\nPlease enter the market price of the bond: ", 1);
          Asset newBond = new Bonds(interestRate, maturityDate, bondCode, organization, currentPrice);
          int quantites = helper.readInt("\nPlease enter the number of bonds held: ", 1, Integer.MAX_VALUE);
          double currentTransactionPrice = helper.readDouble("\nPlease enter the current transaction price: ", 1);
          Date transactionTime = helper.readDate("\nPlease enter the transaction time: ");
          newBond.addToTransaction(TransactionType.BUY ,quantites, currentTransactionPrice, transactionTime);
          this.assetsList.add(newBond);
          System.out.println("\nThe Bond " + bondCode + " has been added to the portfolio.");
          saveAsset();
    }
}


}
