package com.woongyi.lawfulks.danwoldan.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.woongyi.lawfulks.danwoldan.AdapterData.GuildQuestData;
import com.woongyi.lawfulks.danwoldan.AdapterData.MealInfoData;
import com.woongyi.lawfulks.danwoldan.AdapterData.MealSkillInfoData;
import com.woongyi.lawfulks.danwoldan.AdapterData.MonsterDestroyData;
import com.woongyi.lawfulks.danwoldan.AdapterData.MonsterInfoData;
import com.woongyi.lawfulks.danwoldan.AdapterData.MonsterListData;
import com.woongyi.lawfulks.danwoldan.AdapterData.SkillInfoData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DBHelper {
	private static DBHelper instance;
	@SuppressLint("SdCardPath")
	public static final String PACKAGE_DIR = "/data/data/com.woongyi.lawfulks.danwoldan/";
	public static final String DATABASE_NAME = "MH_Monster_DB";
	public final static String MONSTER_INFO_TABLE_NAME = "MH_MONSTER_INFO";
	public final static String MONSTER_DESTROY_TABLE_NAME = "MH_MONSTER_DESTROY";
	public final static String SKILL_INFO_TABLE_NAME = "MH_SKILL_INFO";
	public final static String MEAL_SKILL_INFO_TABLE_NAME = "MH_MEAL_SKILL_INFO";
	public final static String MEAL_INFO_TABLE_NAME = "MH_MEAL_INFO";
	public final static String SEARCH_CONDITION_TABLE_NAME = "MH_SEARCH_CONDITION";
	public final static String GUILDQUEST_CONDITION_TABLE_NAME = "MH_GUILDQUEST_CONDITION";
	public final static String ADORNMENT_INFO_TABLE_NAME = "MH_ADORNMENT_INFO";
	private Context mContext = null;
	private UtilSQLite sqlLite = null;
	private Cursor cursor = null;

	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper(context);
		}

		return instance;
	}

	private DBHelper(Context context) {
		mContext = context;
		sqlLite = new UtilSQLite(mContext, "MH_DB");

		boolean dbFileCheck = PreferenceUtil.instance(mContext.getApplicationContext()).getBooleanPref("dbFileCheck");

		if (!dbFileCheck) {
			initialize(mContext);
			PreferenceUtil.instance(mContext.getApplicationContext()).setBooleanPref("dbFileCheck", true);
		}
	}

	public void getMonsterList(ArrayList<MonsterListData> list) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		cursor = db.rawQuery("SELECT * FROM " + MONSTER_INFO_TABLE_NAME, null);
		cursor.moveToFirst();

		MonsterListData data;
		for (int i=0; i<cursor.getCount(); i++) {
			data = new MonsterListData();

			data.setNum(cursor.getInt(0));
			data.setName(cursor.getString(1));

			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getMonsterNameList(ArrayList<String> list) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		cursor = db.rawQuery("SELECT * FROM " + MONSTER_INFO_TABLE_NAME, null);
		cursor.moveToFirst();

		for (int i=0; i<cursor.getCount(); i++) {
			list.add(cursor.getString(1));

			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getMonsterInfoList(ArrayList<MonsterInfoData> list) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		cursor = db.rawQuery("SELECT * FROM " + MONSTER_INFO_TABLE_NAME, null);
		cursor.moveToFirst();
		MonsterInfoData data;

		for (int i=0; i<cursor.getCount(); i++) {
			data = new MonsterInfoData();
			data.setNum(cursor.getInt(0));
			data.setName(cursor.getString(1));
			data.setType(cursor.getString(2));
			data.setAttribute(cursor.getString(3));
			data.setWindPressure(cursor.getString(4));
			data.setRoar(cursor.getString(5));
			data.setCut(cursor.getString(6));
			data.setBlow(cursor.getString(7));
			data.setBullet(cursor.getString(8));
			data.setFire(cursor.getInt(9));
			data.setWater(cursor.getInt(10));
			data.setThunder(cursor.getInt(11));
			data.setIce(cursor.getInt(12));
			data.setDragon(cursor.getInt(13));
			data.setPoison(cursor.getInt(14));
			data.setParalysis(cursor.getInt(15));
			data.setSleep(cursor.getInt(16));
			data.setTrap(cursor.getInt(17));
			data.setParalysisTrap(cursor.getInt(18));
			data.setFlashBead(cursor.getInt(19));
			data.setSoundBomb(cursor.getInt(20));
			data.setTrapMeat(cursor.getInt(21));
			data.setCapture(cursor.getString(22));

			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getMonsterDestroyList(ArrayList<MonsterDestroyData> list, int infoNum) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		cursor = db.rawQuery("SELECT * FROM " + MONSTER_DESTROY_TABLE_NAME, null);
		cursor.moveToFirst();
		MonsterDestroyData data;

		for (int i=0; i<cursor.getCount(); i++) {
			int num = cursor.getInt(2);
			if (num == infoNum) {
				data = new MonsterDestroyData();
				data.setNum(cursor.getInt(0));
				data.setName(cursor.getString(1));
				data.setInfoNum(cursor.getInt(2));
				data.setPart(cursor.getString(3));
				data.setContent(cursor.getString(4));

				list.add(data);
			}
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getSkillNameList(ArrayList<String> list) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		cursor = db.rawQuery("SELECT * FROM " + SKILL_INFO_TABLE_NAME, null);

		cursor.moveToFirst();

		for (int i = 0; i < cursor.getCount(); i++) {
			list.add(cursor.getString(1));
			list.add(cursor.getString(2));
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getSkillInfoList(ArrayList<SkillInfoData> list, String query) {
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		int count = 1;
		SkillInfoData data;

		do {
			if (query.equals("")) {
				cursor = db.rawQuery("SELECT * FROM " + SKILL_INFO_TABLE_NAME, null);
				count = 0;
			} else if (count == 1) {
				cursor = db.rawQuery("SELECT * FROM " + SKILL_INFO_TABLE_NAME + " WHERE ActivateSkill = " + "'" + query + "'", null);
			} else if (count == 2) {
				cursor = db.rawQuery("SELECT * FROM " + SKILL_INFO_TABLE_NAME + " WHERE Skill = " + "'" + query + "'", null);
			}

			cursor.moveToFirst();


			for (int i = 0; i < cursor.getCount(); i++) {
				data = new SkillInfoData();
				data.setNum(cursor.getInt(0));
				data.setActivateSkill(cursor.getString(1));
				data.setSkill(cursor.getString(2));
				data.setRequired(cursor.getString(3));
				data.setDesc(cursor.getString(4));

				list.add(data);
				cursor.moveToNext();
			}

			cursor.close();
			count++;
		} while (count == 2);
	}

	public void getAllSkillList(ArrayList<SkillInfoData> list) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		cursor = db.rawQuery("SELECT * FROM " + SKILL_INFO_TABLE_NAME, null);

		cursor.moveToFirst();

		SkillInfoData data;
		for (int i = 0; i < cursor.getCount(); i++) {
			data = new SkillInfoData();
			data.setNum(cursor.getInt(0));
			data.setActivateSkill(cursor.getString(1));
			data.setSkill(cursor.getString(2));
			data.setRequired(cursor.getString(3));
			data.setDesc(cursor.getString(4));

			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getAdornmentNameList(ArrayList<String> list) {
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		cursor = db.rawQuery("SELECT * FROM " + ADORNMENT_INFO_TABLE_NAME, null);

		cursor.moveToFirst();

		for (int i = 0; i < cursor.getCount(); i++) {
			list.add(cursor.getString(1));
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getAdornmentList(ArrayList<SkillInfoData> list, String query) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		cursor = db.rawQuery("SELECT * FROM " + ADORNMENT_INFO_TABLE_NAME + " WHERE Name LIKE " + "'" + query + "'", null);

		cursor.moveToFirst();

		SkillInfoData data;
		for (int i = 0; i < cursor.getCount(); i++) {
			data = new SkillInfoData();
			data.setNum(cursor.getInt(0) - 100);
			data.setAdornName(cursor.getString(1));
			data.setRare(cursor.getString(2));
			data.setSlot(cursor.getString(3));
			data.setPositive(cursor.getString(4));
			data.setNegative(cursor.getString(5));
			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getAdornmentSkill(ArrayList<SkillInfoData> list, String query) {
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		cursor = db.rawQuery("SELECT * FROM " + ADORNMENT_INFO_TABLE_NAME + " WHERE Positive LIKE " + "'" + query + "%'", null);

		cursor.moveToFirst();

		SkillInfoData data;
		for (int i = 0; i < cursor.getCount(); i++) {
			data = new SkillInfoData();
			data.setNum(cursor.getInt(0) - 100);
			data.setAdornName(cursor.getString(1));
			data.setRare(cursor.getString(2));
			data.setSlot(cursor.getString(3));
			data.setPositive(cursor.getString(4));
			data.setNegative(cursor.getString(5));
			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getMealSkillNameList(ArrayList<String> list) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		cursor = db.rawQuery("SELECT * FROM " + MEAL_SKILL_INFO_TABLE_NAME, null);

		cursor.moveToFirst();

		for (int i = 0; i < cursor.getCount(); i++) {
			list.add(cursor.getString(1));
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getMealSkillList(ArrayList<MealSkillInfoData> list, String query) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		cursor = db.rawQuery("SELECT * FROM " + MEAL_SKILL_INFO_TABLE_NAME + " WHERE ActivateSkill LIKE " + "'" + query + "'", null);

		cursor.moveToFirst();

		MealSkillInfoData data;
		for (int i = 0; i < cursor.getCount(); i++) {
			data = new MealSkillInfoData();
			data.setActivateSkill(cursor.getString(1));
			data.setDesc(cursor.getString(2));
			data.setStuff1(cursor.getString(3));
			data.setStuff2(cursor.getString(4));
			data.setHow(cursor.getString(5));
			data.setSize(cursor.getString(6));

			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getMealInfoList(ArrayList<MealInfoData> list) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		cursor = db.rawQuery("SELECT * FROM " + MEAL_INFO_TABLE_NAME, null);

		cursor.moveToFirst();

		MealInfoData data;
		for (int i = 0; i < cursor.getCount(); i++) {
			data = new MealInfoData();
			data.setNum(cursor.getInt(0));
			data.setStuff1(cursor.getString(1));
			data.setStuff2(cursor.getString(2));
			data.setStirFrying(cursor.getString(3));
			data.setBoiling(cursor.getString(4));
			data.setSteaming(cursor.getString(5));
			data.setFrying(cursor.getString(6));

			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getGuildQuestNameList(ArrayList<String> list) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		cursor = db.rawQuery("SELECT * FROM " + GUILDQUEST_CONDITION_TABLE_NAME, null);

		cursor.moveToFirst();

		for (int i = 0; i < cursor.getCount(); i++) {
			list.add(cursor.getString(1));

			cursor.moveToNext();
		}

		cursor.close();

		cursor = db.rawQuery("SELECT * FROM " + SEARCH_CONDITION_TABLE_NAME, null);

		cursor.moveToFirst();

		for (int i = 0; i < cursor.getCount(); i++) {
			list.add(cursor.getString(1));

			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getSearchingAndGuildQuestCondition(ArrayList<GuildQuestData> list, String query) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		cursor = db.rawQuery("SELECT * FROM " + SEARCH_CONDITION_TABLE_NAME + " WHERE Name = " + "'" + query + "'", null);

		cursor.moveToFirst();

		GuildQuestData data;
		for (int i = 0; i < cursor.getCount(); i++) {
			data = new GuildQuestData();

			data.setNum(cursor.getInt(0));
			data.setGuildQuestName(cursor.getString(1));
			data.setTarget1(cursor.getString(2));
			data.setTarget2(cursor.getString(3));
			data.setTarget3(cursor.getString(4));
			data.setType("searching");

			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();

		cursor = db.rawQuery("SELECT * FROM " + GUILDQUEST_CONDITION_TABLE_NAME + " WHERE GuildQuest = " + "'" + query + "'", null);
		cursor.moveToFirst();

		for (int i = 0; i < cursor.getCount(); i++) {
			data = new GuildQuestData();
			data.setNum(cursor.getInt(0));
			data.setGuildQuestName(cursor.getString(1));
			data.setTarget1(cursor.getString(2));
			data.setTarget2(cursor.getString(3));
			data.setTarget3(cursor.getString(4));
			data.setTarget4(cursor.getString(5));
			data.setTarget5(cursor.getString(6));
			data.setTarget6(cursor.getString(7));
			data.setTarget7(cursor.getString(8));
			data.setTarget8(cursor.getString(9));
			data.setType("guildquest");

			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();
	}

	public void getSearchingAndGuidQuestCondition(ArrayList<GuildQuestData> list) {
		list.clear();
		SQLiteDatabase db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

		cursor = db.rawQuery("SELECT * FROM " + SEARCH_CONDITION_TABLE_NAME, null);

		cursor.moveToFirst();

		GuildQuestData data;

		for (int i = 0; i < cursor.getCount(); i++) {
			data = new GuildQuestData();

			data.setNum(cursor.getInt(0));
			data.setGuildQuestName(cursor.getString(1));
			data.setTarget1(cursor.getString(2));
			data.setTarget2(cursor.getString(3));
			data.setTarget3(cursor.getString(4));
			data.setType("searching");

			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();

		cursor = db.rawQuery("SELECT * FROM " + GUILDQUEST_CONDITION_TABLE_NAME, null);
		cursor.moveToFirst();

		for (int i = 0; i < cursor.getCount(); i++) {
			data = new GuildQuestData();
			data.setNum(cursor.getInt(0));
			data.setGuildQuestName(cursor.getString(1));
			data.setTarget1(cursor.getString(2));
			data.setTarget2(cursor.getString(3));
			data.setTarget3(cursor.getString(4));
			data.setTarget4(cursor.getString(5));
			data.setTarget5(cursor.getString(6));
			data.setTarget6(cursor.getString(7));
			data.setTarget7(cursor.getString(8));
			data.setTarget8(cursor.getString(9));
			data.setType("guildquest");

			list.add(data);
			cursor.moveToNext();
		}

		cursor.close();
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
		}
	}
}
