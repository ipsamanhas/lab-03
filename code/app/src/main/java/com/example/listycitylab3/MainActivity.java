package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements com.example.listycitylab3.AddCityFragment.AddCityDialogListener {

    private ArrayList<com.example.listycitylab3.City> dataList;
    private ListView cityList;
    private com.example.listycitylab3.CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("ListyCity");

        String[] cities = {"Edmonton", "Vancouver", "Toronto"};
        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new com.example.listycitylab3.City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new com.example.listycitylab3.CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);


        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v ->
                new com.example.listycitylab3.AddCityFragment().show(getSupportFragmentManager(), "Add City"));


        cityList.setOnItemClickListener((parent, view, position, id) -> {
            com.example.listycitylab3.City selected = dataList.get(position);
            com.example.listycitylab3.AddCityFragment.newInstance(selected)
                    .show(getSupportFragmentManager(), "Edit City");
        });
    }

    @Override
    public void addCity(com.example.listycitylab3.City city) {
        dataList.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCityUpdated() {
        cityAdapter.notifyDataSetChanged();
    }
}