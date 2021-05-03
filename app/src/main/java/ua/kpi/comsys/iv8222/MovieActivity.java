package ua.kpi.comsys.iv8222;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Movie");

        Movie currMovie = getMovieFullInfo();

        TextView tw = findViewById(R.id.movie_info);
        tw.setText(Html.fromHtml(currMovie.getInfo()));

        ImageView imageView = findViewById(R.id.movie_Poster);
        imageView.setImageResource(currMovie.getPosterID());
    }

    private Movie getMovieFullInfo(){

        int fileID = getIntent().getExtras().getInt("file");
        Scanner scanner = new Scanner(getResources().openRawResource(fileID));
        try {
            String data = scanner.nextLine();
            JSONObject jsonObject = new JSONObject(data);

            String Title = jsonObject.getString("Title");
            String Year = jsonObject.getString("Year");
            String Rated = jsonObject.getString("Rated");
            String Released = jsonObject.getString("Released");
            String Runtime = jsonObject.getString("Runtime");
            String Genre = jsonObject.getString("Genre");
            String Director = jsonObject.getString("Director");
            String Writer = jsonObject.getString("Writer");
            String Actors = jsonObject.getString("Actors");
            String Plot = jsonObject.getString("Plot");
            String Language = jsonObject.getString("Language");
            String Country = jsonObject.getString("Country");
            String Awards = jsonObject.getString("Awards");
            String Poster = jsonObject.getString("Poster").toLowerCase();
            String imdbRating = jsonObject.getString("imdbRating");
            String imdbVotes = jsonObject.getString("imdbVotes");
            String imdbID = jsonObject.getString("imdbID");
            String Type = jsonObject.getString("Type");
            String Production = jsonObject.getString("Production");


            int formatIndex = Poster.lastIndexOf(".");
            if(formatIndex == -1)
                formatIndex = 0;
            String post = Poster.substring(0, formatIndex);

            int PosterID = getResources().getIdentifier(post, "drawable", getPackageName());
            Movie currMovie = new Movie(Title, Year, imdbID, Type, PosterID);
            currMovie.setInfo(Rated, Released, Runtime, Genre, Director, Writer,Actors,Plot,Language,Country,Awards,imdbRating,imdbVotes,Production);
            return currMovie;

        } catch (JSONException e) {
            Toast.makeText(this, "JSON exception!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (NoSuchElementException e){
            Toast.makeText(this, "Exception while scanning file!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
