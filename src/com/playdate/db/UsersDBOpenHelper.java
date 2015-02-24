package com.playdate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;

public class UsersDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "DatingGame";

	private static final String DATABASE_NAME = "users.db";
	private static final int DATABASE_VERSION = 1;

	// user base database

	public static final String TABLE_USERS = "users";
	public static final String COLUMN_ID = "userId";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_FIRSTNAME = "firstname";
	public static final String COLUMN_LASTNAME = "lastname";

	// user info database

	public static final String TABLE_USERDETAILS = "UserDetails";
	public static final String COLUMN_USERDETAILSID = "userDetailsId";
	public static final String COLUMN_SEX = "sex";
	public static final String COLUMN_ETHNICITY = "ethnicity";
	public static final String COLUMN_BIRTHDAY = "birthday";
	public static final String COLUMN_HEIGHT = "height";
	public static final String COLUMN_CITY = "city";
	public static final String COLUMN_BODY_TYPE = "bodyType";
	public static final String COLUMN_RELIGION = "religion";
	public static final String COLUMN_EDUCATION = "education";
	public static final String COLUMN_LANGUAGE = "language";
	public static final String COLUMN_USERID = "userId";

	// user images database

	private Context context;
	private final String TAG = "DatabaseHelperClass";
	private static final String TABLE_IMAGE = "UserImageTable";

	// Image Table Columns names
	private static final String COL_ID = "col_id";
	private static final String IMAGE_TEXT = "image_text";
	private static final String IMAGE_PATH = "image_path";
	private static final String USERTABLE_ID = "usertable_id";
	// col foreign key userid



	private static final String TABLE_CREATE_USER_DETAILS = "CREATE TABLE "
			+ TABLE_USERDETAILS + " (" + COLUMN_USERDETAILSID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SEX + " TEXT, "
			+ COLUMN_SEX + " STRING, "
			+ COLUMN_ETHNICITY + " TEXT, "
			+ COLUMN_BIRTHDAY + " TEXT, "
			+ COLUMN_HEIGHT + " TEXT, " 
			+ COLUMN_CITY + " TEXT, "
			+ COLUMN_BODY_TYPE + " TEXT, "
			+ COLUMN_RELIGION + " TEXT, "
			+ COLUMN_EDUCATION + " TEXT, "
			+ COLUMN_LANGUAGE + " TEXT, "
			+ COLUMN_USERID + " INTEGER, " + " FOREIGN KEY (" + COLUMN_USERID
			+ ") REFERENCES " + TABLE_USERS + " (" + COLUMN_ID + "));";

	private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_USERS
			+ " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_USERNAME + " TEXT, " + COLUMN_EMAIL + " EMAIL, "
			+ COLUMN_PASSWORD + " TEXT, " + COLUMN_FIRSTNAME + " TEXT, "
			+ COLUMN_LASTNAME + " TEXT " + ")";
	
	private static final String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE + "(" + COL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ IMAGE_TEXT + " TEXT,"
			+ IMAGE_PATH + " STRING, "
			+ USERTABLE_ID + " integer,"
			+ " FOREIGN KEY (" + USERTABLE_ID + ") REFERENCES "
			+ TABLE_USERS + " (" + COLUMN_ID + "));";

	public UsersDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		db.execSQL(TABLE_CREATE_USER_DETAILS);
		db.execSQL(CREATE_IMAGE_TABLE);
		Log.i(LOGTAG, "Table have been created");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATE_USER_DETAILS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
		onCreate(db);

	}

}
