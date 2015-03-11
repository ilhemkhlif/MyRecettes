package com.example.recettes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "MyDBName.db";
   public static final String RECETTES_TABLE_TITLE = "recettes";
   public static final String RECETTES_COLUMN_ID = "id";
   public static final String RECETTES_COLUMN_TITLE = "title";
   public static final String RECETTES_COLUMN_INGREDIENTS = "ingredients";
   public static final String RECETTES_COLUMN_REALISATION = "realisation";
  

   private HashMap hp;

   public DBHelper(Context context)
   {
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      // TODO Auto-generated method stub
      db.execSQL(
      "create table recettes " +
      "(id integer primary key, title text,ingredients text,realisation text)"
      );
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
      db.execSQL("DROP TABLE IF EXISTS recettes");
      onCreate(db);
   }

   public boolean insertRecette (String T, String I, String R)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();

      contentValues.put("title", T);
      contentValues.put("ingredients", I);
      contentValues.put("realisation", R);	
    
      db.insert("recettes", null, contentValues);
      return true;
   }
   public Cursor getData(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from recettes where id="+id+"", null );
      return res;
   }
   public int numberOfRows(){
      SQLiteDatabase db = this.getReadableDatabase();
      int numRows = (int) DatabaseUtils.queryNumEntries(db, RECETTES_TABLE_TITLE);
      return numRows;
   }
   public boolean updateRecette (Integer id, String T, String I, String R)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("title", T);
      contentValues.put("ingredients", I);
      contentValues.put("realisation", R);	
      db.update("recettes", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
      return true;
   }

   public Integer deleteContact (Integer id)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete("recettes", 
      "id = ? ", 
      new String[] { Integer.toString(id) });
   }
   public ArrayList getAllCotacts()
   {
      ArrayList array_list = new ArrayList();
      //hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from recettes", null );
      res.moveToFirst();
      while(res.isAfterLast() == false){
      array_list.add(res.getString(res.getColumnIndex(RECETTES_COLUMN_TITLE)));
      res.moveToNext();
      }
   return array_list;
   }
}