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
       service.callMethodWhichRequiresTx(false);
    }

    @Test
    public void testRequiresTxFail() throws SystemException {
        service.callMethodWhichRequiresTx(true);
    }

    @Test
    public void testRequiresNewTx() throws SystemException {
        service.callMethodWhichRequiresNewTx(false);
    }

    @Test
    public void testRequiresNewTxFail() throws SystemException {
        service.callMethodWhichRequiresNewTx(true);
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