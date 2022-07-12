package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.SystemException;

@QuarkusTest
public class ServiceTest {

    @Inject
    Service service;

    @Test
    public void testRequiresTx() throws SystemException {
       service.callMethodRequiresTx(false);
    }

    @Test
    public void testRequiresTxFail() throws SystemException {
        service.callMethodRequiresTx(true);
    }

    @Test
    public void testRequiresNewTx() throws SystemException {
        service.callMethodRequiresNewTx(false);
    }

    @Test
    public void testRequiresNewTxFail() throws SystemException {
        service.callMethodRequiresNewTx(true);
    }

    @Test
    public void testNotSupportsTx() throws SystemException {
        service.callMethodWhichNotSupportsTx(false);
    }

    @Test
    public void testNotSupportsTxFail() throws SystemException {
        service.callMethodWhichNotSupportsTx(true);
    }

}