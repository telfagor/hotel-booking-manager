package com.bolun.hotel.connection;

import lombok.experimental.UtilityClass;
import com.bolun.hotel.exception.ConnectionException;

import java.sql.Connection;

@UtilityClass
public class ConnectionManager {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new ConnectionException("The PostgresSQL driver was not been loaded!", ex);
        }
    }

    public static Connection getConnection() {
        try {
            return ConnectionPool.getPool().take();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new ConnectionException("The connection was not obtained!", ex);
        }
    }
}
