package edu.upc.marcog.restapi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TrackApiService tracksApiService;
    private EditText titleEditText;
    private EditText singerEditText;
    private ListView tracksListView;
    private List<Track> trackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.titleEditText);
        singerEditText = findViewById(R.id.singerEditText);
        tracksListView = findViewById(R.id.tracksListView);

        Button addTrackButton = findViewById(R.id.addTrackButton);
        Button getTrackButton = findViewById(R.id.getTrackButton);

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") // Cambia a la URL de tu servidor
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tracksApiService = retrofit.create(TrackApiService.class);

        // Configurar el botón para agregar una pista
        addTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTrack();
            }
        });

        // Configurar el botón para obtener todas las pistas
        getTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllTracks();
            }
        });

        tracksListView.setOnItemClickListener((parent, view, position, id) -> {
            Track selectedTrack = trackList.get(position);
            Intent intent = new Intent(MainActivity.this, TrackDetailActivity.class);
            intent.putExtra("id", selectedTrack.getId());
            intent.putExtra("title", selectedTrack.getTitle());
            intent.putExtra("singer", selectedTrack.getSinger());
            startActivity(intent);
        });
    }

    private void addTrack() {
        String title = titleEditText.getText().toString();
        String singer = singerEditText.getText().toString();

        Track track = new Track(null, title, singer);
        Call<Track> call = tracksApiService.addTrack(track);
        call.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Track added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to add track", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Log.e("API Error", "Failed to add track", t);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAllTracks() {
        Call<List<Track>> call = tracksApiService.getAllTracks();
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.isSuccessful()) {
                    trackList = response.body();
                    if (trackList != null && !trackList.isEmpty()) {
                        TrackAdapter adapter = new TrackAdapter(MainActivity.this, trackList);
                        tracksListView.setAdapter(adapter);
                    } else {
                        Toast.makeText(MainActivity.this, "No tracks found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch tracks", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch tracks", t);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
