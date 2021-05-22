package ua.kpi.comsys.iv8222;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Movie");

        getMovieFullInfo();
    }

    private void getMovieFullInfo(){
        String imdbID = getIntent().getExtras().getString("imdbID");
        String API_KEY="ef531fda";
        String url = String.format("http://www.omdbapi.com/?apikey=%s&i=%s", API_KEY, imdbID);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.loading_info);
        TextView tv = findViewById(R.id.movie_info);

        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String Title = response.getString("Title");
                            String Year = response.getString("Year");
                            String Rated = response.getString("Rated");
                            String Released = response.getString("Released");
                            String Runtime = response.getString("Runtime");
                            String Genre = response.getString("Genre");
                            String Director = response.getString("Director");
                            String Writer = response.getString("Writer");
                            String Actors = response.getString("Actors");
                            String Plot = response.getString("Plot");
                            String Language = response.getString("Language");
                            String Country = response.getString("Country");
                            String Awards = response.getString("Awards");
                            String Poster = response.getString("Poster").toLowerCase();
                            String imdbRating = response.getString("imdbRating");
                            String imdbVotes = response.getString("imdbVotes");
                            String imdbID = response.getString("imdbID");
                            String Type = response.getString("Type");
                            String Production = response.getString("Production");


                            Movie currMovie = new Movie(Title, Year, imdbID, Type, Poster);
                            currMovie.setInfo(Rated, Released, Runtime, Genre, Director, Writer,Actors,Plot,Language,Country,Awards,imdbRating,imdbVotes,Production);

                            ImageView imageView = findViewById(R.id.movie_Poster);

                            tv.setText(Html.fromHtml(currMovie.getInfo()));
                            Ion.with(imageView).load(currMovie.getPosterSRC());
                            progressBar.setVisibility(View.GONE);
                        }
                        catch (JSONException e) {
                            tv.setText(Html.fromHtml("JSONException!"));
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        tv.setText(Html.fromHtml("Get request error."));
                        progressBar.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}