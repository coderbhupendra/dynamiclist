package com.example.dynamiclist;



import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 7;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// Table Names
	private static final String TABLE_TODO = "todos";
	

	// Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";

	private static final String KEY_TODO = "todo";
	

	

	
	

	// Table Create Statements
	// Todo table create statement
	private static final String CREATE_TABLE_TODO = 
			"CREATE TABLE "+ TABLE_TODO 
			+ "(" 
			+ KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_TODO+ " TEXT,"
			
			+ KEY_CREATED_AT+ " TEXT" 
			+ ")";


	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_TODO);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
		
		// create new tables
		onCreate(db);
	}

	// ------------------------ "todos" table methods ----------------//

	/*
	 * Creating a todo
	 */
	public long createToDo(Todo todo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, todo.id);
		values.put(KEY_TODO, todo.note);
		values.put(KEY_CREATED_AT, todo.created_at);

		// insert row
		long todo_id = db.insert(TABLE_TODO, null, values);


		return todo_id;
	}

	/*
	 * get single todo
	 */
	public Todo getTodo(int todo_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_TODO + " WHERE "
				+ KEY_ID + " = " + todo_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();
		

		Todo td = new Todo();
		td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
		td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

		return td;
	}

	public int  lastid() {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_TODO ;
		Cursor c = db.rawQuery(selectQuery, null);
		c.moveToLast();
		Todo td = new Todo();
		
		int id=c.getInt(c.getColumnIndex(KEY_ID));
		
		return id;
		
	}
	/**
	 * getting all todos
	 * */
	public List<Todo> getAllToDos() {
		List<Todo> todos = new ArrayList<Todo>();
		String selectQuery = "SELECT  * FROM " + TABLE_TODO;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Todo td = new Todo();
				td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

				// adding to todo list
				todos.add(td);
			} while (c.moveToNext());
		}

		return todos;
	}

	

		

	/*
	 * getting todo count
	 */
	public int getToDoCount() {
		String countQuery = "SELECT  * FROM " + TABLE_TODO;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/*
	 * Updating a todo
	 */
	public int updateToDo(Todo todo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TODO, todo.getNote());
		

		// updating row
		return db.update(TABLE_TODO, values, KEY_ID + " = ?",
				new String[] { String.valueOf(todo.getId()) });
	}

	/*
	 * Deleting a todo
	 */
	public void deleteToDo(int todo_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TODO, KEY_ID + " = ?",
				new String[] { String.valueOf(todo_id) });
		
	}


	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	
	
}
