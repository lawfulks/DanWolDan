package com.woongyi.lawfulks.danwoldan.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.woongyi.lawfulks.danwoldan.MonsterHunterData.MonsterData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DBHelper {
	@SuppressLint("SdCardPath")
	public static final String PACKAGE_DIR = "/data/data/com.woongyi.lawfulks.danwoldan/";
	public static final String DATABASE_NAME = "MH_Monster_DB";
//	public static final String DATABASE_NAME = "API_DB";
	public final static String TABLE_NAME = "MH_MONSTER_INFO";
	private Context mContext = null;
	private UtilSQLite sqlLite = null;
	private Cursor cursor = null;

	public DBHelper(Context context) {
		mContext = context;
		sqlLite = new UtilSQLite(mContext, "MH_DB");
		initialize(mContext);

		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

		if (c.moveToFirst()) {
			for (;;) {
				Log.d("123", c.getString(0));
				if (!c.moveToNext()) {
					break;
				}
			}
		}
	}

	public void getMonsterList(ArrayList<MonsterData> list) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		cursor = db.rawQuery("SELECT * From " + TABLE_NAME, null);
		cursor.moveToFirst();
		MonsterData data;

		for (int i=0; i<cursor.getCount(); i++) {
			data = new MonsterData();
			data.setNum(cursor.getInt(0));
			data.setName(cursor.getString(1));
			data.setType(cursor.getString(2));
			data.setWindPressure(cursor.getString(3));
			data.setRoar(cursor.getString(4));
			data.setCut(cursor.getString(5));
			data.setBlow(cursor.getString(6));
			data.setBullet(cursor.getString(7));
			data.setFire(cursor.getInt(8));
			data.setWater(cursor.getInt(9));
			data.setThunder(cursor.getInt(10));
			data.setIce(cursor.getInt(11));
			data.setDragon(cursor.getInt(12));
			data.setPoison(cursor.getInt(13));
			data.setParalysis(cursor.getInt(14));
			data.setSleep(cursor.getInt(15));
			data.setTrap(cursor.getInt(16));
			data.setParalysisTrap(cursor.getInt(17));
			data.setFlashBead(cursor.getInt(18));
			data.setSoundBomb(cursor.getInt(19));
			data.setTrapMeat(cursor.getInt(20));

			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();
	}

	public boolean tableCheck() {
		return sqlLite.isTable(TABLE_NAME);
	}

	public int getRowSize() {
		return sqlLite.getRowCount(TABLE_NAME);
	}

	public boolean isOpen() {
		return sqlLite.isOpen();
	}

	public void close() {
		sqlLite.close();
	}

	public static void initialize(Context context) {
		File folder = new File(PACKAGE_DIR + "databases");
		folder.mkdirs();
		File outfile = new File(PACKAGE_DIR + "databases/" + DATABASE_NAME);
		if (outfile.length() <= 0) {
			AssetManager assetManager = context.getResources().getAssets();
			try {
				InputStream is = assetManager.open(DATABASE_NAME, AssetManager.ACCESS_BUFFER);
				long filesize = is.available();
				byte [] tempdata = new byte[(int)filesize];
				is.read(tempdata);
				is.close();
				outfile.createNewFile();
				FileOutputStream fo = new FileOutputStream(outfile);
				fo.write(tempdata);
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
