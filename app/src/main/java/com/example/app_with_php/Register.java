package com.example.app_with_php;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    RequestQueue queue;
    String DOB,url,Gender;
    int id=0;
    String[] countries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe", "Palestine"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();

        SSL s=new SSL();
        s.handleSSLHandshake();

        Button Reg=findViewById(R.id.button);
        EditText New_UserName=findViewById(R.id.ET_User_reg);
        EditText New_Email=findViewById(R.id.ET_email_reg);
        EditText New_Password=findViewById(R.id.ET_Pass_reg);
        AutoCompleteTextView New_Country=findViewById(R.id.Country);
        EditText New_Month=findViewById(R.id.DOB_month);
        EditText New_Day=findViewById(R.id.DOB_day);
        EditText New_Year=findViewById(R.id.DOB_year);
        RadioButton New_female=findViewById(R.id.Female);
        RadioButton New_male=findViewById(R.id.Male);



        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        queue = Volley.newRequestQueue(this);

        ArrayAdapter ad=new ArrayAdapter(this, android.R.layout.select_dialog_item,countries);

        New_Country.setAdapter(ad);

        New_Country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Register.this, "u choose"+ad.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });


        New_Month.setFilters(new InputFilter[]{
                new InputMinMax("1","12")
        });
        New_Month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(New_Month.getText().toString().equals("2")){
                    New_Day.setFilters(new InputFilter[]{
                            new InputMinMax("1","28")
                    });
                }else if(New_Month.getText().toString().equals("1")||New_Month.getText().toString().equals("3")||New_Month.getText().toString().equals("5")||New_Month.getText().toString().equals("7")||New_Month.getText().toString().equals("8")||New_Month.getText().toString().equals("10")||New_Month.getText().toString().equals("12")){
                    New_Day.setFilters(new InputFilter[]{
                            new InputMinMax("1","31")
                    });
                }else{
                    New_Day.setFilters(new InputFilter[]{
                            new InputMinMax("1","30")
                    });
                }
            }
        });









        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(New_female.isChecked()){
                    Gender=New_female.getText().toString();
                }else{
                    Gender=New_male.getText().toString();
                }
                DOB=New_Year.getText().toString()+"-"+New_Month.getText().toString()+"-"+New_Day.getText().toString()+" 00:00:00";

                String Username=New_UserName.getText().toString();
                String Email=New_Email.getText().toString();
                String Pass=New_Password.getText().toString();
                String country=New_Country.getText().toString();
                if(Username.isEmpty()){
                    New_UserName.setError("Username needed");
                }
                else if(Email.isEmpty()){
                    New_Email.setError("Email needed");
                }
                else if(Pass.isEmpty()){
                    New_Password.setError("Password needed");
                }
                else if(country.isEmpty()){
                    New_Country.setError("Country needed");
                }

                else{
                url = "https://" + s.ip() + "/dashboard/Register.php/?UN="+Username+"&E="+Email+"&P="+Pass+"&G="+Gender+"&C="+country+"&DOB="+DOB;
                StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    //error
                    public void onResponse(String response) {
                        String url2 = "https://" + s.ip() + "/dashboard/login.php/?user=" + Username + "&pass=" + Pass;
                        Log.e("login", "log" + url2.toString());
                        StringRequest request2 = new StringRequest(Request.Method.GET, url2, new com.android.volley.Response.Listener<String>() {
                            @Override
                            //error
                            public void onResponse(String response) {
                                editor.putString("key_name_user", Username);

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
                                    Toast.makeText(Register.this, "error: incorrect password or username", Toast.LENGTH_LONG).show();
                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error", error.toString());
                            }
                        });
                        queue.add(request2);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                    }
                });

                queue.add(request);
                    }
            }
        });



    }
}
