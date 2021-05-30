package com.appsnipp.loginsamples;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeDActivity extends AppCompatActivity {
    private RecyclerView txtlist;
   TextView txtnama;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;

    Context mContext;
    SharedPrefManager sharedPrefManager;
    @BindView(R.id.txtnama)
    TextView getTxtnama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layouthomed);

        txtnama = (TextView) findViewById(R.id.txtnama);
        Button tambah = (Button) findViewById(R.id.btntambah);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        String nama;


        if(extras != null){
            nama = extras.getString("nama");
            txtnama.setText(nama);
        }

        mContext = this;



        String url = "https://ahpj.000webhostapp.com/getkerjaan.php";


        txtlist = (RecyclerView) findViewById(R.id.txtlist);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        txtlist.setLayoutManager(llm);

        requestQueue = Volley.newRequestQueue(HomeDActivity.this);

        list_data = new ArrayList<HashMap<String, String>>();
        stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("aktivitas");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("kerjaan", json.getString("kerjaan"));

                        //map.put("id_kuisioner2", json.getString("id_kuisioner2"));
                        ///map.put("kuisioner2", json.getString("kuisioner2"));

                        list_data.add(map);
                        AdapterList2 adapter = new AdapterList2(HomeDActivity.this, list_data);
                        txtlist.setAdapter(adapter);
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeDActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama = getTxtnama.getText().toString();
                Intent intent = new Intent(HomeDActivity.this, AktivitasDActivity.class);
                intent.putExtra("nama", "{"+nama+"}");

                startActivity(intent);
            }

        });

    }
    @OnClick(R.id.btnlogout) void out(){
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
        startActivity(new Intent(HomeDActivity.this,LandingActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }




}

