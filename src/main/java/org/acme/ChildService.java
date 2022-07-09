package org.acme;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class ChildService {

    @Inject
    Logger log;

    @Inject
    TransactionManager transactionManager;

//    (default): starts a transaction if none was started, stays with the existing one otherwise.
    @Transactional(Transactional.TxType.REQUIRED)
    public void methodRequiresTx(boolean fail) throws SystemException {
        logTX();
        if(fail) throw new TestException(transactionManager.getTransaction());
        // results in tx status: ActionStatus.ABORT_ONLY
    }

//    starts a transaction if none was started
//    if an existing one was started, suspends it and starts a new one for the boundary of that method.
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void methodRequiresNewTx(boolean fail) throws SystemException {
        logTX();
        if(fail) throw new TestException(transactionManager.getTransaction());
        // results in tx status: ActionStatus.ABORTED
    }

//    if a transaction was started, suspends it and works with no transaction for the boundary of the method
//    otherwise works with no transaction.
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void methodNotSupportsTx(boolean fail) throws SystemException {
        logTX();
        if(fail) throw new RuntimeException("simulate failure");
    }


    private void logTX() throws SystemException {
        if (transactionManager.getTransaction() == null) {
            log.info("no active tx found");
            return;
        }
        log.info(transactionManager.getTransaction().toString());
    }
}
