package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UsersPosts extends AppCompatActivity {

    private LinearLayout  linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        linearLayout = findViewById(R.id.xlinearLayout);


        Intent receivedIntentObject = getIntent();
        final String receivedUserName = receivedIntentObject.getStringExtra("username");

//        FancyToast.makeText(this, receivedUserName, FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

        setTitle(receivedUserName + "'s Posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedUserName);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();





        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (objects.size() >0 && e== null){
                    for (final ParseObject post : objects){

                        final TextView postDesc = new TextView(UsersPosts.this);
                        postDesc.setText(post.get("description")+"");
                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if (data != null && e == null   ){

                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0 , data.length);
                                    ImageView postImageView = new ImageView(UsersPosts.this);
                                    LinearLayout.LayoutParams imageView_params = new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageView_params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageView_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams postDesc_params = new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    postDesc_params.setMargins(5,5,5,5);
                                    postDesc.setLayoutParams(postDesc_params);
                                    postDesc.setGravity(Gravity.CENTER);
                                    postDesc.setBackgroundColor(Color.RED);
                                    postDesc.setTextColor(Color.WHITE);
                                    postDesc.setTextSize(24f);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDesc);

                                }

                            }
                        }); // end of getDataInBackground

                    }   // End of for loop
                }
                /*else  {
                    FancyToast.makeText(UsersPosts.this, receivedUserName+" doesn't have any posts",
                            FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                    finish();
                }*/   // removed this 'else' and added to TabUsers.java so that the Intent is not opened if nil objects

                dialog.dismiss();
            }

        });

    }   // End of onCreate
}
