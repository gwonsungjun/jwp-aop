package core.aop.transactional;

import core.aop.Advice;
import core.aop.MethodInvocation;
import core.jdbc.DataAccessException;
import core.util.DataSourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;

public class TransactionalAdvice implements Advice {
    private static final Logger logger = LoggerFactory.getLogger(TransactionalAdvice.class);

    private final DataSource dataSource;

    public TransactionalAdvice(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Connection connection = DataSourceUtils.getConnection(dataSource);

        try {
            logger.debug("transaction start");
            connection.setAutoCommit(false);
            Object proceed = invocation.proceed();
            connection.commit();
            logger.debug("transaction end");
            return proceed;
        } catch (Throwable e) {
            connection.rollback();
            logger.error(e.getMessage());
            throw new DataAccessException(e);
        } finally {
            connection.close();
            DataSourceUtils.removeConnection();
        }
    }
}
