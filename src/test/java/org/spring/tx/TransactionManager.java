package org.spring.tx;

/**
 * @author zenghui
 * 2020/8/5
 */
public class TransactionManager {
    public void start() {
        System.out.println("start tx");
    }

    public void commit() {
        System.out.println("commit tx");
    }

    public void rollback() {
        System.out.println("rollback tx");
    }
}
