package com.playdate.db;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;

import com.playdate.model.User;
import com.playdate.model.UserImages;
import com.playdate.utils.SessionManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

public class UsersDataSource {

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase db;
	private static final String IMAGE_BITMAP = "image_bitmap";
	private static final String TABLE_IMAGE = "UserImagesTable";
	private static final String IMAGE_TEXT = "image_text";
	private static final String USER_ID = "user_id";
	//
	//public static final String TABLE_NAME = {TABLE_USERS, TABLE_USERDETAILS, TABLE_IMAGE};

	private static final String[] allColumns = { UsersDBOpenHelper.COLUMN_ID,
			UsersDBOpenHelper.COLUMN_USERNAME, UsersDBOpenHelper.COLUMN_EMAIL,
			UsersDBOpenHelper.COLUMN_PASSWORD,
			UsersDBOpenHelper.COLUMN_FIRSTNAME,
			UsersDBOpenHelper.COLUMN_LASTNAME };

	public UsersDataSource(Context context) {
		dbhelper = new UsersDBOpenHelper(context);

	}

	public void open() {
		db = dbhelper.getWritableDatabase();
	}

	public void close() {
		dbhelper.close();
	}

	public void CreateUser(String username, String email, String password,
			String firstname, String lastname) {
		ContentValues values = new ContentValues();
		values.put(UsersDBOpenHelper.COLUMN_USERNAME, username);
		values.put(UsersDBOpenHelper.COLUMN_EMAIL, email);
		values.put(UsersDBOpenHelper.COLUMN_PASSWORD, password);
		values.put(UsersDBOpenHelper.COLUMN_FIRSTNAME, firstname);
		values.put(UsersDBOpenHelper.COLUMN_LASTNAME, lastname);

		db.insert(UsersDBOpenHelper.TABLE_USERS, null, values);

	}

	/**
	 * Getting user data from database
	 * */
	public HashMap <String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + UsersDBOpenHelper.TABLE_USERS;

		db = dbhelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			cursor.moveToLast();
			user.put("username", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("password", cursor.getString(3));
			user.put("firstname", cursor.getString(4));
			user.put("lastname", cursor.getString(5));
		}
		cursor.close();
		dbhelper.close();
		// return user
		return user;
	}

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + UsersDBOpenHelper.TABLE_USERS;
		db = dbhelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		dbhelper.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	public boolean Auth(String email, String password) {
		String authQuery = "SELECT  * FROM " + UsersDBOpenHelper.TABLE_USERS
				+ " WHERE " + UsersDBOpenHelper.COLUMN_EMAIL + " = " + '"'
				+ email + '"' + " AND " + UsersDBOpenHelper.COLUMN_PASSWORD
				+ " = " + '"' + password + '"';
		db = dbhelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(authQuery, null);
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			return true;
		} else
			return false;
	}

	public User getUserByCol(String collunm, String collunmVal) {
		User user = new User();
		String selectQuery = "SELECT  * FROM " + UsersDBOpenHelper.TABLE_USERS;
		String selectCol = " WHERE " + collunm + "=" + collunmVal;
		String queryCol = selectQuery + selectCol;
		db = dbhelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryCol, null);
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.setUserId(cursor.getLong(0));
			user.setUsername(cursor.getString(1));
			user.setPassword(cursor.getString(2));
			user.setEmail(cursor.getString(3));
			user.setFirstname(cursor.getString(4));
			user.setLastname(cursor.getString(5));
		}
		return user;
	}
	
	public Long getUserId(String email){
		Long userid = null;
		String selectQuery = "SELECT  * FROM " + UsersDBOpenHelper.TABLE_USERS;
		String selectCol = " WHERE email = " + "'" + email + "'" ;
		String queryCol = selectQuery + selectCol;
		db = dbhelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryCol, null);
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			userid = cursor.getLong(0);
			return userid;
		} else {
			return userid=(long) 00001;
		}
	}
	
	

}