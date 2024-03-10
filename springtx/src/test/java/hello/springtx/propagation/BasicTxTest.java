package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config {
        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("트랜잭션 커밋 시작");
        txManager.commit(status);
        log.info("트랜잭션 커밋 완료");
    }

    @Test
    void rollback() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("트랜잭션 커밋 시작");
        txManager.rollback(status);
        log.info("트랜잭션 커밋 완료");
    }

    @Test
    void doubleCommit() {
        log.info("트랜잭션 1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("트랜잭션 1 커밋 시작");
        txManager.commit(tx1);
        log.info("트랜잭션 1 커밋 완료");

        log.info("트랜잭션 2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("트랜잭션 2 커밋 시작");
        txManager.commit(tx2);
        log.info("트랜잭션 2 커밋 완료");
    }

    @Test
    void doubleCommitAndRollback() {
        log.info("트랜잭션 1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("트랜잭션 1 커밋 시작");
        txManager.commit(tx1);
        log.info("트랜잭션 1 커밋 완료");

        log.info("트랜잭션 2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("트랜잭션 2 커밋 시작");
        txManager.rollback(tx2);
        log.info("트랜잭션 2 커밋 완료");
    }

    @Test
    void commitInInner() {
        outerCommit(this::innerCommit);
    }

    @Test
    void rollbackInInner() {
        Assertions.assertThatThrownBy(() -> outerCommit(this::innerRollback)).isInstanceOf(UnexpectedRollbackException.class);
    }

    @Test
    void rollbackInOuter() {
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("outer.isNewTransaction()={}", outer.isNewTransaction());

        innerCommit();

        log.info("외부 트랜잭션 롤백");
        txManager.rollback(outer);
    }

    private void outerCommit(Runnable runnable) {
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("outer.isNewTransaction()={}", outer.isNewTransaction());

        runnable.run();

        log.info("외부 트랜잭션 커밋");
        txManager.commit(outer);
    }

    private void innerCommit() {
        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("inner.isNewTransaction()={}", inner.isNewTransaction());
        log.info("내부 트랜잭션 커밋");
        txManager.commit(inner);
    }

    private void innerRollback() {
        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("inner.isNewTransaction()={}", inner.isNewTransaction());
        log.info("내부 트랜잭션 롤백");
        txManager.rollback(inner);
    }

    @Test
    void innerRollbackAndRequiresNew() {
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("outer.isNewTransaction()={}", outer.isNewTransaction());

        log.info("내부 트랜잭션 시작");
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus inner = txManager.getTransaction(definition);
        log.info("inner.isNewTransaction()={}", inner.isNewTransaction());

        log.info("내부 트랜잭션 롤백");
        txManager.rollback(inner);

        log.info("외부 트랜잭션 커밋");
        txManager.commit(outer);
    }
}
