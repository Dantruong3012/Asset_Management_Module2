package com.asset_management.dantruong.user_operations;
import com.asset_management.dantruong.FindAnAsset;
import com.asset_management.dantruong.helper.helpMethod;
import com.asset_management.dantruong.market_simulator.MaketSimulator;
import com.asset_management.dantruong.portfolio.Portfolio;
import com.asset_management.dantruong.portfolio.sale.SellManager;
import com.asset_management.dantruong.update.updatePrices;
public class Dashboard {
    private String loggedInUsername;
    private helpMethod helper;
    private Portfolio myPortfolio;
    private SellManager salesManager;
    private updatePrices updatePrices;
    private Thread marketThread;
    private FindAnAsset findAnAsset;

    public Dashboard(String username, Portfolio myPortfolio) {
        this.loggedInUsername = username;
        this.helper = helpMethod.getInstance();
        this.myPortfolio = myPortfolio; 
        this.salesManager = new SellManager(myPortfolio, helper, username);
        this.updatePrices = new updatePrices(helper, myPortfolio, username);
        this.findAnAsset = new FindAnAsset();
    }

    public void showMenu() {
        MaketSimulator maketSimulator = new MaketSimulator(myPortfolio);
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
            System.out.println("9. View Portfolio Summary.");
            System.out.println("0. Logout");

            int choice = helper.readInt("\nYour choice: ", 0, 9);

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
                    findAnAsset.findingAsset(myPortfolio, this.loggedInUsername);
                    break;
                case 9:
                    myPortfolio.showPortfolioSummary();
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
