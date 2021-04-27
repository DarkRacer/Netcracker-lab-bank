package com.nc.lab.bank.model;

import com.nc.lab.bank.model.enums.TypeOperation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class describes the operator
 * @author Maksim Shcherbakov
 * @version 1.0
 */
@Data
@Slf4j
@NoArgsConstructor
public class Operator implements Runnable {
    /**
     * Operator id
     */
    private int id;

    /**
     * Client queue
     */
    private final BlockingQueue<Client> clients = new LinkedBlockingQueue<>();

    /**
     * Constructor for operator
     * @param id - operator id
     */
    public Operator(int id) {
        this.id = id;
    }

    /**
     * Method for depositing money in the cash desk
     *
     * @param amount - the amount of cash for depositing
     * @param client - the client who wants to perform the operation
     */
    private void depositMoneyInCashDesk(double amount, Client client){
        Bank.getCashDesk().deposit(amount);
        log.info("The operator deposit it in the cash desk: " + amount + " client's " + client);
    }

    /**
     * Method for withdrawing money from the cash desk
     *
     * @param amount - the amount of cash for withdrawing
     * @param client - the client who wants to perform the operation
     */
    private void withdrawMoneyInCashBox(double amount, Client client){
        if (Bank.getCashDesk().withdraw(amount)){
            log.info("The operator withdraw from the cash desk: " + amount + " for the client " + client);
        }
        else {
            log.warn("The operator failed to withdraw from the cash desk: " + amount + " for the client " + client +
                    " because there are not enough funds in the cash register");
        }
    }

    /**
     * Method for adding client to the queue
     *
     * @param newClient - client to add to the queue
     */
    public void addClient(Client newClient) {
        synchronized (clients) {
            clients.add(newClient);
            log.info("Client " + newClient + " added to the queue for the operator " + this.id);
            clients.notify();
        }
    }

    /**
     * Method for getting information about the queue size
     * @return queue size
     */
    public int size(){
        return clients.size();
    }

    /**
     * Method for working with a queue of clients
     */
    @Override
    public void run() {
        Client client;
        while (true){
            try {
                synchronized (clients) {
                    if (clients.isEmpty()) {
                        clients.wait();
                    }
                }

                log.info("Clients in the queue: " + clients.size());
                client = clients.poll();
                Thread.sleep(client.getTime());

                if (client.getTypeOperation() == TypeOperation.DEPOSIT) {
                    this.depositMoneyInCashDesk(client.getAmount(), client);
                } else {
                    this.withdrawMoneyInCashBox(client.getAmount(), client);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
