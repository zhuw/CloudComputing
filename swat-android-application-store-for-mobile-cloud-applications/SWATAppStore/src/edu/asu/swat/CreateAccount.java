package edu.asu.swat;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swarappstore.R;

public class CreateAccount extends Activity{

	private TextView screenDescription;
	private TextView done_bar;
	private TextView cancel_bar;
	private TextView title_bar;
	private EditText rsetUserName;
	private EditText rsetEmail;
	private EditText rsetPassword;
	private EditText rsetConfirmPass;
	private EditText rsetName;
	private Button bDone;
	private HttpRequest httpRequest;
	//private String alertTitle = "MyBeeble";
	private String validationMessage = "";
	private MySQLiteOperator sqliteDo; 	 	
	private MySQLiteOpenHelper helper; 
	@Override
	@TargetApi(14)
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    //Code for normal Register Screen
	 	
		sqliteDo = new MySQLiteOperator();
		setContentView(R.layout.register_screen);
		
		screenDescription = (TextView) findViewById(R.id.rstvHeadline);
		rsetUserName = (EditText) findViewById(R.id.rsetUserName);
		rsetEmail = (EditText) findViewById(R.id.rsetEmail);
		rsetPassword = (EditText) findViewById(R.id.rsetPassword);
		rsetConfirmPass = (EditText) findViewById(R.id.rsetConfirmPass);
		rsetName = (EditText) findViewById(R.id.rsetName);
		bDone = (Button) findViewById(R.id.bDone);
		
		Typeface helvetica = Typeface.createFromAsset(getAssets(), "fonts/helveticaneue.ttf");
		
		screenDescription.setTypeface(helvetica);
		rsetUserName.setTypeface(helvetica);
		rsetEmail.setTypeface(helvetica);
		rsetPassword.setTypeface(helvetica);
		rsetConfirmPass.setTypeface(helvetica);
		rsetName.setTypeface(helvetica);
		
		String s= "Welcome to join S.W.A.T! Just One step left!";
		SpannableString ss1=  new SpannableString(s);
		ss1.setSpan(new RelativeSizeSpan(2f), 0,7, 0); // set size
		screenDescription.setText(ss1); 
		
		/* 
		String htmlString="<u>Agree to Terms &amp; Conditions</u>";
		termco.setText(Html.fromHtml(htmlString));
		
	    //Code for Facebook Register Screen
	 	
		
		setContentView(R.layout.fb_register_screen);
		
		termco = (CheckBox)findViewById(R.id.fbrsCheckBox);
		String htmlString="<u>Agree to Terms &amp; Conditions</u>";
		termco.setText(Html.fromHtml(htmlString));
		*/
		
		
		final LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext().
        		getSystemService(LAYOUT_INFLATER_SERVICE);
        
        final View customActionBarView = inflater.inflate(
        		R.layout.actionbar_custom_view_done_cancel, null);
        customActionBarView.findViewById(R.id.action_bar_done).setEnabled(false);
		//final CheckBox rsCheckBox = (CheckBox)findViewById(R.id.rsCheckBox);

        /////////
        
        
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(
        		ActionBar.DISPLAY_SHOW_CUSTOM, 
        		ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
        				| ActionBar.DISPLAY_SHOW_TITLE);
        
        actionBar.setCustomView(customActionBarView,
        		new ActionBar.LayoutParams(
        				ViewGroup.LayoutParams.MATCH_PARENT,
        				ViewGroup.LayoutParams.MATCH_PARENT));

        done_bar = (TextView)findViewById(R.id.done_bar_text);
        done_bar.setText("");
        done_bar.setTypeface(helvetica);
        
        title_bar = (TextView)findViewById(R.id.title_bar_text);
        title_bar.setText("Registration");
        title_bar.setTypeface(helvetica);
        
        cancel_bar = (TextView) findViewById(R.id.cancel_bar_text);
        cancel_bar.setText("Cancel");
        cancel_bar.setTypeface(helvetica);
        
        bDone.setOnClickListener(
        		new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/**
						 * Zongkun Register
						 */
						//username,email,password,referralId

						
						if(checkValidation(rsetUserName,rsetEmail,rsetPassword,rsetConfirmPass,rsetName)){
							
							final String username = rsetUserName.getText().toString();
							String email = rsetEmail.getText().toString();
							String name = rsetName.getText().toString();
							final String password = rsetPassword.getText().toString();
							//Done
							//finish();
							String uri = "http://10.1.10.248/elgg/services/api/rest/json/?method=user.register&username=";
							uri += username;
							uri += "&password=";
							uri += password;
							uri += "&name=";
							uri += name;
							uri += "&email=";
							uri += email;
			    			httpRequest = new HttpRequest();
			    			httpRequest.doGet(uri);
			    			
			    			try {
			    				if(!StringUtils.isBlank(HttpRequest.resultStr)){
			    						JSONObject jsonResult = new JSONObject(HttpRequest.resultStr);
			    						JSONObject resultObject = jsonResult.getJSONObject("result");
			    						//JSONObject core = resultObject.getJSONObject("core");
			    						//int bookCount = booksJson.length();
			    						Boolean success = resultObject.getBoolean("success");
			    						if(success){
			    							new AlertDialog.Builder(CreateAccount.this).setMessage("Success!")
											.setNeutralButton("Close",new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,int id) {
													Intent i = new Intent();
													i.setClass(getApplicationContext(), MainActivity.class);
													startActivity(i);
												}
											})
											.show();
			    						}else{
			    							String msg = resultObject.getString("message");
			    							Toast.makeText(getApplicationContext(), msg, 5).show();
			    						}
			    				}
			    			} catch (JSONException e) {
			    				// TODO Auto-generated catch block
			    				System.out.println(e.toString());
			    			}
						}else{
							Toast.makeText(getApplicationContext(), validationMessage, 5).show();
						}
						
					}
				});
        
        customActionBarView.findViewById(R.id.action_bar_cancel).setOnClickListener(
        		new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//Done
						finish();
					}
				});
        

        
	}
	
	private Boolean checkValidation(EditText rsetUserName,EditText rsetEmail,EditText rsetPassword,EditText rsetConfirmPass,EditText rsetRefId){
		if(rsetUserName == null||rsetUserName.getText().toString().equals("")||rsetUserName.getText().toString()==""
//		||rsetEmail == null||rsetEmail.getText().toString().equals("")||rsetEmail.getText().toString()==""
//		||rsetPassword == null||rsetPassword.getText().toString().equals("")||rsetPassword.getText().toString()==""
//		||rsetConfirmPass == null||rsetConfirmPass.getText().toString().equals("")||rsetConfirmPass.getText().toString()==""
		){
			validationMessage = "Please input your username.";
			return false;
		}
		if(rsetEmail == null||rsetEmail.getText().toString().equals("")||rsetEmail.getText().toString()==""
		){
			validationMessage = "Please input your email address.";
			return false;
		}
		if(rsetPassword == null||rsetPassword.getText().toString().equals("")||rsetPassword.getText().toString()==""
		){
			validationMessage = "Please input your password.";
			return false;
		}
		if(rsetConfirmPass == null||rsetConfirmPass.getText().toString().equals("")||rsetConfirmPass.getText().toString()==""
		){
			validationMessage = "Password doesn't match.";
			return false;
		}
		if(rsetUserName.getText().toString().length()<6){
			validationMessage = "Username needs to be at least 6 characters.";
			return false;
		}
		if(rsetPassword.getText().toString().length()<8){
			validationMessage = "Password needs to be at least 8 characters.";
			return false;
		}
		if(!rsetPassword.getText().toString().equals(rsetConfirmPass.getText().toString())&&
		   rsetPassword.getText().toString() !=rsetConfirmPass.getText().toString()){
			validationMessage = "Password doesn't match.";
			return false;
		}
		return true;
	}
	
}
