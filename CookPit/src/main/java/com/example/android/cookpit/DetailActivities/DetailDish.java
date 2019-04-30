package com.example.android.cookpit.DetailActivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.cookpit.Controller.Adapter.DishViewPagerAdapter;
import com.example.android.cookpit.R;
import com.example.android.cookpit.Fragments.TabFragments.Tab_menu;

import java.util.ArrayList;

public class DetailDish extends AppCompatActivity {


    public ViewPager viewPager;
    public DishViewPagerAdapter pagerAdapter;
    public ArrayList<Uri> dishListUri;
    public int position = -1;
    public ActionBar mActionbar;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        pagerAdapter.getItem(position).onCreate(null);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_detail_dish);

        mActionbar = getSupportActionBar();
        mActionbar.setTitle(getIntent().getStringExtra("category"));

        dishListUri = getIntent().getParcelableArrayListExtra(Tab_menu.DETAIL_URI);
        if (dishListUri != null) {
            position = getIntent().getIntExtra(Tab_menu.POSITION, 0);

            viewPager = findViewById(R.id.detail_dish);

            pagerAdapter = new DishViewPagerAdapter(getSupportFragmentManager(), dishListUri);


            viewPager.setAdapter(pagerAdapter);
            pagerAdapter.notifyDataSetChanged();
            viewPager.setCurrentItem(position);

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("URIList", dishListUri);
        outState.putInt(Tab_menu.POSITION, position);
        Log.v("instance saved", position + " " + dishListUri.get(position).toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt(Tab_menu.POSITION);
        dishListUri = savedInstanceState.getParcelableArrayList("URIList");
        Log.v("instance restored", position + " " + dishListUri.get(position).toString());

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}






