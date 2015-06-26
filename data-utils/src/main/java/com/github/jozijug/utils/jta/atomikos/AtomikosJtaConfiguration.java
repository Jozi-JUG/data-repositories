package com.github.jozijug.utils.jta.atomikos;

import com.atomikos.icatch.config.UserTransactionService;
import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.UserTransaction;
import java.io.File;
import java.util.Properties;

/**
 * @author Corneil du Plessis
 */
@Configuration
public class AtomikosJtaConfiguration {

    @Bean(initMethod = "init", destroyMethod = "shutdownForce")
    public UserTransactionService userTransactionService() {
        Properties properties = new Properties();
        properties.setProperty("com.atomikos.icatch.log_base_dir", getLogBaseDir());
        return new UserTransactionServiceImp(properties);
    }

    private String getLogBaseDir() {
        return new File("/tmp", "transaction-logs").getAbsolutePath();
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @Autowired
    public UserTransactionManager atomikosTransactionManager(UserTransactionService transactionService) throws Exception {
        UserTransactionManager manager = new UserTransactionManager();
        manager.setStartupTransactionService(false);
        manager.setForceShutdown(true);
        return manager;
    }

    @Bean(name = "atomikosUserTransaction")
    public UserTransaction userTransaction() {
        UserTransactionImp result = new UserTransactionImp();
        return result;
    }
    @Bean(name = "transactionManager")
    @Autowired
    PlatformTransactionManager platformTransactionManager(UserTransactionService transactionService, UserTransactionManager transactionManager) throws Exception {
        return new JtaTransactionManager(userTransaction(), transactionManager);
    }
}