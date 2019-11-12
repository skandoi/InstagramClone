package com.example.instagramclone;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabUsers extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;


    public TabUsers() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        listView = view.findViewById(R.id.xlistView);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, arrayList);

        listView.setOnItemClickListener(TabUsers.this);
        listView.setOnItemLongClickListener(TabUsers.this);

        final TextView txtLoading = view.findViewById(R.id.xtxtLoading);

        final ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();


        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                if (e == null){

                    if (users.size() >0) {
                       for (ParseUser user :users){

                           arrayList.add(user.getUsername());
                           FancyToast.makeText(getContext(),"user size is " + users.size(),
                                   FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();


                       }

                       listView.setAdapter(arrayAdapter);
                       listView.animate().alpha(1).setDuration(2000);
                       txtLoading.animate().alpha(0).setDuration(2000);



                    }

                }

            }
        });


        return view;
    }   // end of onCreate

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view,final int position, long id) {

/*      Intent intent = new Intent(getContext(),UsersPosts.class);
        intent.putExtra("username",arrayList.get(position));
        startActivity(intent);*/

/*      The above code was placed under the if statement below, after adding the parse query
        This will check if the objects are nil or not and will not open the new
        activity i.e. UserPosts, if there are no objects for the particular user.
       */
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",arrayList.get(position));

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() >0 && e== null){

                    Intent intent = new Intent(getContext(),UsersPosts.class);
                    intent.putExtra("username",arrayList.get(position));
                    startActivity(intent);

                } else  {
                    FancyToast.makeText(getContext(),  arrayList.get(position)+" doesn't have any posts",
                            FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                }   // end of if/else statement
            }       // end of done
        });         // end of findInBackGround

    }   // end of onItemClick

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username",arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user!= null && e == null){

                    final PrettyDialog prettyDialog = new PrettyDialog(getContext());

                    prettyDialog.setTitle(user.getUsername() + "'s Info")
                            .setMessage(user.get("profileName")+"\n" + user.get("profileBio")+"\n" +
                                    user.get("profileProfession")+"\n" + user.get("profileHobby")+"\n"+
                                    user.get("profileSport"))
                            .setIcon(R.drawable.person)
                            .addButton("OK",
                                    R.color.pdlg_color_blue,
                                    R.color.pdlg_color_white,
                                    new PrettyDialogCallback() {
                                @Override
                                public void onClick() {
                                    prettyDialog.dismiss();
                                }
                            })      // end of prettyDialogCallBack
                            .show();

/*                    FancyToast.makeText(getContext(), user.get("profileProfession")+"",
                            FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();*/
                }   // end of if statement
            }       // end of done
        });         // end of GetCallBack
        return true;
    }   // end of onItemLongClick

}
