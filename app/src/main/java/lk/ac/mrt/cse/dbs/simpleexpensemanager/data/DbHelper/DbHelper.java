package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DbHelper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public abstract class DbHelper extends SQLiteOpenHelper {


    public DbHelper(Context context) {
        super(context, DabDetails.db , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE account (" + DabDetails.AccountsTable.accountNo + " TEXT PRIMARY KEY, "
                + DabDetails.AccountsTable.bankName + " TEXT" + DabDetails.AccountsTable.accountHolderName + "Text,"
                + DabDetails.AccountsTable.balance + " REAL" + " )");
        db.execSQL("CREATE TABLE userTransaction(" + DabDetails.TransactionsTable.date + " INT, "
                + "FOREIGN KEY (" + DabDetails.TransactionsTable.accountNo + ") REFERENCES account(" + DabDetails.AccountsTable.accountNo +"),"
                + DabDetails.TransactionsTable.expenseType+" " +"Text,"
                + DabDetails.TransactionsTable.amount + " REAL" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("DROP TABLE IF EXISTS userTransaction");
        onCreate(db);
    }


}
