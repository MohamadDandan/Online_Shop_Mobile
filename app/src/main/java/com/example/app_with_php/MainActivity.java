package com.example.app_with_php;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    ListView list;
    String num;
    List<HashMap<String, String>> aList;
    ArrayAdapter<String> arrayAdapter1;
    String key_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SharedPreferences
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();

        //HttpsURLConnection
        SSL s=new SSL();
        s.handleSSLHandshake();


        //call DataBase.php
        String url = "https://"+s.ip()+"/dashboard/DataBase.php";

        list = findViewById(R.id.list);
        queue = Volley.newRequestQueue(this);

        String user =sp.getString("key_name_user","");

        TextView setuser=findViewById(R.id.username);
        setuser.setText(user);

        //Database.php request
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 aList = new ArrayList<HashMap<String, String>>();
                num = response.toString();
                String news = num.substring(num.lastIndexOf("Count") + 5);
                int size = Integer.parseInt(news);

                Log.e("ll","kk"+size);
                for(int g=0;g<size;g++) {
                    num = response.toString();
                    String re[] = num.split(",");
                    String id[] = re[g].split(";");
                    String name[] = id[1].split(":");
                    String price = name[1].toString();

                        if(g==0){
                                String uri = "@drawable/p3";  // where myresource (without the extension) is the file
                                int idImage = getResources().getIdentifier(uri, "drawable", getPackageName());
                                String idImageString = String.valueOf(idImage);

                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("listview_title", name[0]);
                                hm.put("listview_discription", "Price: " + price + "$");
                                hm.put("listview_image",  idImageString);
                                hm.put("id",  "3");
                                aList.add(hm);
                        }else{
                                String uri = "@drawable/p" + id[0];  // where myresource (without the extension) is the file
                                int idImage = getResources().getIdentifier(uri, "drawable", getPackageName());
                                String idImageString = String.valueOf(idImage);
                                Log.e("dd","ss"+uri);
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("listview_title", name[0]);
                                hm.put("listview_discription", "Price: " + price + "$");
                                hm.put("listview_image",  idImageString);
                                hm.put("id",  id[0]);
                                aList.add(hm);
                        }

                }

                String[] from = {"listview_image", "listview_title", "listview_discription"};
                int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};
                SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.newac, from, to);
                ListView androidListView = (ListView) findViewById(R.id.list);
                androidListView.setAdapter(simpleAdapter);}




        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
            }
        });
        queue.add(request);


        //on click take id and go to pd.java
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String temp=aList.get(i).get("id");
                temp=temp.toString();
                key_id=temp;

                ///save product id
                editor.putString("key_id",key_id );
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), pd.class);
                startActivity(intent);
            }
        });


        Button logout=findViewById(R.id.logout);

        //delete user id and return to login form
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update="";
                editor.putString("key_id_user",update);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);

            }
        });
        Button view_order=findViewById(R.id.view_order);
        view_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(getApplicationContext(), OrderView.class);
                startActivity(intent);
            }
        });


    }






















}