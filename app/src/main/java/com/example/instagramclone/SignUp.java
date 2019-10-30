package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave;
    private EditText edtName, edtKickSpeed, edtKickPower, edtPunchSpeed, edtPunchPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.xbtnSave);
        edtName = findViewById(R.id.xedtName);
        edtPunchSpeed = findViewById(R.id.xedtPunchSpeed);
        edtPunchPower = findViewById(R.id.xedtPunchPower);
        edtKickSpeed = findViewById(R.id.xedtKickSpeed);
        edtKickPower = findViewById(R.id.xedtKickPower);

        btnSave.setOnClickListener(SignUp.this);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(SignUp.this,
                " clicked",Toast.LENGTH_SHORT).show();
        try {

            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(edtPunchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.put("kickSpeed",  Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("kickPower",  Integer.parseInt(edtKickPower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
//                Toast.makeText(SignUp.this, kickBoxer.get("name") + " is Saved to the Server",Toast.LENGTH_LONG).show();
                        FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " is Saved to the Server",
                                FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {
                        FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " not Saved" + e.getMessage(),
                                FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
//                Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(SignUp.this, e.getMessage().toString(),
                    FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
        }
        }


    /*    public void helloWorldTapped(View view){

//        ParseObject boxer = new ParseObject("Boxer");
//        boxer.put("punch_speed",200);
//        boxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e==null){
//                    Toast.makeText(SignUp.this,"Boxer is Saved",Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        final ParseObject kickBoxer = new ParseObject("KickBoxer");
        kickBoxer.put("name", "John");
        kickBoxer.put("punchSpeed",1000);
        kickBoxer.put("punchPower",2000);
        kickBoxer.put("kickSpeed",3000);
        kickBoxer.put("kickPower",4000);
        kickBoxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Toast.makeText(SignUp.this,
                            kickBoxer.get("name") + " is Saved to the Server",Toast.LENGTH_LONG).show();
                }
            }
        });


    }*/

}
