package com.asset_management.dantruong.user_operations;
import com.asset_management.dantruong.edit_company_infor.AssetEditor;
import com.asset_management.dantruong.helper.HelpMethod;
import com.asset_management.dantruong.market_simulator.MarketSimulator;
import com.asset_management.dantruong.portfolio.FindAnAsset;
import com.asset_management.dantruong.portfolio.Portfolio;
import com.asset_management.dantruong.portfolio.sell.SellManager;
import com.asset_management.dantruong.sort_asset.AssetBST;
import com.asset_management.dantruong.update.PriceUpdater;
public class Dashboard {
    private String loggedInUsername;
    private HelpMethod helper;
    private Portfolio myPortfolio;
    private SellManager salesManager;
    private PriceUpdater updatePrices;
    private Thread marketThread;
    private FindAnAsset findAnAsset;
    private AssetEditor editer;
    private AssetBST treeAssetBST;

    public Dashboard(){}

    public Dashboard(String username, Portfolio myPortfolio) {
        this.loggedInUsername = username;
        this.helper = HelpMethod.getInstance();
        this.myPortfolio = myPortfolio; 
        this.salesManager = new SellManager(myPortfolio, helper, username);
        this.updatePrices = new PriceUpdater(helper, myPortfolio, username);
        this.findAnAsset = new FindAnAsset(myPortfolio);
        this.treeAssetBST = new AssetBST();
        this.editer = new AssetEditor(helper, myPortfolio, this.treeAssetBST, username);
    }

    public void showMenu() {
        MarketSimulator maketSimulator = new MarketSimulator(myPortfolio);
        marketThread = new Thread(maketSimulator);
        marketThread.setDaemon(true);
        marketThread.start();
        System.out.println("\nWhat would you like to do to day?");
        boolean running = true;
        while(running) {
            System.out.println("\n--- PORTFOLIO DASHBOARD ---");
            System.out.println("1. View my portfolio.");
            System.out.println("2. Add a new Stock.");
            System.out.println("3. Add a new Bond.");
            System.out.println("4. Selling an asset.");
            System.out.println("5. View sell transaction history.");
            System.out.println("6. Update market price for assets.");
            System.out.println("7. View the asset's price update history.");
            System.out.println("8. Find an asset.");
            System.out.println("9. View portfolio summary.");
            System.out.println("10. Edit asset information.");
            System.out.println("0. Logout");

            int choice = helper.readInt("\nYour choice: ", 0, 10);

            switch(choice) {
                case 1:
                    myPortfolio.handleLiveView();
                    break;
                case 2:
                    myPortfolio.addStockToAsset();
                    break;
                case 3:
                    myPortfolio.addBonToAsset();
                    break;

                 case 4:
                    salesManager.handleSale();
                    break;
                case 5:
                    salesManager.viewSaleHistory();
                    break;
                case 6:
                    updatePrices.updateMarketPrice();
                    break;

                case 7:
                    updatePrices.viewUpdatedHistory();
                    break;

                case 8:
                    findAnAsset.findingAsset(this.loggedInUsername);
                    break;
                case 9:
                    myPortfolio.showPortfolioSummary();
                    break;
                case 10:
                    editer.handleEdit();
                    break;
                case 0:
                    maketSimulator.stopSimulation();
                    System.out.println("Logging out... Goodbye, " + this.loggedInUsername);
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}
