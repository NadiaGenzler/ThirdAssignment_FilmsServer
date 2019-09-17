package com.example.thirdassignment_filmlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

     ArrayList<Film> filmList;
     MyArrayAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getTheList();

        //add a new movie
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),AddFilmActivity.class);
                startActivity(intent);
            }
        });

        onClicks();
    }

    //show the list
   public void getTheList(){
       filmList=new ArrayList<>();
       adapter = new MyArrayAdapter(getBaseContext(), R.layout.my_layout_three_text, filmList);
       lv  = findViewById(R.id.myMainLV);
       lv.setAdapter(adapter);
       lv.setClickable(true);

       String url = "https://third-assignment-films.herokuapp.com/film_list";
       final OkHttpClient client = new OkHttpClient();
       Request request = new Request.Builder().url(url).build();

       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               String serverRespons = response.body().string();
               if (response.isSuccessful()) {

                   GsonBuilder gsonBuilder = new GsonBuilder();
                   Gson gson = gsonBuilder.create();

                   filmList = gson.fromJson(serverRespons, new TypeToken<ArrayList<Film>>(){}.getType());

                   MainActivity.this.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           for (Film film : filmList) {
                               adapter.add(film);
                           }
                           adapter.notifyDataSetChanged();
                       }
                   });
               }
           }
       });
   }

   public void onClicks(){

       //film info with update option
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               final int index=i;
               Intent intent=new Intent(getBaseContext(),FilmInformation.class);
               intent.putExtra(String.valueOf(R.string.Key_For_Transferring_Film),filmList.get(index));
               startActivity(intent);
           }
       });

       //delete from list
       lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
               final int index=i;
               showAlert(index,"Delete '" + filmList.get(index).getFilmName()+ "'",
                       "Are you sure you want to delete "+filmList.get(index).getFilmName() +"?",view);

               return true;
           }
       });
   }

    public void showAlert(final int index, String title, String message,final View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String url = "https://third-assignment-films.herokuapp.com/film_list/"+filmList.get(index).getFilmName()+"";
                final OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).delete().build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String serverRespons = response.body().string();

                        if (response.isSuccessful()) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Snackbar.make(view, serverRespons, Snackbar.LENGTH_LONG).show();
                                    getTheList();
                                }
                            });
                        }
                    }
                });
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


}
