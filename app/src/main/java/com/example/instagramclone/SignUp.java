package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave, btnAllData;
    private TextView txtGetData;
    private EditText edtName, edtKickSpeed, edtKickPower, edtPunchSpeed, edtPunchPower;
    private String allKickBoxers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.xbtnSave);
        btnAllData = findViewById(R.id.xbtnAllData);
        edtName = findViewById(R.id.xedtName);
        edtPunchSpeed = findViewById(R.id.xedtPunchSpeed);
        edtPunchPower = findViewById(R.id.xedtPunchPower);
        edtKickSpeed = findViewById(R.id.xedtKickSpeed);
        edtKickPower = findViewById(R.id.xedtKickPower);
        txtGetData = findViewById(R.id.xtxtGetData);


        btnSave.setOnClickListener(SignUp.this);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");

                parseQuery.getInBackground("9jY9UVxFyt", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if (object !=null&& e == null){

                            txtGetData.setText( object.get("name")+ "\n" +
                                    "Punch Power: " + object.get("punchPower") + "\n" +
                                    "Punch Speed: " + object.get("punchSpeed") + "\n" +
                                    "Kick Power: " + object.get("kickPower") + "\n" +
                                    "Kick Speed: " + object.get("kickSpeed"));

                        }

                    }
                });



            }  // end of onClick
        }); //end of onClickListener -GetTextData

           btnAllData.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   allKickBoxers = "";

                   ParseQuery<ParseObject> allQuery = ParseQuery.getQuery("KickBoxer");

                   allQuery.findInBackground(new FindCallback<ParseObject>() {
                       @Override
                       public void done(List<ParseObject> objects, ParseException e) {

                           if (objects.size() > 0 && e == null) {

                               for (ParseObject kickboxers : objects){

                                   allKickBoxers = allKickBoxers +
                                           kickboxers.get("name") +">"+ kickboxers.get("punchSpeed") +","
                                           +kickboxers.get("punchPower") +"," +kickboxers.get("kickSpeed") +","
                                           +kickboxers.get("kickPower")+ "\n";

                               }

                               FancyToast.makeText(SignUp.this, allKickBoxers,
                                       FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                           } else {
                               FancyToast.makeText(SignUp.this, "Error" + e.getMessage(),
                                       FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                           }

                       }
                   });


               }
           }); //end of onClickListener -btnAllData

    } // end of onCreate

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
        } //end of onClick


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
