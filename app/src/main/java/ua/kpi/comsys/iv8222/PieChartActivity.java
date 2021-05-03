package ua.kpi.comsys.iv8222;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> colors = new ArrayList<>();
        colors.add(new PieEntry(10, "Жовтий"));
        colors.add(new PieEntry(20, "Зелений"));
        colors.add(new PieEntry(25, "Синій"));
        colors.add(new PieEntry(5, "Червоний"));
        colors.add(new PieEntry(40, "Блакитний"));


        PieDataSet pieDataSet = new PieDataSet(colors,"");

        final int[] MY_COLORS = {Color.rgb(255,215,0), Color.rgb(0,128,0), Color.rgb(0,0,255),
                Color.rgb(255,0,0), Color.rgb(0,191,255)};
        ArrayList<Integer> colorss = new ArrayList<Integer>();

        for(int c: MY_COLORS) colorss.add(c);
        pieDataSet.setColors(colorss);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.animate();
    }
}