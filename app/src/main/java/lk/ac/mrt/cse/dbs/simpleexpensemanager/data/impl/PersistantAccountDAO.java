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

    public PersistantAccountDAO(DbHelper dbHelper)
    {
        this.db = dbHelper;
    }

    @Override
    public List<String> getAccountNumbersList() {
        System.out.println("11111111111111111111111");

        List<String> list = new ArrayList<>();
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
        System.out.println("222222222222222222222222");
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
        System.out.println(account.getAccountHolderName());
                System.out.println(account.getAccountNo());
                        System.out.println(account.getBalance());
        System.out.println(account);

        long a  = db.insertAccount(account);
        if(a==-1) {
            System.out.println("No insert happen in Account");
        }

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        boolean a =db.deleteAccount(accountNo);
        if(!a)
        {
            System.out.println("Delete not happen in account");
        }

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {


//        db.updateAccount(accountNo, expenseType,  amount);

    }
}
