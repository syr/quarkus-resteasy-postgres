package org.acme;

import org.acme.service.TestService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestResource {

    @Inject
    Logger log;

    @Inject
    TestService testService;

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

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public void test() throws Exception {

//        Docu: https://quarkus.io/guides/transaction

        logTX();

//        TEST#1 Transactional.TxType.REQUIRED: methodRequiresTX() runs in same tx as test().
//        Exception in subservice also affects/propagates to main service and causes ABORT/ROLLBACK because TX is identical
//        try{
//          testService.methodRequiresTX(false);
//          testService.methodRequiresTX(true);
//        } catch (TestException e){
//            log transaction from methodRequiresTX after fail
//            log.error(e.getTransaction().toString());
//        }


//        TEST#2 Transactional.TxType.REQUIRES_NEW:
//        Exception in subservice NOT affecting/propagating to main service because separate dedicated TX
//          1. on methodRequiresNewTX(): tx of test() is suspended/paused
//          2. methodRequiresNewTX() executed in new tx
//          3. after methodRequiresNewTX finished/failed: test() continues with its tx
        try{
//          testService.methodRequiresNewTX(false);
          testService.methodRequiresNewTX(true);
        } catch (TestException e){
//            log transaction from methodRequiresTX after fail
            log.error(e.getTransaction().toString());
        }


//        TEST#3 Transactional.TxType.NOT_SUPPORTED:
//        Exception in subservice NOT affecting/propagating to main service because no TX active
//          1. on methodNotSupportsTX(): tx of test() is suspended/paused
//          2. methodNotSupportsTX() executed without tx
//          3. after methodNotSupportsTX: test() continues with its tx
//        try{
//            testService.methodNotSupportsTX(false);
//            testService.methodNotSupportsTX(true);
//        } catch (Exception e){}

        logTX();

    }

    private void logTX() throws SystemException {
        log.info(transactionManager.getTransaction().toString());
    }

}