package com.example.app_with_php;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MakeOrder {

    pd p=new pd();
    public void Order (RequestQueue queue, String UID){
        SSL s=new SSL();
        s.handleSSLHandshake();


//String id_er=sp.getString("Order_id","0");
//                                Log.e("dd","Dd  "+id_er);
//        EditText qun=findViewById(R.id.Qun);
//        EditText address=findViewById(R.id.address);
//        Button confirm=findViewById(R.id.confirm);


//                String Qun=qun.getText().toString();
//                String Address=address.getText().toString();
//                id_product=sp.getString("key_id","3");
//                id_user=sp.getString("key_id_user","0");

                String url ="https://"+s.ip()+"/dashboard/Order.php/?UID="+UID;


                StringRequest request= new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    //error
                    public void onResponse(String response) {




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("error",error.toString());
                    }
                });
                queue.add(request);



    }


}
