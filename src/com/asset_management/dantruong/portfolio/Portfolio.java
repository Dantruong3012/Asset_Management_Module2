package com.asset_management.dantruong.portfolio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.asset_management.dantruong.helper.HelpMethod;
import com.asset_management.dantruong.sort_asset.AssetBST;
import com.asset_management.dantruong.trasaction.Asset;
import com.asset_management.dantruong.trasaction.TransactionType;

public class Portfolio implements Serializable {
    private static final String PORTFOLIO_DATA_FOLDER = "src/com/asset_management/dantruong/portfolio/data_buy";
    private List<Asset> assetsList;
    private transient HelpMethod helper;
    private String currentLoginUser;
    private Random random = new Random();
    public Portfolio() {
    }

    public Portfolio(HelpMethod helper, String userName) {
        this.currentLoginUser = userName;
        this.helper = helper;
        this.assetsList = initializeAssets();
    }

    public synchronized List<Asset> getAssetsList() {
        // Các lớp khác chỉ được ĐỌC, muốn sửa phải gọi hàm của Portfolio
        return Collections.unmodifiableList(this.assetsList);
    }

    public String getDynamicPath() {
        File folder = new File(PORTFOLIO_DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return PORTFOLIO_DATA_FOLDER + "/" + this.currentLoginUser.replaceAll("\\s+", "") + "_asset.dat";
    }

    public synchronized void saveAsset() {
        String userFile = getDynamicPath();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile))) {
            oos.writeObject(assetsList);
            System.out.println("Saved To Database Successfully!");
        } catch (IOException e) {
            System.out.println("Error Saving To Database!" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public synchronized List<Asset> initializeAssets() {
        String userFile = getDynamicPath();
        File myFile = new File(userFile);
        if (myFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(myFile))) {
                List<Asset> loadedList = (List<Asset>) ois.readObject();
                System.out.println("\n>> Welcome back! " + this.currentLoginUser + ", Loaded " +  loadedList.size() + " assets from database.");
                return loadedList;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("\nLoad List Failed Please Check Your Database Again");
            }
        } else {
            System.out.println("\nDatabase not found for '" + this.currentLoginUser + "'. Starting new portfolio.");
        }
        return new ArrayList<>();
    }

    public synchronized void diplayPortfolio(boolean isAtoZ) {
        if (this.assetsList == null || this.assetsList.isEmpty()) {
            System.out.println("\nYour category is currently empty.");
            return;
        }
        
        AssetBST stockTree = new AssetBST();
        AssetBST bondTree = new AssetBST();

        for (Asset myAsset : assetsList) {
            if (myAsset instanceof Stock) {
                stockTree.insert(myAsset);
            }else{
                bondTree.insert(myAsset);
            }
        }

        System.out.println("\n=========== STOCKS ===========");
        if (isAtoZ) {
            stockTree.printAtoZ();
        }else{
            stockTree.printZtoA();
        }

        System.out.println("\n=========== BONDS ============");
        if (isAtoZ) {
            bondTree.printAtoZ();
        }else{
            bondTree.printZtoA();
        }

       

    }

    public synchronized void addStockToAsset() {
        System.out.println("\n----------Please Enter Stock Information----------");
        String stockCode = helper.readString("\nPlease enter the company's stock code: ").toUpperCase();
        Asset existingAsset = helper.isExist(stockCode, assetsList);
        if (existingAsset != null && existingAsset instanceof Stock) {
            System.out.println("\nStocks found: " + existingAsset.getCompanyName());
            System.out.println("\nYou are purchasing in addition to this code...");
            int quantites = helper.readInt("\nPlease enter the quantites (số lượng mua thêm): ", 1, Integer.MAX_VALUE);
            double currentTransactionPrice = helper.readDouble("\nPlease enter the current transaction price: ", 0);
            Date transactionTime = helper.readDate("\nPlease enter the transaction time: ");
            existingAsset.addToTransaction(TransactionType.BUY, quantites, currentTransactionPrice, transactionTime);
            double newMarketPrice = helper.readDouble("\nPlease update the CURRENT market price of " + stockCode + ": ",
                    0);
            existingAsset.setCurrentMarketPrice(newMarketPrice);
            System.out.println("\nThe stock " + stockCode + " has been updated.");
            saveAsset();
        } else if (existingAsset != null && !(existingAsset instanceof Stock)) {
            System.out.println("\nError: Code " + stockCode + " existed but was not a Stock.");
            return;
        } else {
            String sector = helper.readString("\nPlease enter your company's business sector: ");
            String companyName = helper.readString("\nPlease enter the company name: ");
            double currentPrice = helper.readDouble("\nPlease enter the stock price on the market: ", 0);
            Asset newStock = new Stock(sector, companyName, stockCode.toUpperCase(), currentPrice);
            int quantites = helper.readInt("\nPlease enter the quantites (số lượng mua): ", 1, Integer.MAX_VALUE);
            double currentTransactionPrice = helper
                    .readDouble("\nPlease enter the current transaction price (giá vốn): ", 0);
            Date transactionTime = helper.readDate("\nPlease enter the transaction time: ");
            newStock.addToTransaction(TransactionType.BUY, quantites, currentTransactionPrice, transactionTime);
            this.assetsList.add(newStock);
            System.out.println("\nThe stock " + stockCode.toUpperCase() + " has been added to the portfolio.");
            saveAsset();
        }
    }

    public synchronized void addBonToAsset() {
        System.out.println("\n----------Please Enter Bon Information----------");
        String bondCode = helper.readString("\nPlease enter the bond code: ").toUpperCase();
        Asset existingBonds = helper.isExist(bondCode, assetsList);
        if (existingBonds != null && existingBonds instanceof Bonds) {
            System.out.println("\nBonds found: " + existingBonds.getCompanyName());
            System.out.println("\nYou are purchasing in addition to this bond...");
            int quantites = helper.readInt("\nPlease enter the number of bonds held: ", 1, Integer.MAX_VALUE);
            double currentTransactionPrice = helper.readDouble("\nPlease enter the current transaction price: ", 1);
            Date transactionTime = helper.readDate("\nPlease enter the transaction time: ");
            existingBonds.addToTransaction(TransactionType.BUY, quantites, currentTransactionPrice, transactionTime);
            double newMarketPrice = helper.readDouble("\nPlease update the CURRENT market price of " + bondCode + ": ",
                    0);
            existingBonds.setCurrentMarketPrice(newMarketPrice);
            System.out.println("\nThe bond " + bondCode + " has been updated.");
        } else if (existingBonds != null && !(existingBonds instanceof Bonds)) {
            System.out.println("\nError: Bond " + bondCode + " existed but was not a bond.");
            return;
        } else {
            String organization = helper.readString("\nPlease enter the name of the bond issuer: ");
            double interestRate = helper.readDouble("\nPlease enter the bond interest rate: ", 0);
            Date maturityDate = helper.readDate("\nPlease enter the bond maturity date: ");
            double currentPrice = helper.readDouble("\nPlease enter the market price of the bond: ", 1);
            Asset newBond = new Bonds(interestRate, maturityDate, bondCode, organization, currentPrice);
            int quantites = helper.readInt("\nPlease enter the number of bonds held: ", 1, Integer.MAX_VALUE);
            double currentTransactionPrice = helper.readDouble("\nPlease enter the current transaction price: ", 1);
            Date transactionTime = helper.readDate("\nPlease enter the transaction time: ");
            newBond.addToTransaction(TransactionType.BUY, quantites, currentTransactionPrice, transactionTime);
            this.assetsList.add(newBond);
            System.out.println("\nThe Bond " + bondCode + " has been added to the portfolio.");
            saveAsset();
        }
    }

    public synchronized void priceFluctuations() {
        for (Asset myAssets : assetsList) {
            if (myAssets instanceof Stock) {
                double oldPrice = myAssets.getCurrentMarketPrice();
                double changePercent = (random.nextDouble() - 0.5) * 0.13;
                double newPrice = oldPrice * (1 + changePercent);
                if (newPrice < 0) {
                    newPrice = 0;
                }
                newPrice = Math.round(newPrice * 100.0) / 100.0;
                myAssets.setCurrentMarketPrice(newPrice);
            }
        }
    }

    public void handleLiveView() {
        if (this.helper == null) {
            System.out.println("Error cannot view live market");
            return;
        }

        if (this.assetsList == null || this.assetsList.isEmpty()) {
            System.out.println("\nSorry " + this.currentLoginUser + ", you currently dont have any asssets!");
            System.out.println("\nLet's start adding assets to your portfolio.");
            return; 
        }

        System.out.println("\n--- LIVE PORTFOLIO VIEW SETTINGS ---");
        System.out.println("Choose sorting order:");
        System.out.println("1. Sort A-Z (Ascending)");
        System.out.println("2. Sort Z-A (Descending)");
        int sortChoice = helper.readInt("Select (1 or 2): ", 1, 2);

        boolean isAtoZ = (sortChoice == 1);

        helper.clean();

        System.out.println("\n--- LIVE PORTFOLIO VIEW ---");
        Thread inputListener = new Thread(() -> {
            try {
                System.in.read();
            } catch (IOException e) {
                System.out.println("Errors!");
            }
        });
        inputListener.start();
        while (inputListener.isAlive()) {
            try {
                helper.clean();
                String sortMode = isAtoZ ? "A-Z" : "Z-A";
                System.out.println("--- LIVE PORTFOLIO VIEW " + sortMode + " (Press (ENTER) to back to the dash bord!) ---");
                diplayPortfolio(isAtoZ);
                System.out.println("Prices are being updated according to market transactions......");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }

        if (inputListener.isAlive()) {
            inputListener.interrupt();
        }

        System.out.println("Saving updated market prices to database...");
        saveAsset();

        helper.clean();
        ;
        System.out.println("--- End of live viewing. Return to main menu. ---");
    }

    public synchronized void showPortfolioSummary(){
        double totalMarketValue = 0;
        double totalInvestmentCapital = 0;
        if (this.assetsList == null || this.assetsList.isEmpty()) {
            System.out.println("\nHello " + this.currentLoginUser + ", Your portfolio is empty.");
        }
        for (Asset mAsset : assetsList) {
            totalInvestmentCapital += mAsset.calculateTotalTransactionValue();
            totalMarketValue += mAsset.totalValueWithMarketPrice();
        }
        double netProfit = totalMarketValue - totalInvestmentCapital;
        System.out.println("\n--- PORTFOLIO SUMMARY ---");
        System.out.println("Hello " + this.currentLoginUser + "!");
        System.out.printf("   Total Investment Capital: %.2f\n", totalInvestmentCapital);
        System.out.printf("   Total Current Market Value: %.2f\n", totalMarketValue);
        System.out.printf("   Total Profit/Loss: %.2f\n", netProfit);
        System.out.println("-----------------------------");
    }


    public synchronized void removeAsset(Asset removeAsset){
        assetsList.remove(removeAsset);
    }

}
