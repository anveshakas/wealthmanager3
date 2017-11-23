package com.anveshak.wealthmanagerapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.numetriclabz.numandroidcharts.ChartData;
import com.numetriclabz.numandroidcharts.DonutChart;

import java.util.ArrayList;
import java.util.List;

public class WealthManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHelper helper = new DBHelper(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_wealth_manager);
        DonutChart donut = (DonutChart) findViewById(R.id.piegraph);

        ChartData values = new ChartData();
        values.setSectorValue(4);
        values.setSectorLabel("INVESTMENTS");
        donut.addSector(values);

        values = new ChartData();
        values.setSectorValue(3);
        values.setSectorLabel("EXPENSES");
        donut.addSector(values);

        values = new ChartData();
        values.setSectorValue(8);
        values.setSectorLabel("EMI");
        donut.addSector(values);

        values = new ChartData();
        values.setSectorValue(5);
        values.setSectorLabel("SAVINGS");
        donut.addSector(values);

        values = new ChartData();
        values.setSectorValue(5);
        values.setSectorLabel("SHARES");
        donut.addSector(values);

        ListView lv = (ListView) findViewById(R.id.listTaskView);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> your_array_list = new ArrayList<String>();
        your_array_list.add("foo");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");
        your_array_list.add("bar");


        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_detail_layout, R.id.taskPercentage,
                your_array_list );

        lv.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wealth_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
