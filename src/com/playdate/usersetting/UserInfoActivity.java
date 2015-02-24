package com.playdate.usersetting;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.playdate.content.ImageUploader;
import com.playdate.db.UsersDBOpenHelper;
import com.playdate.db.UsersDataSource;
import com.playdate.login.MainLogin;
import com.playdate.model.DataHolder;
import com.playdate.model.User;
import com.playdate.model.UserInfo;
import com.playdate.utils.JdomXMLparser;
import com.playdate.utils.XmlPullParserHelper;
import com.playdate.R;

public class UserInfoActivity extends Activity {

	User user;
	UserInfo userinfo;
	UsersDataSource usersdatasource;
	DataHolder dataholder;
	UsersDBOpenHelper db;
	EditText inputFirstName;
	EditText inputLastName;
	Spinner spinner_height, spinner_city;
	TextView pDisplayDate;
	Button pPickDate;
	int pYear;
	int pMonth;
	int pDay;
	RadioGroup radioSexGroup;
	RadioButton radioSexButton;
	Button btnDisplay;
	Button doneBtn;

	/**
	 * This integer will uniquely define the dialog to be used for displaying
	 * date picker.
	 */
	static final int DATE_DIALOG_ID = 0;

	private DatePickerDialog.OnDateSetListener pDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			pYear = year;
			pMonth = monthOfYear;
			pDay = dayOfMonth;
			updateDisplay();
			displayToast();
		}
	};

	/** Updates the date in the TextView */
	private void updateDisplay() {
		pDisplayDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(pMonth + 1).append("/").append(pDay).append("/")
				.append(pYear).append(" "));
	}

	/** Displays a notification when the date is updated */
	private void displayToast() {
		Toast.makeText(
				this,
				new StringBuilder().append("Date choosen is ").append(
						pDisplayDate.getText()), Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_fill);
		userinfo = new UserInfo();

		/** Capture our View elements */
		pDisplayDate = (TextView) findViewById(R.id.displayDate);
		pPickDate = (Button) findViewById(R.id.pickDate);

		/** Listener for click event of the button */
		pPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		/** Get the current date */
		final Calendar cal = Calendar.getInstance();
		pYear = cal.get(Calendar.YEAR);
		pMonth = cal.get(Calendar.MONTH);
		pDay = cal.get(Calendar.DAY_OF_MONTH);

		/** Display the current date in the TextView */
		updateDisplay();

		spinner_height = (Spinner) findViewById(R.id.spinnerHeight);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.height_values,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner_height.setAdapter(adapter);
		spinner_height
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						Object item_height = parent.getItemAtPosition(pos);
						String heightValue = spinner_height.getSelectedItem()
								.toString();
						userinfo.setHeigth(heightValue);
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

		spinner_city = (Spinner) findViewById(R.id.spinnerCity);
		JdomXMLparser parser = new JdomXMLparser();
		List<String> cities = parser.parseXML(this);
		ArrayAdapter adapter_city = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, cities);
		spinner_city.setAdapter(adapter_city);
		spinner_city
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						Object item_city = parent.getItemAtPosition(pos);
						String cityValue = spinner_city.getSelectedItem()
								.toString();
						userinfo.setCity(cityValue);
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

		radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		radioSexGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radioMale:
					radioSexButton = (RadioButton) findViewById(checkedId);
					String male_value = (String) radioSexButton.getText();
					userinfo.setSex(male_value);
					break;
				case R.id.radioFemale:
					radioSexButton = (RadioButton) findViewById(checkedId);
					String female_value = (String) radioSexButton.getText();
					userinfo.setSex(female_value);
					break;
				}

			}
		});
		
		Button doneBtn = (Button) findViewById(R.id.buttonDone);
		doneBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				userinfo.setBirthday(pDisplayDate.getText().toString());
				Spinner etn=(Spinner) findViewById(R.id.spinnerEtnicity);
				Spinner bodyType=(Spinner) findViewById(R.id.spinnerBodyType);
				Spinner religion=(Spinner) findViewById(R.id.spinnerRel);
				Spinner lang=(Spinner) findViewById(R.id.spinnerLanguage);
				userinfo.setEthnicity( etn.getSelectedItem().toString());
				userinfo.setBody_type(bodyType.getSelectedItem().toString());
				userinfo.setReligion(religion.getSelectedItem().toString());
				userinfo.setLanguage(lang.getSelectedItem().toString());
				Long useriddata = DataHolder.getInstance().getData();
				userinfo.setUserId(useriddata);
				
				
				
				Intent intent = new Intent(getBaseContext(), ImageUploader.class);
				startActivity(intent);
			}
			
		});
	}

	/** Create a new dialog for date picker */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, pDateSetListener, pYear, pMonth,
					pDay);
		}
		return null;
	}
}
