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

//        TEST#1 Transactional.TxType.REQUIRED: methodRequiresTX() runs in same tx as test()
//        logTX();
//        testService.methodRequiresTX();
//            TODO TEST#1b add fail & rollback tests


//        TEST#2 Transactional.TxType.REQUIRES_NEW:
//          1. on methodRequiresNewTX(): tx of test() is suspended/paused
//          2. methodRequiresNewTX() executed in new tx
//          3. after methodRequiresNewTX: test() continues with its tx
//        logTX();
//        testService.methodRequiresNewTX();
//        logTX();
//            TODO TEST#2b add fail & rollback tests

//        TEST#3 Transactional.TxType.NOT_SUPPORTED:
//          1. on methodNotSupportsTX(): tx of test() is suspended/paused
//          2. methodNotSupportsTX() executed without tx
//          3. after methodNotSupportsTX: test() continues with its tx
        logTX();
        testService.methodNotSupportsTX();
        logTX();

//            TODO TEST#3b add fail & rollback tests
    }

    private void logTX() throws SystemException {
        log.info(transactionManager.getTransaction().toString());
    }

}