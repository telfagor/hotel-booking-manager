package com.bolun.hotel.connection;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

@UtilityClass
public class ConnectionManager {

    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String POOL_SIZE = "db.pool.size";
    private static final int DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;

    private static List<Connection> sourceConnections;

    static {
        initConnectionPool();
    }

    private static void initConnectionPool() {
        String poolSize = PropertiesUtil.getValue(POOL_SIZE);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            Connection connection = open();
            Connection proxyConnection = (Connection)
                    Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close")
                            ? pool.add(connection)
                            : method.invoke(connection, args)
            );
            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    @SneakyThrows
    public static Connection getConnection() {
        return pool.take();
    }

    @SneakyThrows
    private static Connection open() {
        return DriverManager.getConnection(
                PropertiesUtil.getValue(DB_URL),
                PropertiesUtil.getValue(DB_USERNAME),
                PropertiesUtil.getValue(DB_PASSWORD)
        );
    }

    @SneakyThrows
    public static void closePool() {
        for (Connection sourceConnection : sourceConnections) {
            sourceConnection.close();
        }
    }
}
