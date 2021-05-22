package ua.kpi.comsys.iv8222;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.google.android.material.tabs.TabLayout;

import ua.kpi.comsys.iv8222.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        // tabs.getTabAt(0).setIcon(R.drawable.poster_01);
        //tabs.getTabAt(1).setIcon(R.drawable.poster_02);
        //tabs.getTabAt(2).setIcon(R.drawable.poster_03);
        //tabs.getTabAt(3).setIcon(R.drawable.poster_04);
        tabs.setupWithViewPager(viewPager);
        AndroidNetworking.initialize(getApplicationContext());



    }
}