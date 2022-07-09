package org.acme;

import javax.transaction.Transaction;

public class TestException extends RuntimeException{
    Transaction transaction;

    public TestException(Transaction transaction){
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
