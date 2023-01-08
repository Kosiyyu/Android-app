package com.example.interestingplaces;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private static final String[] dummy = {"apple", "banana", "orange", "grape", "strawberry"};
    private TextView noResultsFoundTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noResultsFoundTextView = findViewById(R.id.no_results_found_text_view);

        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dummy);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fruit = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("fruit", fruit);
                startActivity(intent);
            }
        });

        listView.setVisibility(View.GONE); // hide the ListView

        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Show the TextView if no results were found
        if (adapter.getCount() == 0) {
            noResultsFoundTextView.setVisibility(View.VISIBLE);
        } else {
            noResultsFoundTextView.setVisibility(View.GONE);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            listView.setVisibility(View.GONE);
            noResultsFoundTextView.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.VISIBLE);
            adapter.getFilter().filter(newText);
            listView.setAdapter(adapter);
        }
        return false;
    }


}
