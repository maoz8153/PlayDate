package com.playdate.content;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.playdate.R;
import com.playdate.db.UsersDataSource;
import com.playdate.model.DataHolder;
import com.playdate.model.User;
import com.playdate.utils.SessionManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class ImageUploader extends Activity {


		private static final int SELECT_PICTURE = 1;
		private String selectedImagePath;
		DataHolder userdata;

		public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_pick_activity);
		Long userdata = DataHolder.getInstance().getData();
		Button imageButton = ((Button) findViewById(R.id.ImagePicker));

		imageButton.setOnClickListener(new View.OnClickListener() {

		            public void onClick(View arg0) {
		                // in onCreate or any event where your want the user to
		                // select a file
		                Intent intent = new Intent();
		                intent.setType("image/*");
		                intent.setAction(Intent.ACTION_GET_CONTENT);
		                startActivityForResult(Intent.createChooser(intent,
		                        "Select Picture"), SELECT_PICTURE);
		            }
		        });
		}

		public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

		    if (requestCode == SELECT_PICTURE) {
		        Uri selectedImageUri = data.getData();
		        selectedImagePath = getPath(selectedImageUri);

		        

		    }
		}
		}

		@SuppressWarnings("deprecation")
		public String getPath(Uri uri) {

		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
		        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);

		}
}
