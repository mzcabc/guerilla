package com.mindy.guerilla.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TjuSeatSQLiteOpenHelper extends SQLiteOpenHelper {

	public TjuSeatSQLiteOpenHelper(Context context) {
		super(context, "TjuSeat.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table seatInfo (id integer primary key autoincrement, buildingNum varchar(20), weekNum varchar(20), className varchar(20), infos varchar(20))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
