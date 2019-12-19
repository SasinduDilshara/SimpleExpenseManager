package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DbHelper;


import android.provider.BaseColumns;


public class DabDetails {
    static final String db = "170024R";
    public static class AccountsTable implements BaseColumns{
        public static final String accountNo = "accountNo";
        public static final String bankName = "bankName";
        public static final String accountHolderName = "accountHolderNames";
        public static final String balance = "balance";
    }

    public static class TransactionsTable implements BaseColumns{
        public static final String date = "date";
        public static final String accountNo = "accountNo";
        public static final String expenseType = "expenseType";
        public static final String amount = "amount";
    }
}