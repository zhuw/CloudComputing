package edu.asu.swat;


import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swarappstore.R;

public class MainActivity extends Activity {

	private TextView register;
	private Button bLogin;
	private EditText userName;
	private EditText password;
	private HttpRequest httpRequest;
	private String avatar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_page);
		
//		WebView webView = (WebView) findViewById(R.id.webView1);
//		String url = "http://10.1.10.248/elgg/";
//		
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.loadUrl(url);
		userName=(EditText)findViewById(R.id.etUName);
		password=(EditText)findViewById(R.id.etPass);
		bLogin = (Button) findViewById(R.id.bLogin);
		register = (TextView) findViewById(R.id.tvCreateAccount);
		register.setClickable(true);
		
        register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent createAccountIntent = new Intent(MainActivity.this,CreateAccount.class);
				startActivity(createAccountIntent);
			}
		});
                
        bLogin.setClickable(true);
        bLogin.setOnClickListener(new OnClickListener(){
        	
        	@Override
        	public void onClick(View v){
        		
        		if(userName == null||userName.getText().toString().equals("")||userName.getText().toString()==""||
        		   password == null||password.getText().toString().equals("")||password.getText().toString()==""){
        			Toast.makeText(getApplicationContext(), "Input both username and password.", 5).show();
        		}else{
	        		String usernameStr = userName.getText().toString();
	        		String passwordStr = password.getText().toString();
	        		String uri = "http://10.1.10.248/elgg/services/api/rest/json/?method=user.get_profile&username="+usernameStr;
	    			httpRequest = new HttpRequest();
	    			httpRequest.doGet(uri);
	    			
	    			try {
	    				if(!StringUtils.isBlank(HttpRequest.resultStr)){
	    					JSONObject jsonResult = new JSONObject(HttpRequest.resultStr);
	    					int status = (Integer)jsonResult.get("status");
	    					if(status == 0){
	    						JSONObject resultObject = jsonResult.getJSONObject("result");
	    						//JSONObject core = resultObject.getJSONObject("core");
	    						JSONObject userInfo = resultObject.getJSONObject("core");
	    						//int bookCount = booksJson.length();
	    						String passReturned = userInfo.getString("password");
	    						avatar = resultObject.getString("avatar url");
	    						
//	    						if(passReturned == passwordStr || passReturned.equals(passwordStr)){
//	    							//Login Succeed
//	    						}else{
//	    							new AlertDialog.Builder(MainActivity.this).setMessage("Username or password is incorrect!")
//									.setNeutralButton("Close",new DialogInterface.OnClickListener() {
//										public void onClick(DialogInterface dialog,int id) {
//											finish();
//										}
//									})
//									.show();
//	    						}
	    					}else{
	    						new AlertDialog.Builder(MainActivity.this).setMessage("Username does not exist!")
								.setNeutralButton("Close",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										finish();
									}
								})
								.show();
	    					}
	    				}
	    			} catch (JSONException e) {
	    				// TODO Auto-generated catch block
	    				System.out.println(e.toString());
	    			}
        		}
        	}
        });
	}

}
