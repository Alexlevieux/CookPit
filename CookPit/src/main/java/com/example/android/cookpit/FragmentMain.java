package com.example.android.cookpit;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.android.cookpit.TabFragments.Tab_MEP;
import com.example.android.cookpit.TabFragments.Tab_dish;
import com.example.android.cookpit.TabFragments.Tab_ingredient;
import com.example.android.cookpit.TabFragments.Tab_menu;
import com.example.android.cookpit.TabFragments.Tab_search;


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


    public FragmentMain() {
        // Required empty public constructor
    }

    private FragmentTabHost mTabHost;

    public static FragmentMain newInstance(Boolean isSearchResult, Object result) {


        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();

        args.putBoolean(ARG_PARAM1, isSearchResult);
        fragment.setArguments(args);
        return fragment;
    }

    public interface Callback {

        public void OnTabchange(String tabName, Boolean isSearchResult);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void onSearchresult(Object result) {
        isSearchResult = true;
        mTabHost.setCurrentTab(4);
        isSearchResult = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTabHost = (FragmentTabHost) view.findViewById(R.id.tabhost);
        mTabHost.setup(getContext(), getFragmentManager(), R.id.realtabcontent);


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
        mTabHost.addTab(spec, Tab_search.class, savedInstanceState);


        mTabHost.setSaveEnabled(true);


        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

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


        return view;
    }


}

