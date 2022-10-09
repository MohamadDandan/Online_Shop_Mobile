package com.example.app_with_php;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public void onCreate(
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.makeorder, container, false);
        SharedPreferences sp = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        EditText qun=v.findViewById(R.id.Qun);

        Button Add=v.findViewById(R.id.Add);
        Button confirm=v.findViewById(R.id.confirm_Order);

        SSL s = new SSL();
        s.handleSSLHandshake();
        String id_product=sp.getString("key_id","3");
        String Order_id=sp.getString("Order_id","0");

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qun.getText().toString().isEmpty()){
                    qun.setError("Fill the quantity");

                }else{

                    String   url ="https://"+s.ip()+"/dashboard/OrderDetail.php/?qun="+qun.getText().toString()+"&OID="+Order_id+"&PID="+id_product;

                    StringRequest request= new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                   //error
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                        qun.setText("");

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

        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("Order_id", "");
                editor.commit();
                Intent intent = new Intent(getActivity().getApplicationContext(), OrderView.class);
                startActivity(intent);
            }
        });
        return v;
    }
}