package com.example.thirdassignment_filmlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddFilmActivity extends AppCompatActivity {
    private EditText nameET;
    private EditText ratingET;
    private EditText actorsET;
    private EditText imageET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);
        nameET=findViewById(R.id.nameET);
        ratingET=findViewById(R.id.rateET);
        actorsET=findViewById(R.id.actorsET);
        imageET=findViewById(R.id.imageET);

        //add film that was chosen by the user
        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://third-assignment-films.herokuapp.com/film_list/";
                final OkHttpClient client = new OkHttpClient();

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String contentToUpdate = "{\"filmName\":\"" + nameET.getText().toString() + "\"," +
                        "\"rating\":\"" + ratingET.getText().toString() + "\"," +
                        "\"actors\":\"" + actorsET.getText().toString() + "\", " +
                        "\"image\":\"" + imageET.getText().toString() + "\"}";
                RequestBody body = RequestBody.create(JSON, contentToUpdate);
                Request request = new Request.Builder().url(url).post(body).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //  Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String serverRespons = response.body().string();
                        Log.e("+++++++++++++", serverRespons);

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        //add film that was chosen by me
        findViewById(R.id.addFilmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://third-assignment-films.herokuapp.com/film_list/";
                final OkHttpClient client = new OkHttpClient();

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String contentToUpdate = "{\"filmName\":\"John Wick\"," +
                        "\"rating\":\"80%\"," +
                        "\"actors\":\" Keanu Reeves, Halle Berry, Ian McShane\", " +
                        "\"image\":\"https://m.media-amazon.com/images/M/MV5BMDg2YzI0ODctYjliMy00NTU0LTkxODYtYTNkNjQwMzVmOTcxXkEyXkFqcGdeQXVyNjg2NjQwMDQ@._V1_UX182_CR0,0,182,268_AL_.jpg\"}";
                RequestBody body = RequestBody.create(JSON, contentToUpdate);
                Request request = new Request.Builder().url(url).post(body).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //  Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String serverRespons = response.body().string();
                        Log.e("+++++++++++++", serverRespons);

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }

                });
            }
        });
    }
}
