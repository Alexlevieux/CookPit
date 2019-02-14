package com.example.android.cookpit.Adapter;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.cookpit.DetailActivities.Dish_detail_fragment;
import com.example.android.cookpit.TabFragments.Tab_menu;

import java.util.ArrayList;

/**
 * Created by alexandrelevieux on 31/12/2016.
 */

public class DishViewPagerAdapter extends FragmentStatePagerAdapter {
    public ArrayList<Uri> uriArrayList;
    private Bundle args;

    public DishViewPagerAdapter(FragmentManager fm, ArrayList<Uri> uriList) {
        super(fm);
        uriArrayList = new ArrayList<Uri>(uriList);

    }


    @Override
    public Fragment getItem(int position) {

        args = new Bundle();
        args.putInt(Tab_menu.POSITION, position);
        args.putParcelableArrayList(Tab_menu.DETAIL_URI, uriArrayList);

        return Dish_detail_fragment.newInstance(args, position);
    }

    @Override
    public int getCount() {

        int count = uriArrayList.size();

        return count;
    }


}
