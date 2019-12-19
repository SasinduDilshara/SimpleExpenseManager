package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DbHelper.DbHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistantAccountDAO implements AccountDAO {

    DbHelper db;

    PersistantAccountDAO(Context context)
    {
        db = new DbHelper(context);
    }

    @Override
    public List<String> getAccountNumbersList() {

        List<String> list = new ArrayList<>();
        list = null;
          ArrayList<Account> accounts =   db.selectAllAccount();
          for(Account acc:accounts)
          {
              list.add(acc.getAccountNo());
          }
        if(list == null)
        {
            System.out.println("ACCOUNT IS NULL");
            return list;
        }
         else {
            return list;
        }
    }

    @Override
    public List<Account> getAccountsList() {
        ArrayList<Account> accounts =   db.selectAllAccount();


        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Account acc = db.selectAccount(accountNo);
        if(acc == null)
        {
            System.out.println("ACCOUNT IS NULL");
            return acc;
        }
        else{
            return acc;
        }
    }

    @Override
    public void addAccount(Account account) {

        long a  = db.insertAccount(account);
        if(a==-1) {
            System.out.println("No insert happen in Account");
        }

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }
}
