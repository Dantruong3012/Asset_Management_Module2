package com.asset_management.dantruong.market_simulator;

import com.asset_management.dantruong.portfolio.Portfolio;

public class MaketSimulator implements Runnable {
    private Portfolio portfolio;
    private volatile boolean running = true;

public MaketSimulator(Portfolio portfolio){
    this.portfolio = portfolio;
}

public void stopSimulation() {
        this.running = false;
}

@Override 

public void run(){
    while (running) {
        try {
            Thread.sleep(10000);
            if (running) {
                portfolio.priceFluctuations();
            }
        } catch (InterruptedException e) {
           System.out.println("Market simulation interrupted.");
           running = false; 
        }catch (Exception e){
            System.out.println("Unknown error" + e.getMessage());
        }
    }
}
}
