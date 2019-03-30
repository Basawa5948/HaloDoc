package com.droid.basawa.halodoc;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.MediaStore.Audio.AlbumColumns.ALBUM_ID;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerView_adapter;
    Button button;
    EditText editText;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    private String request_url = "https://hn.algolia.com/api/v1/search?query=";
    public String query_entered;
    private ArrayList<HashMap<String,String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        arrayList = new ArrayList<>();
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclreView);
        button = (Button) findViewById(R.id.buttonClick);
        editText = (EditText) findViewById(R.id.query);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query_entered = editText.getText().toString().toLowerCase().trim();
                arrayList.clear();
                new getData().execute();

            }
        });
    }


    private class getData extends AsyncTask<Void,Void,Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            String final_request = request_url+query_entered;
            RequestResponseHandler requestResponseHandler = new RequestResponseHandler();
            String response = requestResponseHandler.processRequest(final_request);
            if(response!=null){

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("hits");

                    for(int i =0;i<jsonArray.length();i++){

                        JSONObject object = jsonArray.getJSONObject(i);

                        String date = object.getString("created_at");
                        String title = object.getString("title");
                        String url = object.getString("url");
                        String author = object.getString("author");

                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("DATE",date);
                        hashMap.put("TITLE",title);
                        hashMap.put("URL",url);
                        hashMap.put("AUTHOR",author);

                        arrayList.add(hashMap);

                    }
                } catch (JSONException e) {
                    Log.e("JSON_ERROR", "Json parsing error: " + e.getMessage());
                }
            }else {
                Log.e("Else_Part", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            recyclerView_adapter = new Adapter(arrayList,MainActivity.this);
            recyclerView.setAdapter(recyclerView_adapter);
        }
    }
}
