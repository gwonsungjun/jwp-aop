package core.util;

import core.jdbc.ConnectionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class DataSourceUtils {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceUtils.class);
    private static final ConnectionHolder connectionHolder = new ConnectionHolder();

    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        if (!connectionHolder.hasConnection()) {
            connectionHolder.setConnection(fetchConnection(dataSource));
        }

        return connectionHolder.getConnection();
    }

    private static Connection fetchConnection(DataSource dataSource) {
        if (Objects.isNull(dataSource)) {
            return null;
        }

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("", e);
        }

        return null;
    }

    public static void removeConnection() {
        connectionHolder.removeConnection();
    }
}
