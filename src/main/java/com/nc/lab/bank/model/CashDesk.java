package com.nc.lab.bank.model;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class describes the cash desk
 * @author Maksim Shcherbakov
 * @version 1.0
 */
@Slf4j
@NoArgsConstructor
public class CashDesk {
    /**
     * Cash in the cash desk
     */
    private volatile double cash = 0;

    /**
     * Method for depositing cash in the cash desk
     * @param amount - amount depositing
     */
    public synchronized void deposit(double amount) {
        log.info("Money in the cash desk before making a deposit: " + this.cash);
        this.cash += amount;
        log.info("Money in the cash desk after making a deposit: " + this.cash);
    }

    /**
     * Method for withdrawing cash in the cash desk
     * @param amount - amount withdrawing
     * @return true - if there is a sufficient amount in the bank's cash desk
     */
    public synchronized boolean withdraw(double amount){
        log.info("Money in the cash desk before making a withdraw: " + this.cash);
        if (this.cash-amount >= 0) {
            this.cash -= amount;
            log.info("Money in the cash desk after making a withdraw: " + this.cash);
            return true;
        }
        return false;
    }
}
