package com.bolun.hotel.connection;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import com.bolun.hotel.exception.ConnectionException;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

@UtilityClass
public class ConnectionPool {

    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String POOL_SIZE = "db.pool.size";
    private static final int DEFAULT_POOL_SIZE = 10;

    @Getter
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

    private static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.getValue(DB_URL),
                    PropertiesUtil.getValue(DB_USERNAME),
                    PropertiesUtil.getValue(DB_PASSWORD));
        } catch (SQLException ex) {
            throw new ConnectionException("The connection cannot be opened!", ex);
        }
    }


    public static void closePool() {
        try {
            for (Connection sourceConnection : sourceConnections) {
                sourceConnection.close();
            }
        } catch (SQLException ex) {
            throw new ConnectionException("The connection pool cannot be closed!", ex);
        }
    }
}
