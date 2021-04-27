package com.nc.lab.bank.model;

import com.nc.lab.bank.model.enums.TypeOperation;

import java.util.Random;

/**
 * Class describes the generator client
 * @author Maksim Shcherbakov
 * @version 1.0
 */
public class GeneratorClient extends Thread {
    /**
     * Average service time
     */
    private static final long SERVICE_TIME = 5000;

    /**
     * Average amount of cash a client has
     */
    private static final double CASH_AMOUNT = 15000;

    /**
     * Average number of clients per minute
     */
    private static final int CLIENTS_PER_MINUTE = 60;

    /**
     * The time interval between the creation of clients.
     */
    private final int generationDelay;

    /**
     * The bank that clients come to
     */
    private final Bank bank;

    /**
     * An object of the Random class for generating deviation from the mean
     */
    private final Random random;

    /**
     * Constructor with parameters for class Generator client
     *
     * @param bank the bank that clients come to
     */
    public GeneratorClient(Bank bank) {
        this.generationDelay = 60 / CLIENTS_PER_MINUTE;
        this.random = new Random();
        this.bank = bank;
    }

    /**
     * The method creates a new client at a specified time interval and redirects him to the queue to the bank operator
     */
    @Override
    public void run() {
        double rand;
        while (true) {
            try {
                Thread.sleep(Math.abs((long) ((random.nextGaussian() * (generationDelay / 2) + generationDelay) * 1000)));
                long clientServiceTime = Math.abs(Math.round(random.nextGaussian() * (SERVICE_TIME / 2) + SERVICE_TIME));
                double clientCash = Math.abs(Math.round(random.nextGaussian() * (CASH_AMOUNT / 2) + CASH_AMOUNT));
                rand = Math.random();
                Client client;

                if (rand > 0.5){
                    client = new Client(TypeOperation.DEPOSIT, clientCash, clientServiceTime);
                }
                else
                    client = new Client(TypeOperation.WITHDRAW, clientCash, clientServiceTime);

                bank.findFreeOperator(client);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
