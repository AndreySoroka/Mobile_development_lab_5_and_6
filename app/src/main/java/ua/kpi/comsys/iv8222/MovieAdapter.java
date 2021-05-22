package ua.kpi.comsys.iv8222;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>  {
    private ArrayList<Movie> movies;
    private final OnMovieListener onMovieListener;
    public MovieAdapter(ArrayList<Movie> movieList, OnMovieListener onClick) {
        movies = movieList;
        onMovieListener = onClick;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView Title, Year, imdbID, Type;
        public final ImageView PosterView;
        public final OnMovieListener onMovieListener;

        public ViewHolder(View itemView, OnMovieListener onMovieListener) {
            super(itemView);
            Title = itemView.findViewById(R.id.movie_Title);
            Year = itemView.findViewById(R.id.movie_Year);
            imdbID = itemView.findViewById(R.id.movie_imdbID);
            Type = itemView.findViewById(R.id.movie_Type);
            PosterView = itemView.findViewById(R.id.movie_icon);
            this.onMovieListener = onMovieListener;

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            onMovieListener.onMovieClick(getAdapterPosition());
        }
    }


    public interface OnMovieListener {
        void onMovieClick(int position);
    }
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View library = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(library, onMovieListener);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.Title.setText(movie.getTitle());
        holder.Year.setText(movie.getYear());
        holder.imdbID.setText(movie.getimdbID());
        holder.Type.setText(movie.getType());
        Ion.with(holder.PosterView)
                .load(movie.getPosterSRC());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public void changeList(ArrayList<Movie> filterllist) {
        movies = filterllist;
        notifyDataSetChanged();
    }
    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
