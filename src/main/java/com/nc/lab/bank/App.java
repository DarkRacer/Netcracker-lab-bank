package com.nc.lab.bank;

import com.nc.lab.bank.model.Bank;

/**
 * Main class of the program
 * @author Maksim Shcherbakov
 * @version 1.0
 */
public class App {
    /**
     * Starting method of the program
     * @param args command line argument
     */
    public static void main(String[] args) {
        Bank bank = new Bank(4, 30000);
        bank.start();
    }
}
