package edu.upc.marcog.restapi;

import edu.upc.marcog.restapi.Track;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;
public interface TrackApiService {
    String BASE_URL = "http://localhost:8080/dsaApp/";

    @GET("tracks") // Obtener todas las pistas
    Call<List<Track>> getAllTracks();

    @GET("tracks/{id}") // Obtener pista por ID
    Call<Track> getTrackById(@Path("id") String id);

    @POST("tracks") // Añadir nueva pista
    Call<Track> addTrack(@Body Track track);

    @PUT("tracks/{id}") // Actualizar pista
    Call<Track> updateTrack(@Path("id") String id, @Body Track track);
    Call<Track> updateTrack2( @Body Track track);
    @DELETE("tracks/{id}") // Eliminar pista
    Call<Void> deleteTrack(@Path("id") String id);
}
