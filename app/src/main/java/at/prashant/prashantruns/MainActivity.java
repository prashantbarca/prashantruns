package at.prashant.prashantruns;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.soundcloud.android.crop.Crop;
import com.soundcloud.android.crop.CropImageActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    /*
     * Edge cases
     * 1. App pushed to background
     * 2. Auto-rotation
     * 3. Form validation
     */

    public static final int CAMERA_REQUEST_CODE = 0;
    private Uri tempImgUri;              // Store the URI of the cropped image
    private ImageView imageView;         // Store the imageView displaying the cropped image
    SharedPreferences sharedPreferences; // Store the values on saving
    /*
     * Set of keys for SharedPreferences
     */
    String NAME      = "NameKey";
    String GENDER    = "GenderKey";
    String MAJOR     = "MajorKey";
    String PHONE     = "PhoneKey";
    String CLASS     = "ClassKey";
    String EMAIL     = "EmailKey";
    String IMAGE_URI = "ImageKey";
    /*
     * End of keys
     */
    String URIKEY = "URIKey"; // For the saveInstanceState.
    /*
     * Method saves all entered input to shared preferences.
     * Called from onSave()
     */

    /*
     * Following methods are to validate input
     */

    /*
     * Credit : http://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address#7882950
     */
    private boolean validateEmail(CharSequence target)
    {
        if (TextUtils.isEmpty(target))
            return false;
        else
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean validateClass(String year)
    {
        if(year.length() != 4)
            return false;
        return true;
    }

    /*
     * Credit : http://stackoverflow.com/questions/6358380/phone-number-validation-android#6359128
     */
    private boolean validateCellPhone(String number)
    {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    /*
     * Helper to save profile to sharedPreferences when save is clicked.
     */
    private void saveProfile()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME, ((EditText) findViewById(R.id.editText1)).getText().toString());
        editor.putString(EMAIL, ((EditText) findViewById(R.id.editText2)).getText().toString());
        editor.putString(PHONE, ((EditText) findViewById(R.id.editText3)).getText().toString());
        editor.putString(CLASS, ((EditText) findViewById(R.id.editText4)).getText().toString());
        editor.putString(MAJOR, ((EditText) findViewById(R.id.editText6)).getText().toString());
        editor.putInt(GENDER, ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId());
        if(tempImgUri != null)
            editor.putString(IMAGE_URI, tempImgUri.toString());
        editor.commit();
    }

    /*
     * Method loads values from shared preferences.
     * Called from onCreate()
     */
    private void loadProfile()
    {
        if(sharedPreferences!=null)
        {
            String uriString = sharedPreferences.getString(IMAGE_URI, null);
            if(uriString!=null)
            {
                tempImgUri = Uri.parse(uriString);
                imageView.setImageURI(tempImgUri);
            }
            ((RadioGroup) findViewById(R.id.radioGroup)).check(sharedPreferences.getInt(GENDER, 0));
            ((EditText) findViewById(R.id.editText1)).setText(sharedPreferences.getString(NAME, null));
            ((EditText) findViewById(R.id.editText2)).setText(sharedPreferences.getString(EMAIL, null));
            ((EditText) findViewById(R.id.editText3)).setText(sharedPreferences.getString(PHONE, null));
            ((EditText) findViewById(R.id.editText4)).setText(sharedPreferences.getString(CLASS, null));
            ((EditText) findViewById(R.id.editText6)).setText(sharedPreferences.getString(MAJOR, null));
        }
    }

    /*
     * Called when the save button is clicked.
     */
    public void onSave(View v)
    {
        boolean invalid = false;
        EditText nameEdit = (EditText) findViewById(R.id.editText1);
        EditText emailEdit = (EditText) findViewById(R.id.editText2);
        EditText phoneEdit = (EditText) findViewById(R.id.editText3);
        EditText classEdit = (EditText) findViewById(R.id.editText4);
        EditText majorEdit = (EditText) findViewById(R.id.editText6);
        RadioGroup gender = (RadioGroup) findViewById(R.id.radioGroup);

        // Validation of forms starts
        if(TextUtils.isEmpty(nameEdit.getText().toString()))
        {
            nameEdit.setError("Cannot be empty");
            invalid = true;
        }
        if(gender.getCheckedRadioButtonId() == 0)
        {
            TextView textView = (TextView) findViewById(R.id.textView5);
            textView.setTextColor(Color.RED);
            textView.setText("Gender -- Please select");
            invalid = true;
        }
        if(validateEmail(emailEdit.getText().toString()) == false)
        {
            emailEdit.setError("Email is not valid");
            invalid = true;
        }
        if(validateCellPhone(phoneEdit.getText().toString()) == false)
        {
            phoneEdit.setError("Cell phone number not valid");
            invalid = true;
        }
        if(TextUtils.isEmpty(majorEdit.getText().toString()))
        {
            majorEdit.setError("Major cannot be empty");
            invalid = true;
        }
        if(validateClass(classEdit.getText().toString()) == false)
        {
            classEdit.setError("Class not valid");
            invalid = true;
        }
        // End of validation
        if(invalid == false)
        {
            saveProfile();
            Toast.makeText(MainActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /*
     * Called when the Cancel button is clicked.
     */
    public void onCancel(View v)
    {
        finish();
    }

    /*
     * Permission checking method taken from sample code (Author -- XD)
     */
    private void checkPermissions()
    {
        if (Build.VERSION.SDK_INT < 23)
            return;

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

    /*
     * Called when someone wants to take a picture.
     */
    public void onPictureClick(View v)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues(1);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        tempImgUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempImgUri);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_CANCELED)
        {
            return;
        }
        if (requestCode == CAMERA_REQUEST_CODE)
        {
            try {

                /*
                * This logic was converting the data into bitmap and then cropping it.
                * It worked, but not ideal. Since resolution was at stake.
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                File f = new File(tempImgUri.getPath());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 1000, bos);
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                Log.d("URI", tempImgUri.toString());
                */


                Crop.of(tempImgUri, tempImgUri).asSquare().start(this);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (requestCode == Crop.REQUEST_CROP)
        {
            tempImgUri = Crop.getOutput(data);
            imageView.setImageURI(tempImgUri);
        }
    }

    /*
     * Called when the state changes (rotated, pushed to background, etc.)
     */
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelable(URIKEY, tempImgUri); // Since the second argument is a URI, can't use any other method.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        imageView = (ImageView) findViewById(R.id.imageView);
        sharedPreferences = getSharedPreferences("PrashantRuns", Context.MODE_PRIVATE);
        loadProfile();
        if(savedInstanceState !=null)
        {
            tempImgUri = savedInstanceState.getParcelable(URIKEY); // if the screen was rotated, get it from the bundle.
            imageView.setImageURI(tempImgUri);
        }
    }
}