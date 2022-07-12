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

    // Docu: https://quarkus.io/guides/transaction

    /*
        TEST#1 Transactional.TxType.REQUIRED -> Transactional.TxType.REQUIRED
        ChildService.methodRequiresTx runs in same tx as Service.callMethodRequiresTx.
        Exception in ChildService.methodRequiresTx also affects Service.callMethodRequiresTx and causes ABORT/ROLLBACK of the same tx

        tx boundaries:

        tx1 {
            Service.callMethodRequiresTx
            ChildService.methodRequiresTx
        }
     */
    @Transactional
    public void callMethodRequiresTx(boolean fail) throws SystemException {
        logTX();
        try{
            childService.methodRequiresTx(fail);
        } catch (TestException e){
            log.error(e.getTransaction().toString()); // log transaction from child service method after fail
        }
        logTX();
    }


    /*
         TEST#2 Transactional.TxType.REQUIRED -> Transactional.TxType.REQUIRES_NEW:
         ChildService.methodRequiresNewTx executed in separate tx
         Exception in ChildService.methodRequiresNewTx NOT affecting tx of Service.callMethodRequiresNewTx
         1. on call of ChildService.methodRequiresNewTx: current tx of Service.callMethodRequiresNewTx is suspended/paused
         2. ChildService.methodRequiresNewTx executed in new tx
         3. after ChildService.methodRequiresNewTx finished/failed: tx of Service.callMethodRequiresNewTx continues

         tx boundaries:

         tx1 {
          Service.callMethodRequiresNewTx (code before ChildService.methodRequiresNewTx call)
         }
         tx2 {
          ChildService.methodRequiresNewTx
         }
         tx1 {
          Service.callMethodRequiresNewTx (code after ChildService.methodRequiresNewTx call)
        }
     */
    @Transactional
    public void callMethodRequiresNewTx(boolean fail) throws SystemException {
        logTX();
        try{
            childService.methodRequiresNewTx(fail);
        } catch (TestException e){
            log.error(e.getTransaction().toString()); // log transaction from child service method after fail
        }
        logTX();
    }


    /*
         TEST#3 Transactional.TxType.REQUIRED -> Transactional.TxType.NOT_SUPPORTED:
         Exception in ChildService.methodNotSupportsTx NOT affecting tx of Service.callMethodWhichNotSupportsTx because no TX active
         1. on call of ChildService.methodNotSupportsTx: tx of Service.callMethodWhichNotSupportsTx is suspended/paused
         2. org.acme.ChildService.methodNotSupportsTx executed without tx
         3. after ChildService.methodNotSupportsTx finished/failed: tx of Service.callMethodWhichNotSupportsTx continues

        tx boundaries:

        tx1 {
            Service.callMethodWhichNotSupportsTx (code before ChildService.methodNotSupportsTx call)
        }
        ChildService.methodNotSupportsTx (no tx active)
        tx1 {
            Service.callMethodWhichNotSupportsTx (code after ChildService.methodNotSupportsTx call)
        }
     */
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



    //TODO  next tests:
    //      tx + mutiny
    //      Add hibernate orm (in new branch) with included tx mgr
    //      Add quartz

    // TODO test context propagation, precondition:
//    https://quarkus.io/guides/transaction
//    If your @Transactional-annotated method returns a reactive value, such as:
//    - CompletionStage (from the JDK)
//    - Publisher (from Reactive-Streams)
//    - Any type which can be converted to one of the two previous types using Reactive Type Converters
//    then the behaviour is a bit different, because the transaction will not be terminated until the returned reactive value is terminated.
//    In effect, the returned reactive value will be listened to and if it terminates exceptionally the transaction will be marked for rollback,
//    and will be committed or rolled-back only at termination of the reactive value.
}