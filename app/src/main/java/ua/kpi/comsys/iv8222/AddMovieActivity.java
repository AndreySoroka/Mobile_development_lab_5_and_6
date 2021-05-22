package ua.kpi.comsys.iv8222;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Movie");

        addFunctionality();
    }

    private void addFunctionality(){
        final EditText titleEditText = findViewById(R.id.title_input);
        final EditText typeEditText = findViewById(R.id.type_input);
        final EditText yearEditText = findViewById(R.id.year_input);
        final Button addButton = findViewById(R.id.add);

        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addButton.setEnabled(!s.toString().equals(""));
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = titleEditText.getText().toString();
                String newType = typeEditText.getText().toString();
                String newYear = yearEditText.getText().toString();
                Movie newMovie = new Movie(newTitle, newType,  newYear, true);
                Frag3.addMovie(newMovie);
                finish();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}