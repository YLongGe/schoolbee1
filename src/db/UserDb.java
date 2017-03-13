package db;

import java.io.File;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDb {
	//查询用户的用户名和密码
	private static final String SELECT_USERANDPASS =  "SELECT password, username "
													+ "FROM User "
			
													+ "WHERE username = ? AND password = ? ";
	//查询用户帐号
	private static final String SELECT_USERNAME = "SELECT username FROM User WHERE username = ?";
	
	//插入用户帐号和密码
	private static final String INSERT_USERANDPASS = "INSERT INTO User(username, password) VALUES(?, ?)";
	
	private SQLiteDatabase database;

	public UserDb() {

	}

	public UserDb(File file) {
		database = SQLiteDatabase.openOrCreateDatabase(file, null);
	}


	// 查询用户的用户名和密码是否正确
	public boolean info(String username, String userpass) {
		Cursor cursor = database.rawQuery(SELECT_USERANDPASS, new String[] { username, userpass });
		if (cursor.moveToNext()) {
			return true;
		}
		return false;
	}

	// 查询数据库中是否存在 用户名为 username 的用户
	public boolean queryUsername(String username) {
		Cursor cursor = database.rawQuery(SELECT_USERNAME, new String[] { username });
		if (cursor.moveToNext()) {
			return true;
		}
		return false;
	}

	// 插入用户名、密码到数据库中
	public void insert(String username, String password) {
		database.execSQL(INSERT_USERANDPASS, new String[] { username, password });
	}

	// 关闭数据库
	public void closeDatabase() {
		database.close();
	}
}
