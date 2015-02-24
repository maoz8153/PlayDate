package com.playdate.login;

import com.playdate.R;
import com.playdate.db.UsersDataSource;
import com.playdate.model.DataHolder;
import com.playdate.utils.SessionManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainLogin extends Activity {
	Button btnLogin;
	Button Btnregister;
	Button passreset;
	EditText inputEmail;
	EditText inputPassword;
	UsersDataSource usersdatasource;
	Boolean auth;
	private TextView loginErrorMsg;
	DataHolder userdata;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_login);
		inputEmail = (EditText) findViewById(R.id.email);
		inputPassword = (EditText) findViewById(R.id.pword);
		Btnregister = (Button) findViewById(R.id.registerbtn);
		btnLogin = (Button) findViewById(R.id.login);
		passreset = (Button) findViewById(R.id.passres);
		loginErrorMsg = (TextView) findViewById(R.id.loginErrorMsg);
		usersdatasource = new UsersDataSource(this);
		usersdatasource.open();
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		passreset.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						PasswordReset.class);
				startActivityForResult(myIntent, 0);
				finish();
			}
		});
		Btnregister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						Registration.class);
				startActivityForResult(myIntent, 0);
				finish();
			}
		});
		/**
		 * Login button click event A Toast is set to alert when the Email and
		 * Password field is empty
		 **/
		btnLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if ((!inputEmail.getText().toString().equals(""))
						&& (!inputPassword.getText().toString().equals(""))) {
					auth = usersdatasource.Auth(
							inputEmail.getText().toString(), inputPassword
									.getText().toString());
					if (auth) {
						Long userid = null;
						userid = usersdatasource.getUserId(inputEmail.getText().toString());
						userdata.setData(userid);
						alertDialog.setMessage("success");
						alertDialog.show();
					} else {
						alertDialog.setMessage("failed to auth");
						alertDialog.show();
					}
				} else if ((!inputEmail.getText().toString().equals(""))) {
					Toast.makeText(getApplicationContext(),
							"Password field empty", Toast.LENGTH_SHORT).show();
				} else if ((!inputPassword.getText().toString().equals(""))) {
					Toast.makeText(getApplicationContext(),
							"Email field empty", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Email and Password field are empty",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

}