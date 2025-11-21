package com.asset_management.dantruong.portfolio;

import java.util.List;

import com.asset_management.dantruong.helper.HelpMethod;
import com.asset_management.dantruong.sort_asset.AssetBST;
import com.asset_management.dantruong.trasaction.Asset;

public class FindAnAsset  {
    private static final HelpMethod helper = HelpMethod.getInstance();
    private Portfolio portfolio;
    public FindAnAsset(Portfolio portfolio){
        this.portfolio = portfolio;
    }

    public synchronized Asset findingAsset(String currentUserName){
        System.out.println("\n----- Hello " + currentUserName + " What would you like to look for? -----" );
        List<Asset> currenList = portfolio.getAssetsList();
        AssetBST assetTree = new AssetBST();
        for (Asset mAsset : currenList) {
            assetTree.insert(mAsset);
        }
        String symbol = helper.readString("\nCan you please enter the stock/bond code you want to search for? ")
                .toUpperCase();

       Asset foundAsset = assetTree.search(symbol);

       if (foundAsset != null) {
        System.out.println(currentUserName + " We've just found your asset!");
        System.out.println(foundAsset.toString());
        return foundAsset;
       }else{
        System.out.println("\n So sorry " + currentUserName + " we can't find your asset with the code " + symbol);
       }
       return null;

    }
}
