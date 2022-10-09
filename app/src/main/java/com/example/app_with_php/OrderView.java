package com.example.app_with_php;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderView extends AppCompatActivity {
    RequestQueue queue;
    String num;
    ListView list;
    String key_id="";
    List<HashMap<String, String>> aList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);


        //SharedPreferences
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();

        //HttpsURLConnection
        SSL s=new SSL();
        s.handleSSLHandshake();

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
        String user =sp.getString("key_name_user","");
        String id =sp.getString("key_id_user","");

        TextView setuser=findViewById(R.id.username);
        setuser.setText(user);

        queue = Volley.newRequestQueue(this);
        list = findViewById(R.id.list);
        //call DataBase.php
        String url = "https://"+s.ip()+"/dashboard/GetOrder.php/?id="+id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                aList = new ArrayList<HashMap<String, String>>();
                num = response.toString();
                String news = num.substring(num.lastIndexOf("Count") + 5);
                int size = Integer.parseInt(news);
                for (int g = 0; g < size; g++) {
                    String re[] = num.split(",");
                    String id[] = re[g].split("id");
                    String status[] = id[1].split("status");
                    String comment[] = status[1].split("comment");
                    String Address[] = comment[1].split("Address");
                    String createdAt = Address[1].toString();
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("listview_id","ID: "+ id[0]);
                    hm.put("listview_status","Status: "+ status[0]);
                    hm.put("listview_comment", "Note: " +comment[0]);
                    hm.put("listview_Address", "Address: " +Address[0]);
                    hm.put("listview_createdAt","Date of Order: "+createdAt);
                    aList.add(hm);

                }

                String[] from = {"listview_id", "listview_status", "listview_comment","listview_Address", "listview_createdAt"};
                int[] to = {R.id.listview_id, R.id.listview_status, R.id.listview_comment, R.id.listview_Address, R.id.listview_createdAt};
                SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.orderview, from, to);
                ListView androidListView = (ListView) findViewById(R.id.list);
                androidListView.setAdapter(simpleAdapter);
            }







        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
            }
        });
        queue.add(request);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String str=aList.get(i).get("listview_id");;
                String numberOnly= str.replaceAll("[^0-9]", "");

                Log.e("dd","ss"+numberOnly);
                Intent intent = new Intent(getApplicationContext(), ODV.class);
                intent.putExtra("order_ID", numberOnly);
                startActivity(intent);

            }
        });
    }
}