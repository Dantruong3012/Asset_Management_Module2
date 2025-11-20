package com.asset_management.dantruong;

import java.util.List;

import com.asset_management.dantruong.helper.helpMethod;
import com.asset_management.dantruong.portfolio.Portfolio;
import com.asset_management.dantruong.sort_asset.assetBST;
import com.asset_management.dantruong.trasaction.Asset;

public class FindAnAsset  {
    private static final helpMethod helper = helpMethod.getInstance();

    public FindAnAsset(){}

    public synchronized void findingAsset(Portfolio portfolio, String currentUserName){
        System.out.println("\n----- Hello " + currentUserName + " What would you like to look for? -----" );
        List<Asset> currenList = portfolio.getAssetsList();
        assetBST assetTree = new assetBST();
        for (Asset mAsset : currenList) {
            assetTree.insert(mAsset);
        }
        String symbol = helper.readString("\nCan you please enter the stock/bond code you want to search for? ")
                .toUpperCase();

       Asset foundAsset = assetTree.search(symbol);

       if (foundAsset != null) {
        System.out.println(currentUserName + " We've just found your asset!");
        System.out.println(foundAsset.toString());
       }else{
        System.out.println("\n So sorry " + currentUserName + " we can't find your asset with the code " + symbol);
       }

    }
}
