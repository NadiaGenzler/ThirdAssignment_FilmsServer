package com.example.thirdassignment_filmlist;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FilmInformation extends AppCompatActivity {
    private EditText nameET;
    private EditText ratingET;
    private EditText actorsET;
    private EditText imageET;
    private ImageView image;
    private Film transfferedFilm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_information);

        transfferedFilm=(Film)getIntent().getSerializableExtra(String.valueOf(R.string.Key_For_Transferring_Film));

        nameET=findViewById(R.id.nameET);
        nameET.setText(transfferedFilm.getFilmName());
        ratingET=findViewById(R.id.rateET);
        ratingET.setText(transfferedFilm.getRating());
        actorsET=findViewById(R.id.actorsET);
        actorsET.setText(transfferedFilm.getActors());
        imageET=findViewById(R.id.imageET);
        imageET.setText(transfferedFilm.getImage());

        image=findViewById(R.id.filmPicToUpdate);
        Picasso.with(getBaseContext()).load(transfferedFilm.getImage()).into(image);

        //update films parameters
        findViewById(R.id.updateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://third-assignment-films.herokuapp.com/film_list/"+transfferedFilm.getFilmName()+"";
                final OkHttpClient client = new OkHttpClient();

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String contentToUpdate="{\"filmName\":\""+nameET.getText().toString()+"\","+
                        "\"rating\":\""+ratingET.getText().toString()+"\"," +
                        "\"actors\":\""+actorsET.getText().toString()+"\", " +
                        "\"image\":\""+imageET.getText().toString()+"\"}";
                RequestBody body = RequestBody.create(JSON, contentToUpdate);
                Request request = new Request.Builder().url(url).put(body).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //  Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String serverRespons = response.body().string();
                        Log.e("+++++++++++++",serverRespons);

                        Intent intent=new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                        }
                });
            }
        });

    }

}
