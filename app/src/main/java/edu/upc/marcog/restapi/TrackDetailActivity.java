package edu.upc.marcog.restapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackDetailActivity extends AppCompatActivity {

    private TrackApiService tracksApiService;
    private String trackId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);

        TextView trackDetailsTextView = findViewById(R.id.trackDetailsTextView);
        Button updateTrackButton = findViewById(R.id.updateTrackButton);
        Button deleteTrackButton = findViewById(R.id.deleteTrackButton);
        Button backToMainButton = findViewById(R.id.backToMainButton);

        // Obtener los datos de la pista del Intent
        trackId = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("title");
        String singer = getIntent().getStringExtra("singer");

        // Mostrar los detalles de la pista
        trackDetailsTextView.setText("ID: " + trackId + "\nTitle: " + title + "\nSinger: " + singer);

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") // Cambia a la URL de tu servidor
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tracksApiService = retrofit.create(TrackApiService.class);

        // Configurar el botón para actualizar una pista
        updateTrackButton.setOnClickListener(v -> updateTrack(title, singer));

        // Configurar el botón para eliminar una pista
        deleteTrackButton.setOnClickListener(v -> deleteTrack());

        // Configurar el botón para volver a MainActivity
        backToMainButton.setOnClickListener(v -> ToMainActivity());
    }

    private void updateTrack(String title, String singer) {
        // Creación del objeto Track actualizado
        Track updatedTrack = new Track(trackId, title, singer);
        Call<Track> call = tracksApiService.updateTrack(trackId, updatedTrack);

        // Llamada a Retrofit para actualizar el Track
        call.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TrackDetailActivity.this, "Track updated successfully", Toast.LENGTH_SHORT).show();
                    ToMainActivity();
                } else {
                    Toast.makeText(TrackDetailActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Toast.makeText(TrackDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteTrack() {
        Call<Void> call = tracksApiService.deleteTrack(trackId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TrackDetailActivity.this, "Track deleted successfully", Toast.LENGTH_SHORT).show();
                    ToMainActivity();
                } else {
                    Toast.makeText(TrackDetailActivity.this, "Failed to delete track", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TrackDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ToMainActivity() {
        Intent intent = new Intent(TrackDetailActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
