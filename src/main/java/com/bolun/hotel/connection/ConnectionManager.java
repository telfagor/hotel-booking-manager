package com.bolun.hotel.connection;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Connection;

@UtilityClass
public class ConnectionManager {

    @SneakyThrows
    public static Connection getConnection() {
        return ConnectionPool.getPool().take();
    }
}
