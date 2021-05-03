package ua.kpi.comsys.iv8222;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Search");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        setUpRecyclerView(recyclerView, view);
    }

    private void setUpRecyclerView(RecyclerView recyclerView, View view) {
        Context context = requireActivity().getApplicationContext();
        if(library.isEmpty())
            createLibrary(context);
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
                        String notification = "Movie " + library.get(position).getTitle() + " deleted";
                        Toast.makeText(getContext(), notification, Toast.LENGTH_SHORT).show();
                        library.remove(position);
                        adapter.changeList(library);
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
    private void createLibrary(Context context) {
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.movieslist));
        try {
            String data = scanner.nextLine();
            JSONObject jsonObject = new JSONObject(data);
            JSONArray moviesInJSON = jsonObject.getJSONArray("Search");
            for (int i = 0; i < moviesInJSON.length(); i++) {
                JSONObject c = moviesInJSON.getJSONObject(i);
                String Title = c.getString("Title");
                String Year = c.getString("Year");
                String imdbID = c.getString("imdbID");
                String Type = c.getString("Type");
                String Poster = c.getString("Poster").toLowerCase();


                int formatIndex = Poster.lastIndexOf(".");
                if(formatIndex == -1)
                    formatIndex = 0;
                String post = Poster.substring(0, formatIndex);

                int PosterID = getResources().getIdentifier(post, "drawable", getContext().getPackageName());
                library.add(new Movie(Title, Year, imdbID, Type, PosterID));
            }
        } catch (JSONException e) {
            Toast.makeText(context, "JSON exception!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (NoSuchElementException e){
            Toast.makeText(context, "Exception while scanning file!", Toast.LENGTH_SHORT).show();
        }
    }
    public static void addMovie(Movie movie){
        library.add(movie);
        adapter.changeList(library);

    }
    @Override
    public void onMovieClick(int position) {
        String imdb = library.get(position).getimdbID();
        Resources res  = getContext().getResources();
        int infoId = res.getIdentifier(imdb, "raw", getContext().getPackageName());

        if(infoId != 0) {
            Intent intent = new Intent(requireActivity(), MovieActivity.class);
            intent.putExtra("file", infoId);
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
                ArrayList<Movie> filtered = new ArrayList<>();

                for (Movie item : library) {
                    if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                        filtered.add(item);
                    }
                }
                if (filtered.isEmpty()) {
                    Toast.makeText(getContext(), "No Movie Found.", Toast.LENGTH_SHORT).show();
                }
                adapter.changeList(filtered);
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