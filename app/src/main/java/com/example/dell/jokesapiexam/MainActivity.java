package com.example.dell.jokesapiexam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

Button btnsearch;
EditText editsearch;
String searched;
String url="http://api.icndb.com/jokes/random/";


boolean connected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editsearch=findViewById(R.id.edit_search);
        btnsearch=findViewById(R.id.searchbutton);
    }

    public void searchJoke(View view) {
        String s= editsearch.getText().toString();

        if (s.equals("")){
            Toast.makeText(this, "Enter some data", Toast.LENGTH_SHORT).show();
            ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) || (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
            if (connected){
                Toast.makeText(this, "Connected to internet", Toast.LENGTH_SHORT).show();

            }
            else {

                AlertDialog.Builder alertdialogue = new AlertDialog.Builder(this);
                alertdialogue.setTitle("Connection Error!!");
                alertdialogue.setMessage("There is no internet connection" + "\n" + "Plzzzzzzzzzzz check your connection");
                alertdialogue.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertdialogue.show();

            }
        }
        else {
            Toast.makeText(this, "you have entered the number", Toast.LENGTH_SHORT).show();
            if (!(url.equals(url+s))&&Integer.parseInt(s)>=1&&Integer.parseInt(s)<=100){
            Intent intent = new Intent(this, JokesActivity.class);
            intent.putExtra("jokes", s);
            startActivity(intent);
            editsearch.getText().clear();
        }
        else {
                Toast.makeText(this, "Plzz Enter in range of 1-100", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
