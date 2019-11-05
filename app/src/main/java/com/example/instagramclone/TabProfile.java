package com.example.instagramclone;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabProfile extends Fragment {

    private EditText edtProfileName, edtProfileBio, edtProfileProfession, edtProfileHobby, edtProfileSport;
    private Button btnUpdateInfo;

    public TabProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.xedtProfileName);
        edtProfileBio = view.findViewById(R.id.xedtProfileBio)  ;
        edtProfileProfession = view.findViewById(R.id.xedtProfileProfession)  ;
        edtProfileHobby = view.findViewById(R.id.xedtProfileHobby)  ;
        edtProfileSport = view.findViewById(R.id.xedtProfileSport)  ;
        btnUpdateInfo = view.findViewById(R.id.xbtnUpdateInfo)  ;

        final ParseUser parseUser = ParseUser.getCurrentUser();
        {   // grouping of all if statements
            if (parseUser.get("profileName") == null) {
                edtProfileName.setText("");
            } else {
                edtProfileName.setText(parseUser.get("profileName").toString());
            }

            if (parseUser.get("profileBio") == null) {
                edtProfileBio.setText("");
            } else {
                edtProfileBio.setText(parseUser.get("profileBio").toString());
            }
            if (parseUser.get("profileProfession") == null) {
                edtProfileProfession.setText("");
            } else {
                edtProfileProfession.setText(parseUser.get("profileProfession").toString());
            }
            if (parseUser.get("profileHobby") == null) {
                edtProfileHobby.setText("");
            } else {
                edtProfileHobby.setText(parseUser.get("profileHobby").toString());
            }
            if (parseUser.get("profileSport") == null) {
                edtProfileSport.setText("");
            } else {
                edtProfileSport.setText(parseUser.get("profileSport").toString());
            }
        }    // End of grouping of all if statements (parseuser value is null or not)

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseUser.put("profileName",edtProfileName.getText().toString());
                parseUser.put("profileBio",edtProfileBio.getText().toString());
                parseUser.put("profileProfession",edtProfileProfession.getText().toString());
                parseUser.put("profileHobby",edtProfileHobby.getText().toString());
                parseUser.put("profileSport",edtProfileSport.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){

                            FancyToast.makeText(getContext(), parseUser.getUsername() + " info is Updated",
                                    Toast.LENGTH_SHORT, FancyToast.INFO, false).show();

                        } else {
                            FancyToast.makeText(getContext(), "There was an error: " + e.getMessage(),
                                    Toast.LENGTH_LONG, FancyToast.ERROR, false).show();


                        }
                    }
                });

            }
        });


        return view;
    }

}
