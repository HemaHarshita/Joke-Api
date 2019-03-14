package com.example.dell.jokesapiexam;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class JokesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
RecyclerView recyclerView;
ProgressBar progressBar;
ArrayList<JokeModel> arrayList;
public static final int Loader_id=74;
String jokes_url="http://api.icndb.com/jokes/random/";
String string_intent;
boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);
        progressBar=findViewById(R.id.progress);
        recyclerView=findViewById(R.id.recycler);

        string_intent=getIntent().getStringExtra("jokes");
        arrayList=new ArrayList<>();
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) || (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        if (connected){
            Toast.makeText(this, "Connected to internet", Toast.LENGTH_SHORT).show();
            getLoaderManager().initLoader(Loader_id,null,this);
        }
        else {
            AlertDialog.Builder alertdialogue=new AlertDialog.Builder(this);
            alertdialogue.setTitle("Connection Error!!");
            alertdialogue.setMessage("There is no internet connection"+"\n"+"Plzzzzzzzzzzz check your connection");
            alertdialogue.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    finish();
                }
            });
            alertdialogue.show();
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(JokesActivity.this) {
            @Override
            public String loadInBackground() {
                try {
                    URL url=new URL(jokes_url+string_intent);
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    Scanner scanner=new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()){
                        return scanner.next();
                    }
                    else {
                        return null;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressBar.setVisibility(View.VISIBLE);

                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data!=null){
            try {
                JSONObject jsonObject=new JSONObject(data);
                JSONArray jsonArray=jsonObject.getJSONArray("value");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    String jokes=jsonObject1.getString("joke");
                    arrayList.add(new JokeModel(jokes));

                }
                recyclerView.setAdapter(new JokesAdapter(JokesActivity.this,arrayList));
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
        }
        else {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
