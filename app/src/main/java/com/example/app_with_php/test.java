package com.example.app_with_php;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test extends Activity {
    //json string
    RequestQueue queue;
    JSONObject obj;
    List<String> name_list;
    String num;
    ArrayAdapter<String> arrayAdapter1;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        SSL s=new SSL();
        s.handleSSLHandshake();

       // TextView name_comment = findViewById(R.id.comment_name);
        //TextView comment = findViewById(R.id.comment);
        list = findViewById(R.id.list);
        String url2 = "https://"+s.ip()+"/dashboard/GET.php/?PostId=" + 5;
        queue = Volley.newRequestQueue(this);
        arrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1);
        StringRequest request2 = new StringRequest(Request.Method.GET, url2, new com.android.volley.Response.Listener<String>() {


            public void onResponse(String response) {


                num=response.toString();
                String str[] = num.split(",");
                name_list=new ArrayList<String>(Arrays.asList(str));
                arrayAdapter1.addAll(name_list);
                list.setAdapter(arrayAdapter1);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
            }
        });
        queue.add(request2);


    }
    }




