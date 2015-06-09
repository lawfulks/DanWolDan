package com.woongyi.lawfulks.danwoldan.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UtilSQLite {
	final private SQLiteDatabase mDatabase;
	private int count = 0;
	private int counterr = 0;

	public UtilSQLite(final Context con, final String database) {
		String dbName = "";
		dbName = database;
		mDatabase = con.openOrCreateDatabase(dbName, 0, null);
	}

	public void createTable(final String createSql) {
		if (mDatabase.isOpen()){
			mDatabase.execSQL(createSql);
		}
	}

	public void insert(final String table, final String key[], final String value[]) {
		final ContentValues newValues = new ContentValues();
		for (int i = 0; i < key.length; i++){
			newValues.put(key[i], value[i]);
		}
		count++;
		Log.e("db insert", String.valueOf(count));

		if (mDatabase.isOpen()){
			try{
				mDatabase.beginTransaction();
				mDatabase.insert(table, null, newValues);
				mDatabase.setTransactionSuccessful();
			} catch (SQLException e){
				counterr++;
				Log.e("db insert ee", String.valueOf(counterr));
			} finally {
				mDatabase.endTransaction();
			}
		}
	}

	public void update(final String table, final String key[], final String value[], final String where) {
		final ContentValues updateValues = new ContentValues();
		for (int i = 0; i < key.length; i++){
			updateValues.put(key[i], value[i]);
		}
		if (mDatabase.isOpen()){
			mDatabase.update(table, updateValues, where, null);
		}
	}

	public void delete(final String table, final String where) {
		if (mDatabase.isOpen()){
			mDatabase.delete(table, where, null);
		}
	}

	public void delete(final String table) {

		final String sql = new StringBuilder("DROP TABLE IF EXISTS ").append(table).toString();

		if (mDatabase.isOpen()){			
			mDatabase.execSQL(sql);			
		}
	}

	public Cursor select(final String table, final String columns[], final String where) {
		final Cursor cursor = mDatabase.query(table, columns, where, null, null, null, null);
		return cursor;
	}

	public Cursor selectOrder(final String table, final String where, final String columns[], final String having, final String order) {
		final Cursor cursor = mDatabase.query(table, columns, where, null, "url", having, order);
		return cursor;
	}

	public Cursor selectCustome(final String where, final String having, final String match){
		String sql = null;
		if(match == null){
			sql = new StringBuilder(
					"SELECT *, COUNT(*) AS ICNT, SUM(favorite) AS FCNT, SUM(count) AS CNT")
			.append(" FROM history")
			.append(" WHERE ").append(where)			
			.append(" GROUP BY (Case type When 0 Then msg Else url End)")
			.append(" HAVING ").append(having)
			.append(" ORDER BY recordtime asc")
			.toString();
		} else {
			sql = new StringBuilder(
					"SELECT *, COUNT(*) AS ICNT, SUM(favorite) AS FCNT, SUM(count) AS CNT")
			.append(" FROM history")
			.append(" WHERE ").append(where)
			.append(" AND ").append(match)
			.append(" GROUP BY (Case type When 0 Then msg Else url End)")
			.append(" HAVING ").append(having)
			.append(" ORDER BY recordtime asc")
			.toString();
		}

		final Cursor cursor = mDatabase.rawQuery(sql, null);
		return cursor;
	}

	public boolean isOpen() {
		return mDatabase.isOpen();
	}

	public void close() {
		if (mDatabase.isOpen()){
			mDatabase.close();
		}
	}

	public boolean isTable(final String table) {
		boolean bRet = false;
		final String sql = new StringBuilder(
				"select name from sqlite_master where type = 'table' and name = '")
		.append(table).append("'").toString();
		final Cursor cursor = mDatabase.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			bRet = true;
		}
		cursor.close();
		return bRet;
	}

	public int getRowCount(final String table) {
		final Cursor cursor = mDatabase.query(table, null, null, null, null, null, null);
		final int nCnt = cursor.getCount();
		cursor.close();
		return nCnt;
	}
}

