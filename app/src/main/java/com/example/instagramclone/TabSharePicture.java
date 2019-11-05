package com.example.instagramclone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabSharePicture extends Fragment implements View.OnClickListener {

    private ImageView imgProfileImage;
    private EditText txtProfileImageDescription;
    private Button btnShareImage;
    Bitmap recdImageBitmap;

    public TabSharePicture() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        imgProfileImage = view.findViewById(R.id.ximgProfileImage);
        txtProfileImageDescription = view.findViewById(R.id.xedtProfileImageDescription);
        btnShareImage = view.findViewById(R.id.xbtnShareImage);

        imgProfileImage.setOnClickListener(TabSharePicture.this);
                // This fragment is implementing the View class so "TabSharePicture.this" else getcontext()
        btnShareImage.setOnClickListener(TabSharePicture.this);

        return view;

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ximgProfileImage:
                if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000);
                } else {
                    getChosenImage();
                }
                break;
            case  R.id.xbtnShareImage:

                if  (recdImageBitmap != null){
                    if (txtProfileImageDescription.getText().toString().equals("")){
                        FancyToast.makeText(getContext(), "Please give an Image description",
                                FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    } else {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        recdImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("pic.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                        parseObject.put("description", txtProfileImageDescription.getText().toString());
                        final ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setMessage("Up-Loading...");
                        dialog.show();

                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null){
                                    FancyToast.makeText(getContext(), "Image uploaded !!!",
                                            FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                                } else {
                                    FancyToast.makeText(getContext(), "UnKnown Error" + e.getMessage(),
                                            FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                                }

                                dialog.dismiss();
                            }
                        });


                    }

                } else  {

                    FancyToast.makeText(getContext(), "Please Select an Image",
                            FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();

                }


                break;


        }   // end of Switch
    } // end of Onclick

    private void getChosenImage() {

        FancyToast.makeText(getContext(), "Access the Images",
                FancyToast.LENGTH_LONG, FancyToast.INFO,false).show();

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getChosenImage();
            }
        }
    }   // end of onRequestPermissionsResult


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000){
            if (resultCode == Activity.RESULT_OK){

                try {
                    Uri selectedImage = data.getData();
                    String [] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    recdImageBitmap = BitmapFactory.decodeFile(picturePath);
                    imgProfileImage.setImageBitmap(recdImageBitmap);

                    FancyToast.makeText(getContext(), selectedImage + "\n :::"
                                    + filePathColumn + "\n :::" + cursor + "\n :::" + columnIndex + "\n :::" + picturePath,
                            FancyToast.LENGTH_LONG, FancyToast.INFO,false).show();


                } catch (Exception e){

                    e.printStackTrace();

                }
            }

        }

    }
}
