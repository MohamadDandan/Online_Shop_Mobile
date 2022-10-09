package com.example.app_with_php;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

public class pd extends AppCompatActivity {
    RequestQueue queue;
    ListView list;
    List<String> name_list;
    ImageView img;
    String num;
    String id;
    int  oid=0;
    ArrayAdapter<String> arrayAdapter1;
    JSONObject obj;
    ObjectAnimator animation ;

    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pd);

        //SharedPreferences
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        //HttpsURLConnection
        SSL s = new SSL();
        s.handleSSLHandshake();

        //get saved product id
        id = sp.getString("key_id", "3");

        FrameLayout fz=findViewById(R.id.fl);
        Button close = (Button)findViewById(R.id.button4);

        //hide close button
        close.setVisibility(View.GONE);
        Button addorder = findViewById(R.id.Add_order);


        TextView pname = findViewById(R.id.pname);
        TextView detail = findViewById(R.id.detail);
        TextView price = findViewById(R.id.price);


        img = findViewById(R.id.imageView);


        SharedPreferences.Editor editor = sp.edit();

        if (id.isEmpty()) {
            id = "3";
            editor.putString("key_id", id);
            editor.commit();
        }

        //call for productD.php
        String url = "https://"+s.ip()+"/dashboard/productD.php/?id=" + id;

        queue = Volley.newRequestQueue(this);

        //get saved order id
        String Order_id=sp.getString("Order_id","0");



        //Check if there is open order session
        if(Order_id.isEmpty()){
            addorder.setText("Create Order");
            addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                         //slide up for fragment
                        fz.setVisibility(View.VISIBLE);
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();

                        //hide add order button
                        addorder.setVisibility(View.GONE);

                        fz.setTranslationY(450f);
                        animation = ObjectAnimator.ofFloat(fz,"translationY",0);
                        animation.setDuration(1600);
                        animation.start();
                        ft.replace(R.id.fl, new FirstFragment());
                        ft.commit();
                        animation.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation, boolean isReverse) {
                                close.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
        }else{
            addorder.setText("Add item");
            addorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fz.setVisibility(View.VISIBLE);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    addorder.setVisibility(View.GONE);
                    fz.setTranslationY(450f);
                    animation = ObjectAnimator.ofFloat(fz,"translationY",0);
                    animation.setDuration(1600);
                    animation.start();
                    ft.replace(R.id.fl, new BlankFragment());
                    ft.commit();
                    animation.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation, boolean isReverse) {
                    close.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
        }


        //close/slide down the fragment
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation = ObjectAnimator.ofFloat(fz,"translationY",340f);
                animation.setDuration(1600);
                animation.start();
                close.setVisibility(View.GONE);
                animation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation, boolean isReverse) {
                        fz.setVisibility(View.GONE);
                        addorder.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


        String uri = "@drawable/p" + id;  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        img = (ImageView) findViewById(R.id.imageView);
        Drawable res = getResources().getDrawable(imageResource);
        img.setImageDrawable(res);


        arrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1);
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    obj = new JSONObject(response);
                    pname.setText(obj.getString("PostName"));
                    detail.setText(obj.getString("PostDetail"));
                    price.setText("Price: "+obj.getString("PostPrice")+"$");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
            }
        });

        queue.add(request);



        //get comment data for the product
        list = findViewById(R.id.list);
        String url2 = "https://"+s.ip()+"/dashboard/GET.php/?PostId=" + id;
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