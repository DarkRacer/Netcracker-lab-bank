package com.nc.lab.bank.model;

import com.nc.lab.bank.model.enums.TypeOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class describes the client
 * @author Maksim Shcherbakov
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    /**
     * Type of client operation
     */
    private TypeOperation typeOperation;

    /**
     * Amount of the client's operation
     */
    private double amount;

    /**
     * Client's service time
     */
    private long time;
}
