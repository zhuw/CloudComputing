package edu.asu.swat;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MySQLiteOperator {
	
	//private MySQLiteOpenHelper helper;
	public void clearAccessToken(MySQLiteOpenHelper helper){
		SQLiteDatabase sqlitedatabase = helper.getWritableDatabase();  
		try {
			sqlitedatabase.execSQL("drop table accessT;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null != sqlitedatabase)
			sqlitedatabase.close();
	}
	
	
	public void loginSaveToken(String username, String accessToken,String email,String avatar, String firstname, String lastname, MySQLiteOpenHelper helper) {
		// TODO Auto-generated method stub
		SQLiteDatabase sqlitedatabase = helper.getWritableDatabase();  
		try {
			sqlitedatabase.execSQL("drop table accessT;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sqlitedatabase.execSQL("create table accessT(id INTEGER PRIMARY KEY autoincrement,username text, accesstoken text, email text, avatar text,firstname text, lastname text);");
			sqlitedatabase.execSQL("insert into accessT(username,accessToken,email,avatar,firstname, lastname) values('"+username+"','"+accessToken+"','"+email+"','"+avatar+"','"+firstname+"','"+lastname+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		if(null != sqlitedatabase)
			sqlitedatabase.close();
	}
	
	public UserInfo getInfoInLocalDB(MySQLiteOpenHelper helper){
		String accessToken = "";
		
		SQLiteDatabase sqlitedatabase = helper.getWritableDatabase(); 
		Cursor cur = null;
		try {
			cur=sqlitedatabase.rawQuery("SELECT * from accessT",new String [] {});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		UserInfo userInfo = new UserInfo();
		if(cur!=null){
			if (cur.moveToFirst()) {
	            do {
	                userInfo.setUsername(cur.getString(1));
	                userInfo.setAccessToken(cur.getString(2));
	                userInfo.setEmail(cur.getString(3));
	                try {
						userInfo.setFirstname(cur.getString(4));
						userInfo.setLastname(cur.getString(5));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
	                // Adding contact to list
	            } while (cur.moveToNext());
	        }
		}
		return userInfo;
		//return accessToken;
	}
	
	
	
}
