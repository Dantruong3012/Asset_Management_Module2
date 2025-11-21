package com.asset_management.dantruong.edit_company_infor;

import java.util.Date; 
import java.util.List;

import com.asset_management.dantruong.helper.helpMethod;
import com.asset_management.dantruong.portfolio.Portfolio;
import com.asset_management.dantruong.portfolio.Stock;
import com.asset_management.dantruong.sort_asset.assetBST;
import com.asset_management.dantruong.portfolio.Bonds; 
import com.asset_management.dantruong.trasaction.Asset;

public class EditAssetInfor {

    private Portfolio portfolio;
    private assetBST tree;
    private helpMethod helper;

    
    public EditAssetInfor(helpMethod helper, Portfolio portfolio, assetBST tree, String userName) {
        this.helper = helper;
        this.portfolio = portfolio;
        this.tree = tree;
    }

   
    public synchronized void handleEdit() {
        List<Asset> currentList = portfolio.getAssetsList();
        for (Asset assets : currentList) {
            tree.insert(assets);
        }
        System.out.println("\n--- ASSET INFORMATION EDITOR ---");
        System.out.println("Total assets you hold: " + currentList.size());
        String editAssetSymbol = helper.readString("Please enter the asset code you want to search for: ").toUpperCase();
        Asset editAsset = tree.search(editAssetSymbol);
        if (editAsset == null) {
            System.out.println("We couldn't find " + editAssetSymbol + ". Please ensure your asset code is correct!");
            return; 
        }

        System.out.println("\nEditing: " + editAsset.getCompanyName() + " (" + editAsset.getType() + ")");

        boolean editing = true;
        while (editing) {
            System.out.println("\nSelect field to update for [" + editAsset.getSymBol() + "]:");
            System.out.println("1. Company/Organization Name");
            if (editAsset instanceof Stock) {
                System.out.println("2. Sector (Stock only)");
            } else if (editAsset instanceof Bonds) {
                System.out.println("2. Interest Rate (Bond only)");
                System.out.println("3. Maturity Date (Bond only)");
            }

            System.out.println("0. Save & Finish Editing");

            int maxChoice = (editAsset instanceof Bonds) ? 3 : 2;
            int choice = helper.readInt("Your choice: ", 0, maxChoice);

            switch (choice) {
                case 1:
                    String newName = helper.readString("Enter new Company/Organization Name: ");
                    editAsset.setCompanyName(newName); 
                    System.out.println(" Name updated.");
                    break;

                case 2:
                    if (editAsset instanceof Stock) {
                        String newSector = helper.readString("Enter new Sector: ");
                        ((Stock) editAsset).setMajor(newSector);
                        System.out.println("Sector updated.");
                    } else if (editAsset instanceof Bonds) {
                        double newRate = helper.readDouble("Enter new Interest Rate: ", 0);
                        ((Bonds) editAsset).setInterestRate(newRate); 
                        System.out.println("Interest Rate updated.");
                    } else {
                        System.out.println("Invalid selection for this asset type.");
                    }
                    break;

                case 3:
                    if (editAsset instanceof Bonds) {
                        Date newDate = helper.readDate("Enter new Maturity Date: ");
                        ((Bonds) editAsset).setMaturityDate(newDate); 
                        System.out.println("Maturity Date updated.");
                    } else {
                        System.out.println("Invalid selection."); 
                    }
                    break;

                case 0:
                    portfolio.saveAsset();
                    System.out.println("\n-------------------------------------------");
                    System.out.println("SUCCESSFULLY SAVED IN DATABASE AND FINISHED EDITING.");
                    System.out.println("-------------------------------------------");
                    editing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
