package org.esisalama.mobile.project.demoapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface GithubUserService {

    // https://api.github.com/users/{user_id}
    @GET("/users/{user_id}/")
    Call<GithubUser> getUser(@Path("user_id") int id);


    // https://www.7timer.info/bin/astro.php?
    // lon=113.2&
    // lat=23.1&
    // ac=0&
    // unit=metric&
    // output=json&
    // tzshift=0
    @GET("/bin/astro.php")
    Call<Meteo> getHoraire(
          @Query("lon") Double module,
          @Query("lat") Double vacation,
          @Query("ac") int ac,
          @Query("tzshift") int tzshift,
          @Query("unit") String unit,
          @Query("output") String output
    );
}
class Meteo {
    String product;
    int init;
    List<DataSerie> dataseries;
}
class DataSerie {
    int temp2m;
    int transparency;
}
