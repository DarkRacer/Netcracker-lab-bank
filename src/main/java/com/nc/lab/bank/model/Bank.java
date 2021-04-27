package com.nc.lab.bank.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Class describes the bank
 * @author Maksim Shcherbakov
 * @version 1.0
 */
@Data
public class Bank {
    /**
     * List of operators
     */
    private List<Operator> operators = new ArrayList<>();

    /**
     * Count of operators
     */
    private int countOperators;

    /**
     * Cash desk of bank
     */
    private static CashDesk cashDesk;

    /**
     * Constructor of bank
     * @param countOperator - count of operators
     * @param cash - initial amount of money in the cash desk
     */
    public Bank(int countOperator, double cash) {
        this.countOperators = countOperator;
        cashDesk = new CashDesk();
        cashDesk.deposit(cash);
    }

    /**
     * Method starting flow of generating clients and operator flows
     */
    public void start(){
        for (int i = 1; i <= this.countOperators; i++) {
            Operator operator = new Operator(i);
            operators.add(operator);
            new Thread(operator).start();
        }
        Thread generator = new GeneratorClient(this);
        generator.start();
    }

    /**
     * Method for finding a free operator and adding a client to it
     *
     * @param client - client
     */
    public void findFreeOperator(Client client){
        Operator operator = operators.get(0);
        int minCountClient = operator.size();

        for (Operator op : operators) {
            if(op.size() < minCountClient){
                minCountClient = op.size();
                operator = op;
            }
        }
        operator.addClient(client);
    }

    /**
     * Singleton thread-safe method for getting bank cash desk
     * @return bank cash desk
     */
    public static synchronized CashDesk getCashDesk() {
        return cashDesk;
    }
}
