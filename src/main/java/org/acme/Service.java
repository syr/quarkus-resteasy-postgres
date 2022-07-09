package org.acme;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class Service {

    @Inject
    Logger log;

    @Inject
    ChildService childService;

    @Inject
    TransactionManager transactionManager;

// TODO test context propagation, precondition:
//    https://quarkus.io/guides/transaction
//    If your @Transactional-annotated method returns a reactive value, such as:
//    - CompletionStage (from the JDK)
//    - Publisher (from Reactive-Streams)
//    - Any type which can be converted to one of the two previous types using Reactive Type Converters
//    then the behaviour is a bit different, because the transaction will not be terminated until the returned reactive value is terminated.
//    In effect, the returned reactive value will be listened to and if it terminates exceptionally the transaction will be marked for rollback,
//    and will be committed or rolled-back only at termination of the reactive value.

    // Docu: https://quarkus.io/guides/transaction


    // TEST#1 Transactional.TxType.REQUIRED: methodRequiresTX() runs in same tx as test().
    // Exception in subservice also affects/propagates to main service and causes ABORT/ROLLBACK because TX is identical
    @Transactional
    public void callMethodWhichRequiresTx(boolean fail) throws SystemException {
        logTX();
        try{
            childService.methodRequiresTx(fail);
        } catch (TestException e){
            log.error(e.getTransaction().toString()); // log transaction from child service method after fail
        }
        logTX();
    }


    // TEST#2 Transactional.TxType.REQUIRES_NEW:
    // Exception in subservice NOT affecting/propagating to main service because separate dedicated TX
    // 1. on methodRequiresNewTX(): tx of test() is suspended/paused
    // 2. methodRequiresNewTX() executed in new tx
    // 3. after methodRequiresNewTX finished/failed: test() continues with its tx
    @Transactional
    public void callMethodWhichRequiresNewTx(boolean fail) throws SystemException {
        logTX();
        try{
            childService.methodRequiresNewTx(fail);
        } catch (TestException e){
            log.error(e.getTransaction().toString()); // log transaction from child service method after fail
        }
        logTX();
    }


    // TEST#3 Transactional.TxType.NOT_SUPPORTED:
    // Exception in subservice NOT affecting/propagating to main service because no TX active
    // 1. on methodNotSupportsTX(): tx of test() is suspended/paused
    // 2. methodNotSupportsTX() executed without tx
    // 3. after methodNotSupportsTX: test() continues with its tx
    @Transactional
    public void callMethodWhichNotSupportsTx(boolean fail) throws SystemException {
        logTX();
        try{
            childService.methodNotSupportsTx(fail);
        } catch (RuntimeException e){
        }
        logTX();
    }

    private void logTX() throws SystemException {
        log.info(transactionManager.getTransaction().toString());
    }

}