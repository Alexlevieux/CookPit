package com.example.android.cookpit.DetailActivities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.android.cookpit.R;
import com.example.android.cookpit.TabFragments.EditDishFragments.EditTabInstruction;

public class EditDishFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentTabHost mTabHost;


    public EditDishFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static EditDishFragment newInstance(String param1, String param2) {
        EditDishFragment fragment = new EditDishFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_dish, container, false);

        mTabHost = (FragmentTabHost) view.findViewById(R.id.tabhostedit);
        mTabHost.setup(getContext(), getFragmentManager(), R.id.editTabContent);
        setupUI(view);
        TabHost.TabSpec spec = mTabHost.newTabSpec("Tab One");
        View tabIndicator = LayoutInflater.from(getContext()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicator.findViewById(R.id.title)).setText("Ingredient");
        ((ImageView) tabIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.ingredient);
        spec.setIndicator(tabIndicator);
        mTabHost.addTab(spec, EditTabInstruction.class, savedInstanceState);

        TabHost.TabSpec spectwo = mTabHost.newTabSpec("Tab Two");
        View tabIndicatortwo = LayoutInflater.from(getContext()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicatortwo.findViewById(R.id.title)).setText("Mep");
        ((ImageView) tabIndicatortwo.findViewById(R.id.icon)).setImageResource(R.drawable.menu);
        spectwo.setIndicator(tabIndicatortwo);
        mTabHost.addTab(spectwo, EditTabInstruction.class, savedInstanceState);

        TabHost.TabSpec specthree = mTabHost.newTabSpec("Tab Three");
        View tabIndicatorthree = LayoutInflater.from(getContext()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicatorthree.findViewById(R.id.title)).setText("Picture");
        ((ImageView) tabIndicatorthree.findViewById(R.id.icon)).setImageResource(R.drawable.chaudron);
        specthree.setIndicator(tabIndicatorthree);
        mTabHost.addTab(specthree, EditTabInstruction.class, savedInstanceState);


        return view;

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
        if (view instanceof RecyclerView) {
            for (int i = 0; i < ((RecyclerView) view).getChildCount(); i++) {
                View innerView = ((RecyclerView) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}

