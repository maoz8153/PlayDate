package com.playdate.login;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.playdate.db.UsersDBOpenHelper;
import com.playdate.db.UsersDataSource;
import com.playdate.login.MainLogin;
import com.playdate.model.User;
import com.playdate.usersetting.UserInfoActivity;
import com.playdate.R;

public class RegistratedDetails extends Activity {

	// private int userid;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrated_user);
		UsersDataSource db = new UsersDataSource(getApplicationContext());
		HashMap user = new HashMap();
		user = db.getUserDetails();

		/**
		 * Displays the registration details in Text view
		 **/
		final TextView fname = (TextView) findViewById(R.id.fname);
		final TextView lname = (TextView) findViewById(R.id.lname);
		final TextView uname = (TextView) findViewById(R.id.uname);
		final TextView email = (TextView) findViewById(R.id.email);

		fname.setText((CharSequence) user.get("firstname"));
		lname.setText((CharSequence) user.get("lastname"));
		uname.setText((CharSequence) user.get("username"));
		email.setText((CharSequence) user.get("email"));
		// userid = (Integer) user.get("id");
		Button login = (Button) findViewById(R.id.login);
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						UserInfoActivity.class);
				// myIntent.putExtra("EXTRA_USER_ID", userid);
				startActivityForResult(myIntent, 0);
				finish();
			}
		});
	}
}