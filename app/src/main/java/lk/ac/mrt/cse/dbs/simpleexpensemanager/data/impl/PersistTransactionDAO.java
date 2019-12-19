package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DbHelper.DbHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistTransactionDAO implements TransactionDAO {


    DbHelper db;

    public  PersistTransactionDAO(DbHelper dbHelper)
    {
        db = dbHelper;
    }


    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
    db.insertTransaction(new Transaction ( date,  accountNo,  expenseType, amount));
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        return db.selectAllTransactions();
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
//        System.out.println("getPaginatedTransactionLogsgetPaginatedTransactionLogsgetPaginatedTransactionLogsgetPaginatedTransactionLogsgetPaginatedTransactionLogsgetPaginatedTransactionLogs");
//        ArrayList<Transaction> transactions = db.selectAllTransactions();
//
//        int size = transactions.size();
//        if (size <= limit) {
//            return transactions;
//        }
//        // return the last <code>limit</code> number of transaction logs
//        return transactions.subList(size - limit, size);
        return db.selectAllTransactions();


    }
}
