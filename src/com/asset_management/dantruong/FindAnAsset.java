package com.asset_management.dantruong;

import java.util.List;

import com.asset_management.dantruong.helper.helpMethod;
import com.asset_management.dantruong.portfolio.Portfolio;
import com.asset_management.dantruong.trasaction.Asset;

public class FindAnAsset  {
    private static final FindAnAsset instance = new  FindAnAsset();
    private static final helpMethod helper = helpMethod.getInstance();
    private FindAnAsset(){
       
    }
    public static FindAnAsset getInstancFindAnAsset(){
        return instance;
    }
    public synchronized void findingAsset(Portfolio portfolio, String currentUserName){
        System.out.println("\n----- Hello " + currentUserName + " What would you like to look for? -----" );
        String symbol = helper.readString("Can you please enter the stock/bond code you want to search for? ").toUpperCase();
        List<Asset> currenList = portfolio.getAssetsList();
        Asset assetLockFor = helper.isExist(symbol, currenList);
        if (assetLockFor == null) {
            System.out.println("We can't find the asset with code " + symbol + ", could you please check the asset code you just enter, to make sure it's correct?");
        }else{
            System.out.println("\nWe found it!");
            System.out.println(assetLockFor);
        }
    }
}
