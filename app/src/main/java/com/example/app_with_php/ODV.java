package com.example.app_with_php;

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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ODV extends AppCompatActivity {
    RequestQueue queue;
    String num;
    ListView list;
    String key_id="";
    int total=0;
    List<HashMap<String, String>> aList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.odview);


        //SharedPreferences
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        String oId = getIntent().getStringExtra("order_ID");
        //HttpsURLConnection
        SSL s=new SSL();
        s.handleSSLHandshake();

        queue = Volley.newRequestQueue(this);
        list = findViewById(R.id.list);
        //call DataBase.php
        String url = "https://"+s.ip()+"/dashboard/test.php/?id="+oId;
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                aList = new ArrayList<HashMap<String, String>>();
                num = response.toString();
                String news = num.substring(num.lastIndexOf("Count") + 5);
                int size = Integer.parseInt(news);
                for (int g = 0; g < size; g++) {
                    String re[] = num.split(",");
                    String id[] = re[g].split("ID");
                    Log.e("dd","dssqq"+re[g]);
                    String Qun[] = id[1].split("Qun");
                    Log.e("dd","dssqq"+id[1]);
                    String PostName[] = Qun[1].split("PostName");
                    Log.e("dd","dssqq"+Qun[1]);
                    String PostPrice[] = PostName[1].split("PostPrice");
                    Log.e("dd","dssqq"+PostName[1]);
                    String createdAt = PostPrice[1].toString();
                    Log.e("dd","dss"+PostName[1]+"  "+PostPrice[1]);
                     total= Integer.parseInt(PostPrice[0])*Integer.parseInt(Qun[0]);
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("listview_id","ID: "+ id[0]);
                    hm.put("listview_Qun","Quantity: "+ Qun[0]);

                    hm.put("listview_PostName", "Product: " +PostName[0]);
                    hm.put("listview_price", "Total Price: " +total+"$");
                    hm.put("listview_createdAt","Date of Adding: "+createdAt);
                    aList.add(hm);

                }

                String[] from = {"listview_id", "listview_Qun", "listview_PostName","listview_price","listview_createdAt"};
                int[] to = {R.id.listview_id, R.id.listview_Qun, R.id.listview_PostName, R.id.listview_price,R.id.listview_createdAt};
                SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.order_d_view, from, to);
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

        TextView setuser=findViewById(R.id.username);
        setuser.setText(user);
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
