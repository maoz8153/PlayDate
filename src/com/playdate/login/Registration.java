package com.playdate.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.playdate.db.UsersDBOpenHelper;
import com.playdate.db.UsersDataSource;
import com.playdate.login.MainLogin;
import com.playdate.model.DataHolder;
import com.playdate.model.User;
import com.playdate.utils.SessionManager;
import com.playdate.R;

public class Registration extends Activity {
	User user;
	UsersDataSource usersdatasource;
	UsersDBOpenHelper db;
	EditText inputFirstName;
	EditText inputLastName;
	EditText inputUsername;
	EditText inputEmail;
	EditText inputPassword;
	Button btnRegister;
	TextView registerErrorMsg;
	DataHolder dataholder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_screen);
		usersdatasource = new UsersDataSource(this);
		usersdatasource.open();
		/**
		 * Defining all layout items
		 **/
		inputFirstName = (EditText) findViewById(R.id.fname);
		inputLastName = (EditText) findViewById(R.id.lname);
		inputUsername = (EditText) findViewById(R.id.uname);
		inputEmail = (EditText) findViewById(R.id.email);
		inputPassword = (EditText) findViewById(R.id.pword);
		btnRegister = (Button) findViewById(R.id.register);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);

		/**
		 * Button which Switches back to the login screen on clicked
		 **/
		Button login = (Button) findViewById(R.id.bktologin);
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), MainLogin.class);
				startActivityForResult(myIntent, 0);
				finish();
			}
		});
		/**
		 * Register Button click event. A Toast is set to alert when the fields
		 * are empty. Another toast is set to alert Username must be 5
		 * characters.
		 **/
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("static-access")
			@Override
			public void onClick(View view) {

				if ((!inputUsername.getText().toString().equals(""))
						&& (!inputPassword.getText().toString().equals(""))
						&& (!inputFirstName.getText().toString().equals(""))
						&& (!inputLastName.getText().toString().equals(""))
						&& (!inputEmail.getText().toString().equals(""))) {
					if (inputUsername.getText().toString().length() > 4) {
						String Username = inputUsername.getText().toString();
						String Email = inputEmail.getText().toString();
						String Password = inputPassword.getText().toString();
						String Firstname = inputFirstName.getText().toString();
						String Lastname = inputLastName.getText().toString();
						usersdatasource.CreateUser(Username, Email, Password,
								Firstname, Lastname);
						Intent intent = new Intent(getBaseContext(),
								RegistratedDetails.class);
						Long userid;
						userid = usersdatasource.getUserId(inputEmail.getText().toString());
						dataholder.getInstance().setData(userid);
						startActivity(intent);
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"Username should be minimum 5 characters",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"One or more fields are empty", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

}