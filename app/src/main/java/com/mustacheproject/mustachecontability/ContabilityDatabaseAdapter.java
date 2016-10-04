package com.mustacheproject.mustachecontability;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;
public class ContabilityDatabaseAdapter  {
    ContabilityHelper helper;

    public ContabilityDatabaseAdapter(Context context){
        helper=new ContabilityHelper(context);
    }

    public SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase sql =helper.getReadableDatabase();
        return sql;
    }

    static class ContabilityHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "DatabaseContability";
        private static final String TABLE_NAME = "Transactions";
        private static final int DATABASE_VERSION =2;
        private static final String UID = "_id";
        private static final String ACCOUNT = "Account";
        private static final String DESCRIPTION = "Description";
        private static final String AMOUNT = "Amount";
        private static final String DATE = "Date";
        private static final String IMAGEURI="imageUri";
        /*Comandi SQL per creare e distruggere la tabella*/
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ACCOUNT+" VARCHAR(255), " + AMOUNT + " DOUBLE(7,2), " + DATE + " DATETIME," + DESCRIPTION + " VARCHAR(300),"+IMAGEURI+" TEXT);";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        Uri imageUri;

        public ContabilityHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }

    }
    /*Metodo per l'inserimento di un nuovo record sul DB*/
    public long InsertData (String account, String description, Double amount, String date,Uri imageUri){
        String imageUriString="";
        if (imageUri!=null)
        imageUriString= imageUri.toString();
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ContabilityHelper.ACCOUNT, account);
        contentValues.put(ContabilityHelper.DESCRIPTION,description);
        contentValues.put(ContabilityHelper.AMOUNT, amount);
        contentValues.put(ContabilityHelper.DATE, date);
        contentValues.put(ContabilityHelper.IMAGEURI,imageUriString);
        long id=db.insert(ContabilityHelper.TABLE_NAME, null, contentValues);
        db.close();
        return id;

    }



    /*Metodo per la richiesta di dati dal DB il quale restituisce una variabile Cursos (?) per la stampa dei dati*/
    public Cursor getAllData(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String[] columns={"_id", ContabilityHelper.ACCOUNT, ContabilityHelper.AMOUNT, ContabilityHelper.DATE, ContabilityHelper.DESCRIPTION};
        Cursor cursor=db.query(ContabilityHelper.TABLE_NAME, columns, null, null, null, null, ContabilityHelper.DATE);
        return cursor;


    }

    public DataProvider getContact(int id){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query(ContabilityHelper.TABLE_NAME, new String[]{"_id"+ ContabilityHelper.ACCOUNT, ContabilityHelper.AMOUNT, ContabilityHelper.DATE, ContabilityHelper.DESCRIPTION},"_id=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();

        DataProvider dataProvider= new DataProvider(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getDouble(2),cursor.getString(3),cursor.getString(4),cursor.getNotificationUri());
        return dataProvider;
    }

    public void deleteMovement(DataProvider dataProvider){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(ContabilityHelper.TABLE_NAME,"_id=?",new String[]{String.valueOf(dataProvider.get_id())});
        db.close();
    }

    public int getMovementsCount(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM" + ContabilityHelper.TABLE_NAME, null);
        cursor.close();
        db.close();
        return cursor.getCount();
    }

    public int updateContact(DataProvider dataProvider){
        SQLiteDatabase db= helper.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(ContabilityHelper.ACCOUNT, dataProvider.get_account());
        values.put(ContabilityHelper.DESCRIPTION,dataProvider.get_reason());
        values.put(ContabilityHelper.AMOUNT, dataProvider.get_amount());
        values.put(ContabilityHelper.DATE, dataProvider.get_data());
        values.put(ContabilityHelper.IMAGEURI, dataProvider.get_imageUri().toString());
        int rowsAffected=db.update(ContabilityHelper.TABLE_NAME,values,"_id=?",new String[]{String.valueOf(dataProvider.get_id())});
        db.close();
        return rowsAffected;

    }

    public void deleteAllData(){
        SQLiteDatabase db= helper.getWritableDatabase();
        db.delete(ContabilityHelper.TABLE_NAME,null,null);
        db.close();
    }


}