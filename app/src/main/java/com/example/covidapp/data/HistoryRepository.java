package com.example.covidapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

public class HistoryRepository {
    private SQLiteDatabase db;

    public HistoryRepository(Context context){
        DatabaseContext dbHelper = new DatabaseContext(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(HistoryEntity entity){
        ContentValues contentValues = new ContentValues();
        contentValues.put(HistoryEntity.CountryColumn, entity.getCountry());
        contentValues.put(HistoryEntity.UpdateDateColumn, entity.getUpdateDate());

        return db.insert(HistoryEntity.TableName, null, contentValues);
    }

    public void delete(long id){
        db.delete(HistoryEntity.TableName, HistoryEntity.IdColumn + " = " + id, null);
    }
    public HistoryEntity getLast() {
        Cursor cursor = this.getSelectQueryCursor();

        if(cursor.moveToFirst()){
            return this.parse(cursor);
        }

        return null;
    }

    public ArrayList<HistoryEntity> getAll(){
        ArrayList<HistoryEntity> data = new ArrayList<HistoryEntity>();
        Cursor cursor = this.getSelectQueryCursor();

        while(cursor.moveToNext()){
            data.add(this.parse(cursor));
        }
        return data;
    }

    private Cursor getSelectQueryCursor(){
        String[] columns =  {
                HistoryEntity.IdColumn,
                HistoryEntity.CountryColumn,
                HistoryEntity.UpdateDateColumn
        };
        Cursor cursor = db.query(HistoryEntity.TableName, columns, null, null,null, null, "id desc");

        return cursor;
    }

    private HistoryEntity parse(Cursor cursor){
        HistoryEntity historyEntity = new HistoryEntity();

        int id = cursor.getInt(cursor.getColumnIndex(HistoryEntity.IdColumn));
        historyEntity.setId(id);

        String country = cursor.getString(cursor.getColumnIndex(HistoryEntity.CountryColumn));
        historyEntity.setCountry(country);

        String updateDate = cursor.getString(cursor.getColumnIndex(HistoryEntity.UpdateDateColumn));
        historyEntity.setUpdateDate(updateDate);

        return  historyEntity;
    }
}
