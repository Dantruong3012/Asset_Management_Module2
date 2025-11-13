package com.asset_management.dantruong.update;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;



import com.asset_management.dantruong.helper.helpMethod;
import com.asset_management.dantruong.portfolio.Portfolio;
import com.asset_management.dantruong.trasaction.Asset;

public class updatePrices {
    private helpMethod helper;
    private Portfolio portfolio;
    private String userName;
    private static final String UPDATE_HISTORY_PATH = "src/com/asset_management/dantruong/update/asset_updated_history/";


public updatePrices(helpMethod helper, Portfolio portfolio, String userName){
    this.helper = helper;
    this.portfolio = portfolio;
    this.userName = userName;
}

public String getUpdatePath(){
    File folder = new File(UPDATE_HISTORY_PATH);
    if (!folder.exists()) {
       System.out.println("Database does not exist yet, creating database!");
       folder.mkdirs(); 
    }
    return UPDATE_HISTORY_PATH + this.userName + "_Updatedlog.txt";
}


public void savePriceUpdated(String updated){
    try (BufferedWriter writer =  new BufferedWriter(new FileWriter(getUpdatePath(), true))){
        writer.write(updated);
        writer.newLine();
    } catch (IOException e) {
        System.out.println("Database error unable to save updated!");
    }
}


public void updateMarketPrice(){
    String symbol = helper.readString("Please enter the code of the stock or bond you want to update the market price: ");
    List<Asset> currAssetsList = portfolio.getAssetsList();
    Asset assetToUpdate = helper.isExist(symbol, currAssetsList);
    if (assetToUpdate == null) {
        System.out.println("Cant find the asset, you look for " + symbol);
        return; 
    }
    double currentPrice = assetToUpdate.getCurrentMarketPrice();
    double newMarketPrice = helper.readDouble("Please enter new market price: ", 0);
    assetToUpdate.setCurrentMarketPrice(newMarketPrice);
    this.portfolio.saveAsset();
    double priceFluctuations = newMarketPrice - currentPrice;
    LocalDateTime upDatedTime = LocalDateTime.now();
    String updatedDetail =  String.format("Update Time [%s] | Last Market Price: %f | New Market Price %f | Price Fluctuations: %.2f ", upDatedTime.toString() ,currentPrice, newMarketPrice, priceFluctuations);
    savePriceUpdated(updatedDetail);
}


public void displayUpdatedHistory(){

}

}
