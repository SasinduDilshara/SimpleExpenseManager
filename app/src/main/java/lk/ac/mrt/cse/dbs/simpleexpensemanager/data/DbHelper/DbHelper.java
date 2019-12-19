package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DbHelper.DabDetails;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DbHelper.DbHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


public  class DbHelper extends SQLiteOpenHelper {

    SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
    public DbHelper(Context context) {
        super(context, DabDetails.db , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE account (" + DabDetails.AccountsTable.accountNo + " TEXT PRIMARY KEY, "
                + DabDetails.AccountsTable.bankName + " TEXT" + DabDetails.AccountsTable.accountHolderName + "Text,"
                + DabDetails.AccountsTable.balance + " REAL" + " )");

        db.execSQL("CREATE TABLE userTransaction(" + DabDetails.TransactionsTable.date + " TEXT, "
                + DabDetails.TransactionsTable.accountNo + " Text not null, "
                + DabDetails.TransactionsTable.expenseType+" Text not null,"
                + DabDetails.TransactionsTable.amount + " REAL not null, Foreign key (accountNo) references account(accountNo))"
        );
    }
    //                "FOREIGN KEY REFERENCES account(" + DabDetails.AccountsTable.accountNo +"),"

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("DROP TABLE IF EXISTS userTransaction");
        onCreate(db);
    }


    public long insertAccount(Account account)
    {
    long a =-1;
    if(account.getAccountNo()!= null) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountNo",account.getAccountNo());
        contentValues.put("bankName",account.getBankName());
        contentValues.put("accountHolderName",account.getAccountHolderName());
        contentValues.put("balance",account.getBalance());
        System.out.println(account.getAccountHolderName());
        System.out.println(contentValues);
        a = db.insert("account",null,contentValues);
//        db.rawQuery("INSERT INTO account(accountNo,bankName,accountHolderName,balance) VALUES (?,?,?,?)",new String[]{account.getAccountNo(),account.getBankName(),account.getAccountHolderName(),String.valueOf(account.getBalance())});

    }
    return a;


    }
    public void updateAccount(Account account)
    {
        if(account.getAccountNo()!= null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("accountNo",account.getAccountNo());
            contentValues.put("bankName",account.getBankName());
            contentValues.put("accountHolderName",account.getAccountHolderName());
            contentValues.put("balance",account.getBalance());
            db.insert("account",null,contentValues);
        }

    }




    public boolean deleteAccount(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("account","accountNo ="+id,null)>0;
    }
    public ArrayList<Account> selectAllAccount()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from account",null);
        int count = res.getCount();
        ArrayList<Account> arr = new ArrayList<>();
        if(count ==0)
        {
            return arr;
        }
        else
        {
            while(res.moveToNext())
            {
                String accountNo = res.getString(0);
                String bankName = res.getString(1);
                String accountHolder = res.getString(2);
                double balance = res.getDouble(3);

                arr.add( new Account(accountNo,bankName,accountHolder,balance));

            }
            return arr;
        }


    }
    public Account selectAccount(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from account where accountNo = ?",new String[]{id});
        int count = res.getCount();
        Account acc = null;
        if(count ==0)
        {
            return null;
        }
        else{
            while(res.moveToNext())
            {
                String accountNo = res.getString(0);
                String bankName = res.getString(1);
                String accountHolder = res.getString(2);
                double balance = res.getDouble(3);

                 acc = new Account(accountNo,bankName,accountHolder,balance);

            }
            return acc;
        }

    }


    public void insertTransaction(Transaction transaction)
    {

        if(transaction.getAccountNo()!= null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("date",sdfr.format( transaction.getDate() ));
            contentValues.put("accountNo",transaction.getAccountNo());
            contentValues.put("expensesType",transaction.getExpenseType().toString());
            contentValues.put("amount",transaction.getAmount());
            db.insert("userTransaction",null,contentValues);
        }

    }
    public ArrayList<Transaction> selectAllTransactions()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from userTransaction",null);
        int count = res.getCount();
        ArrayList<Transaction> arr = new ArrayList<>();
        if(count ==0)
        {
            return arr;
        }
        else
        {


            while(res.moveToNext())
            {
                String date = res.getString(0);
                String accountNum = res.getString(1);
                String expensesType = res.getString(2);
                double amount = res.getDouble(3);
                ExpenseType type = ExpenseType.valueOf(expensesType);
                Date date_ = null;
                try {
                    date_ = sdfr.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                arr.add( new Transaction(date_,accountNum,type,amount));

            }
            return arr;
        }
    }
    public List<Transaction> getPaginatedTransactionLogs(int limit)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Transaction> list = new ArrayList<>();
//        ArrayList<int> num = new ArrayList<>();
        list = this.selectAllTransactions();
        if(limit == 0)
        {
            return list;
        }
        else
        {
//            limit = limit.to
//            num.add(limit);
            Cursor res = db.rawQuery("select * from userTransaction limit  ?",new String[]{String.valueOf(limit)});


            while(res.moveToNext())
            {
                String date = res.getString(0);
                String accountNum = res.getString(1);
                String expensesType = res.getString(2);
                double amount = res.getDouble(3);
                ExpenseType type = ExpenseType.valueOf(expensesType);
                Date date_ = null;
                try {
                    date_ = sdfr.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ArrayList<Transaction> list_ = new ArrayList<>();
                list_.add( new Transaction(date_,accountNum,type,amount));

            }
            return list;

        }

    }
    public Transaction selectTransaction(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from userTransaction where accountNo = ?",new String[]{id});
        int count = res.getCount();
        Transaction acc = null;
        if(count ==0)
        {
            return null;
        }
        else{
            while(res.moveToNext())
            {
                String date = res.getString(0);
                String accountNum = res.getString(1);
                String expensesType = res.getString(2);
                double amount = res.getDouble(3);
                ExpenseType type = ExpenseType.valueOf(expensesType);
                Date date_ = null;
                try {
                    date_ = sdfr.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                acc = new Transaction(date_,accountNum,type,amount);

            }
            return acc;
        }
    }

}
