package com.asset_management.dantruong.sort_asset;

import com.asset_management.dantruong.trasaction.Asset;

class AssetNode {
    Asset asset;
    assetNode left;
    assetNode right;

    public AssetNode(Asset asset) {
        this.asset = asset;
        this.left = null;
        this.right = null;
    }
}


public class AssetBST {
    assetNode root;

    public AssetBST(){
        this.root = null;
    }

    public void insert(Asset assets){
        root = insertRec(root, assets);
    }

    public assetNode insertRec(assetNode root, Asset asset){

        if (root == null) {
            return new assetNode(asset);
        }

       String newSymbol = asset.getSymBol();
       String currentSymbok = root.asset.getSymBol();

       if (newSymbol.compareToIgnoreCase(currentSymbok) < 0) {
        root.left = insertRec(root.left, asset);
       }else if (newSymbol.compareToIgnoreCase(currentSymbok) > 0) {
        root.right = insertRec(root.right, asset);
       }
       return root;
    }

    public void printAtoZ(){
        order(root);
    }

    public void order(assetNode root){
        if (root != null) {
            order(root.left);
            System.out.println(root.asset);
            order(root.right);
        }
    }


    public void printZtoA() {
        reverseOrder(root);
    }

    private void reverseOrder(assetNode root) {
        if (root != null) {
            reverseOrder(root.right);
            System.out.println(root.asset);
            reverseOrder(root.left);
        }
    }

    public Asset search(String symbolToFind){
        return searchRec(root, symbolToFind);
    }


    public Asset searchRec(assetNode root, String symbolToFind){
        if (root == null) {
            return null;
        }

        String currentSymbol =  root.asset.getSymBol();

        if (symbolToFind.equalsIgnoreCase(currentSymbol)) {
            return root.asset;
        }

        if (symbolToFind.compareToIgnoreCase(currentSymbol) < 0) {
            return searchRec(root.left, symbolToFind);
        }

        return searchRec(root.right, symbolToFind);
    }
}
