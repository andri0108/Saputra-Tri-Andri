package com.appsnipp.loginsamples;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AktivitasActivity extends AppCompatActivity {
    public static final String URL = "https://ahpj.000webhostapp.com/";
    private ProgressDialog progress;

    @BindView(R.id.txtkerjaan)
    EditText getTxtkerjaan;

    @BindView(R.id.txtnama)
    TextView getTxtnama;

    Context mContext;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_aktivitas);
        ButterKnife.bind(this);

        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        getTxtnama.setText(sharedPrefManager.getSPNama());

    }
    @OnClick(R.id.btnsimpan) void insert() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
        startActivity(new Intent(AktivitasActivity.this, LandingActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();


        String nama = getTxtnama.getText().toString();
        String kerjaan = getTxtkerjaan.getText().toString();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.insert(nama,kerjaan);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                final String nama = getTxtnama.getText().toString();
                progress.dismiss();
                if (value.equals("1")) {
                    Intent intent = new Intent(AktivitasActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AktivitasActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                final String nama = getTxtnama.getText().toString();
                progress.dismiss();
                Intent intent = new Intent(AktivitasActivity.this, HomeActivity.class);
                intent.putExtra("nama", nama);
                startActivity(intent);
            }
        });
    }
}
