package at.prashant.prashantruns;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.soundcloud.android.crop.Crop;
import com.soundcloud.android.crop.CropImageActivity;

public class MainActivity extends AppCompatActivity {


    public static final int CAMERA_REQUEST_CODE = 0;
    private Uri tempImgUri;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        Log.d("ABCD", Environment.getExternalStorageDirectory().toString());
        Log.d("URL","Permissions");
    }

    /*
     * Permission checking method taken from sample code.
     */
    private void checkPermissions() {
        tempImgUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "tmp.jpg"));
        if (Build.VERSION.SDK_INT < 23)
            return;

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }


    public void onPictureClick(View v) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File f = new File(android.os.Environment.getExternalStorageDirectory(), "tmp.jpg");

        tempImgUri = Uri.fromFile(f);
        tempImgUri = FileProvider.getUriForFile(this,"at.prashant.prashantruns",f);

        Log.d("URI is ", tempImgUri.toString());

        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempImgUri);

        //Log.d("Tag", tempImgUri.toString());
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        Log.d("REACHED RESULT", String.valueOf(resultCode));

        Log.d("REACHED RESULT", String.valueOf(RESULT_CANCELED));
        if(resultCode == RESULT_CANCELED){
            return;
        }

        if (requestCode == CAMERA_REQUEST_CODE) {
            try {
                /*
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
            catch (Exception e) {

            }

        } else if (requestCode == Crop.REQUEST_CROP) {
            Uri selectedImgUri = Crop.getOutput(data);
            imageView.setImageURI(null);
            imageView.setImageURI(selectedImgUri);
        }

    }
}