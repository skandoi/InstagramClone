package com.example.instagramclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class SocialMediaActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolBar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        setTitle("Social Media App!");

        toolBar = findViewById(R.id.xmyToolbar);
        setSupportActionBar(toolBar);

        viewPager = findViewById(R.id.xviewPager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        tabLayout = findViewById(R.id.xtabLayout);
        tabLayout.setupWithViewPager(viewPager,true);

    }   // End of onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }   // End of onCreateOptionsMenu


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.xitmPostImage){

            if (Build.VERSION.SDK_INT >= 23 &&
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        3000);

            } else {
                captureImage();
            }

        } else  if (item.getItemId() == R.id.xitmLogout){
            ParseUser.getCurrentUser().logOut();
            finish();
            Intent intent = new Intent(SocialMediaActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }   // End of onOptionsItemSelected

    private void captureImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,4000);

    }   // End of captureImage

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 3000){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                captureImage();
            }
        }
    }   // End of onRequestPermissionsResult

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4000 && resultCode == Activity.RESULT_OK && data != null){

                try {
                    Uri capturedImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),capturedImage);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    ParseFile parseFile = new ParseFile("pic.png",bytes);
                    ParseObject parseObject = new ParseObject("Photo");
                    parseObject.put("picture",parseFile);
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                    final ProgressDialog dialog = new ProgressDialog(this);
                    dialog.setMessage("Up-Loading...");
                    dialog.show();

                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                FancyToast.makeText(SocialMediaActivity.this, "Image uploaded !!!",
                                        FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                            } else {
                                FancyToast.makeText(SocialMediaActivity.this, "UnKnown Error" + e.getMessage(),
                                        FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                            }

                            dialog.dismiss();
                        }
                    });


                } catch (Exception e){

                    e.printStackTrace();

                }   //end of try/catch


        }


    }
}
