package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import javax.transaction.SystemException;

@QuarkusTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ServiceTest {

    @Inject
    Logger log;

    @Inject
    Service service;

    @Test
    public void testRequiresTx() throws SystemException {
        log.info("#####################################################################");
        log.info("TEST#1 Transactional.TxType.REQUIRED -> Transactional.TxType.REQUIRED");
        service.callMethodRequiresTx(false);

    }

    @Test
    public void testRequiresTxFail() throws SystemException {
        log.info("#####################################################################");
        log.info("TEST#1 Transactional.TxType.REQUIRED -> Transactional.TxType.REQUIRED (fails)");
        service.callMethodRequiresTx(true);
    }

    @Test
    public void testRequiresNewTx() throws SystemException {
        log.info("#####################################################################");
        log.info("TEST#2 Transactional.TxType.REQUIRED -> Transactional.TxType.REQUIRES_NEW");
        service.callMethodRequiresNewTx(false);
    }

    @Test
    public void testRequiresNewTxFail() throws SystemException {
        log.info("#####################################################################");
        log.info("TEST#2 Transactional.TxType.REQUIRED -> Transactional.TxType.REQUIRES_NEW (fails)");
        service.callMethodRequiresNewTx(true);
    }

    @Test
    public void testNotSupportsTx() throws SystemException {
        log.info("#####################################################################");
        log.info("TEST#3 Transactional.TxType.REQUIRED -> Transactional.TxType.NOT_SUPPORTED");
        service.callMethodWhichNotSupportsTx(false);
    }

    @Test
    public void testNotSupportsTxFail() throws SystemException {
        log.info("#####################################################################");
        log.info("TEST#3 Transactional.TxType.REQUIRED -> Transactional.TxType.NOT_SUPPORTED (fails)");
        service.callMethodWhichNotSupportsTx(true);
    }

}