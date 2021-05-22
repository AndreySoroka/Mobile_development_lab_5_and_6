package ua.kpi.comsys.iv8222;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Frag3 extends Fragment implements MovieAdapter.OnMovieListener  {
    private static final ArrayList<Movie> library = new ArrayList<>();
    private static MovieAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.frag_3, container, false);

        return rootView;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Search");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        setUpRecyclerView(recyclerView, view);
    }

    private void setUpRecyclerView(RecyclerView recyclerView, View view) {
        Context context = requireActivity().getApplicationContext();
        adapter = new MovieAdapter(library, this);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecoration);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    Drawable background;
                    Drawable xMark;
                    int xMarkMargin;
                    boolean initiated;

                    private void init() {
                        background = new ColorDrawable(Color.RED);
                        xMark = ContextCompat.getDrawable(context, R.drawable.ic_delete);
                        xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                        initiated = true;
                    }

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Movie deleted = adapter.getMovies().remove(position);
                        library.remove(deleted);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Movie " + deleted.getTitle()  + " deleted", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        View itemView = viewHolder.itemView;

                        if (viewHolder.getAdapterPosition() == -1) {
                            return;
                        }

                        if (!initiated) {
                            init();
                        }

                        background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                        background.draw(c);

                        int itemHeight = itemView.getBottom() - itemView.getTop();
                        int intrinsicWidth = xMark.getIntrinsicWidth();
                        int intrinsicHeight = xMark.getIntrinsicWidth();

                        int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                        int xMarkRight = itemView.getRight() - xMarkMargin;
                        int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                        int xMarkBottom = xMarkTop + intrinsicHeight;
                        xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                        xMark.draw(c);

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }



    private void onSearch(String request) {

        TextView noFoundMsg = (TextView)  requireView().findViewById(R.id.no_movie_msg);
        noFoundMsg.setVisibility(View.GONE);

        ProgressBar progressBar = (ProgressBar)requireView().findViewById(R.id.loading_movies);
        progressBar.setVisibility(View.VISIBLE);

        String API_KEY = "ef531fda";
        String url = String.format("http://www.omdbapi.com/?apikey=%s&s=\"%s\"&page=1", API_KEY, request);

        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray moviesInJSON = response.getJSONArray("Search");

                            for (int i = 0; i < moviesInJSON.length(); i++) {
                                JSONObject c = moviesInJSON.getJSONObject(i);
                                String Title = c.getString("Title");
                                String Year = c.getString("Year");
                                String imdbID = c.getString("imdbID");
                                String Type = c.getString("Type");
                                String PosterURL = c.getString("Poster");


                                library.add(new Movie(Title, Year, imdbID, Type, PosterURL));
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "JSON exception!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.GONE);
                        if(library.isEmpty())
                            noFoundMsg.setVisibility(View.VISIBLE);
                        adapter.changeList(library);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }


                });
    }
    public static void addMovie(Movie movie){
        library.add(movie);
        adapter.changeList(library);

    }
    @Override
    public void onMovieClick(int position) {
        String imdb = adapter.getMovies().get(position).getimdbID();

        if(!imdb.equals("")) {
            Intent intent = new Intent(requireActivity(), MovieActivity.class);
            intent.putExtra("imdb", imdb);
            startActivity(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Movie> found = new ArrayList<>();
                for (Movie item: library) {
                    if(!item.isCreated())
                        found.add(item);
                }
                library.removeAll(found);
                if(library.isEmpty()){
                    TextView noFoundMsg = (TextView)  requireView().findViewById(R.id.no_movie_msg);
                    noFoundMsg.setVisibility(View.VISIBLE);

                    ProgressBar progressBar = (ProgressBar)requireView().findViewById(R.id.loading_movies);
                    progressBar.setVisibility(View.GONE);
                }
                adapter.changeList(library);

                if(newText.length() > 2)
                    onSearch(newText);
                return false;
            }
        });

        MenuItem addItem = menu.findItem(R.id.actionAddMovie);
        addItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(requireActivity(), AddMovieActivity.class);
                startActivity(intent);
                return true;
            }
        });

    }

}