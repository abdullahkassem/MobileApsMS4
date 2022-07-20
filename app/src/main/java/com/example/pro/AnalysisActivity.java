package com.example.pro;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {

    PieChart piechart;
    LineChart linechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        piechart = findViewById(R.id.pieChart_layout);
        linechart = findViewById(R.id.linechart_layout);



        PersonalSettings p = PersonalSettings.getInstance();

        //pieEnt.add( new PieEntry((float)p.getMoneyRemaining(),"Money Remaining" ));








        BottomNavigationView bNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_analysis);

        bNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                make_invisible();

                switch(id){
                    case R.id.pieChartPageIncome:

                        piechart.setVisibility(View.VISIBLE);

                        ArrayList<PieEntry> pieEnt = new ArrayList<PieEntry>();

                        for(FinancialAccount f: p.getMyAccounts()) {
                            pieEnt.add( new PieEntry((float)f.getIncome(),f.getName()) );
                        }

                        PieDataSet pieDS = new PieDataSet(pieEnt,"Accounts Balance");

                        pieDS.setColors(ColorTemplate.MATERIAL_COLORS);
                        pieDS.setValueTextColor(Color.BLACK);
                        pieDS.setValueTextSize(16f);

                        PieData pdata= new PieData(pieDS);

                        piechart.setData(pdata);
                        piechart.getDescription().setText("Accounts PieChart");
                        piechart.animate();


                        break;

                    case R.id.pieChartPageExpense:

                        piechart.setVisibility(View.VISIBLE);

                        ArrayList<PieEntry> pieEnt_exp = new ArrayList<PieEntry>();

                        for(FinancialAccount f: p.getMyAccounts()) {
                            pieEnt_exp.add( new PieEntry((float)f.getExpense()*-1,f.getName()) );
                        }

                        PieDataSet pieDS_exp = new PieDataSet(pieEnt_exp,"Accounts Balance");

                        pieDS_exp.setColors(ColorTemplate.MATERIAL_COLORS);
                        pieDS_exp.setValueTextColor(Color.BLACK);
                        pieDS_exp.setValueTextSize(16f);

                        PieData pdata_exp= new PieData(pieDS_exp);

                        piechart.setData(pdata_exp);
                        piechart.getDescription().setText("Accounts PieChart");
                        piechart.animate();


                        break;
                    case R.id.pieChartPageCategory:

                        piechart.setVisibility(View.VISIBLE);

                        ArrayList<PieEntry> pieEnt_cat = new ArrayList<PieEntry>();

                        for(String catName: p.getCategories()) {
                            float f = (float) p.getCategoryTotal(catName);
                            if(f<0)
                                f=f*-1;
                            pieEnt_cat.add( new PieEntry( f,catName ) );
                            Log.d(TAG, "Category name: "+catName+" has total of "+p.getCategoryTotal(catName));
                        }

                        PieDataSet pieDS_cat = new PieDataSet(pieEnt_cat,"Accounts Balance");

                        pieDS_cat.setColors(ColorTemplate.MATERIAL_COLORS);
                        pieDS_cat.setValueTextColor(Color.BLACK);
                        pieDS_cat.setValueTextSize(16f);

                        PieData pdata_cat= new PieData(pieDS_cat);

                        piechart.setData(pdata_cat);
                        piechart.getDescription().setText("Accounts PieChart");
                        piechart.animate();


                        break;
                    case R.id.lineChartPage:

                        ArrayList<Entry> lineChartValues = new ArrayList<Entry>();


                        break;
                }


                return true;
            }
        });

    }

    void make_invisible(){
        piechart.setVisibility(View.GONE);
        linechart.setVisibility(View.GONE);
    }

}