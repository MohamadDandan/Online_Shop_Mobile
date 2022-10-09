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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    int  oid=0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
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
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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
        View v=inflater.inflate(R.layout.fragment_first, container, false);
        SharedPreferences sp = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();


        EditText address=v.findViewById(R.id.address);
        Button Add=v.findViewById(R.id.C_Order);
        Button Cancel=v.findViewById(R.id.Cancel);

        SSL s = new SSL();
        s.handleSSLHandshake();

        //get saved user id
        String id_user=sp.getString("key_id_user","0");

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(address.getText().toString().isEmpty()){
                    address.setError("fill the address");
                }else{
                    //call for Order.php=> create session
                    String   url ="https://"+s.ip()+"/dashboard/Order.php/?UID="+id_user+"&address="+address.getText().toString();
                    StringRequest request= new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                        @Override
                        //error
                        public void onResponse(String response) {
                            //take only id number
                            Pattern p = Pattern.compile("\\d+");
                            Matcher m = p.matcher(response);
                            while (m.find()) {
                                oid = Integer.parseInt(m.group());
                            }
                            if (oid > 0) {

                                String ids = String.valueOf(oid);
                                Log.e("F","FF"+ids+"   "+oid);
                                editor.putString("Order_id", ids);
                                editor.commit();
                                Intent intent = new Intent(getActivity().getApplicationContext(), pd.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), "error: incorrect password or username", Toast.LENGTH_LONG).show();
                            }}
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

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("Order_id", "");
                editor.commit();
            }
        });

        return v;
    }
}