package com.example.app_with_php;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogIn extends AppCompatActivity {
    RequestQueue queue;
    String url;
    int id=0;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SharedPreferences
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();

        //Check user
        if(!sp.getString("key_id_user","z").isEmpty()){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }else {
        setContentView(R.layout.login);

        //HttpsURLConnection
        SSL s=new SSL();
        s.handleSSLHandshake();


        EditText ET_name=findViewById(R.id.ET_User);
        EditText ET_pass=findViewById(R.id.ET_Pass);
        TextView TV_reg=findViewById(R.id.Register);
        Button login=findViewById(R.id.LogIn);

        //use volley
        queue = Volley.newRequestQueue(this);

        // go to register page
        TV_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });




        //use login form to enter the main activity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String name = ET_name.getText().toString();
                    String pass = ET_pass.getText().toString();

                    /// call login.php to fill the data
                    url = "https://" + s.ip() + "/dashboard/login.php/?user=" + name + "&pass=" + pass;
                    StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                        @Override

                        public void onResponse(String response) {
                            editor.putString("key_name_user", name);

                            //take only id number
                            Pattern p = Pattern.compile("\\d+");
                            Matcher m = p.matcher(response);
                            while (m.find()) {
                                id = Integer.parseInt(m.group());
                            }

                            if (id > 0) {
                                String ids = String.valueOf(id);
                                editor.putString("key_id_user", ids);
                                editor.commit();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);

                            } else {
                                Toast.makeText(LogIn.this, "error: incorrect password or username", Toast.LENGTH_LONG).show();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("error", error.toString());
                        }
                    });

                    queue.add(request);
                }
            });


            /* */


        }

    }}
