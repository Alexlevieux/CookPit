package com.example.android.cookpit.Fragments;


import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.android.cookpit.Fragments.TabFragments.Tab_MEP;
import com.example.android.cookpit.Fragments.TabFragments.Tab_dish;
import com.example.android.cookpit.Fragments.TabFragments.Tab_ingredient;
import com.example.android.cookpit.Fragments.TabFragments.Tab_menu;
import com.example.android.cookpit.Fragments.TabFragments.Tab_search;
import com.example.android.cookpit.MainActivity;
import com.example.android.cookpit.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private boolean isSearchResult;
    private String mParam2;
    private Uri mUri;
    public static final String DETAIL_URI = "URI";
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    public static ArrayList arrayList;
    FragmentManager fm;


    public FragmentMain() {
        // Required empty public constructor
    }

    private FragmentTabHost mTabHost;

    public static FragmentMain newInstance(Boolean isSearchResult, Object result) {

        return newInstance(isSearchResult, result, null, 0);
    }

    public static FragmentMain newInstance(Boolean isSearchResult, Object result, ArrayList arrayList, int parcelType) {


        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isSearchResult);
        args.putParcelableArrayList("searchArray", arrayList);
        args.putInt("parcelType", parcelType);

        fragment.setArguments(args);
        return fragment;
    }

    public interface Callback {

        void OnTabchange(String tabName, Boolean isSearchResult);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTabHost = view.findViewById(R.id.tabhost);
        fm = getChildFragmentManager();
        mTabHost.setup(getContext(), fm, R.id.realtabcontent);
        arrayList = getArguments().getParcelableArrayList("searchArray");

        TabHost.TabSpec spec = mTabHost.newTabSpec("Tab One");
        View tabIndicator = LayoutInflater.from(getContext()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicator.findViewById(R.id.title)).setText("MENU");
        ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.menu);

        spec.setIndicator(tabIndicator);


        if (getArguments() != null) {


            mUri = getArguments().getParcelable(Tab_menu.DETAIL_URI);
            Bundle menuargs = getArguments();
            menuargs.putParcelable(Tab_menu.DETAIL_URI, mUri);
            mTabHost.addTab(spec, Tab_menu.class, menuargs);


        } else {

            mTabHost.addTab(spec, Tab_menu.class, savedInstanceState);
        }


        //Tab 2
        spec = mTabHost.newTabSpec("Tab Two");

        tabIndicator = LayoutInflater.from(getContext()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicator.findViewById(R.id.title)).setText("DISH");
        ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.plate);

        spec.setIndicator(tabIndicator);

        mTabHost.addTab(spec, Tab_dish.class, savedInstanceState);


        spec = mTabHost.newTabSpec("Tab three");
        tabIndicator = LayoutInflater.from(getContext()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicator.findViewById(R.id.title)).setText("PREP/RECIPE");
        ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.recipe);

        spec.setIndicator(tabIndicator);

        mTabHost.addTab(spec, Tab_MEP.class, savedInstanceState);
        spec = mTabHost.newTabSpec("Tab four");

        tabIndicator = LayoutInflater.from(getContext()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicator.findViewById(R.id.title)).setText("INGREDIENT");
        ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.ingredient);


        spec.setIndicator(tabIndicator);


        mTabHost.addTab(spec, Tab_ingredient.class, savedInstanceState);

        spec = mTabHost.newTabSpec("Tab five");

        tabIndicator = LayoutInflater.from(getContext()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicator.findViewById(R.id.title)).setText("SEARCH");
        ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.ic_youtube_searched_for_black_24dp);


        spec.setIndicator(tabIndicator);
        if (arrayList != null) {


            Bundle searchArgs = getArguments();

            mTabHost.addTab(spec, Tab_search.class, searchArgs);

            onSearchresult(arrayList);


        } else {

            mTabHost.addTab(spec, Tab_search.class, savedInstanceState);
        }


        mTabHost.setSaveEnabled(true);


        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                    mTabHost.getTabWidget().getChildAt(i)
                            .setBackgroundColor(Color.parseColor("#CFD8DC")); // unselected
                }
                int tab = mTabHost.getCurrentTab();
                mTabHost.getTabWidget().getChildAt(tab).setBackgroundColor(Color.parseColor("#ff0099CC"));


                switch (tabId) {
                    case "Tab One": {
                        ((FragmentMain.Callback) getActivity()).OnTabchange(MainActivity.TAB_MENU, isSearchResult);

                        break;
                    }
                    case "Tab Two": {
                        ((FragmentMain.Callback) getActivity()).OnTabchange(MainActivity.TAB_DISH, isSearchResult);

                        break;
                    }
                    case "Tab three": {
                        ((FragmentMain.Callback) getActivity()).OnTabchange(MainActivity.TAB_MEP_PREP, isSearchResult);

                        break;
                    }
                    case "Tab four": {
                        ((FragmentMain.Callback) getActivity()).OnTabchange(MainActivity.TAB_INGREDIENT, isSearchResult);

                        break;
                    }
                    case "Tab five": {

                        ((FragmentMain.Callback) getActivity()).OnTabchange(MainActivity.TAB_SEARCH, isSearchResult);

                        break;
                    }

                }

            }
        });
        int tab = mTabHost.getCurrentTab();
        mTabHost.getTabWidget().getChildAt(tab).setBackgroundColor(Color.parseColor("#ff0099CC"));


        return view;
    }

    public void onSearchresult(ArrayList result) {


        isSearchResult = true;
        Tab_search.searchArray = result;


        mTabHost.setCurrentTab(4);
        Tab_search tabSearch = (Tab_search)
                fm.findFragmentByTag("Tab five");
        if (tabSearch != null) {
            tabSearch.onSearchUpdate();
        }

        isSearchResult = false;

    }


}


