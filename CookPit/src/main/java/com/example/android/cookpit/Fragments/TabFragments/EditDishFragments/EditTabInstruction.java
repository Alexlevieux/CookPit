package com.example.android.cookpit.Fragments.TabFragments.EditDishFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.android.cookpit.DetailActivities.EditActivity;
import com.example.android.cookpit.Fragments.dummy.IngredientsFragment;
import com.example.android.cookpit.R;
import com.example.android.cookpit.Model.pojoClass.Ingredient;

import java.util.ArrayList;

import static com.example.android.cookpit.DetailActivities.EditActivity.INGREDIENT_SEARCH_TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTabInstruction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTabInstruction extends Fragment implements IngredientsFragment.OnListFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout searchFrame;

    EditText description;
    searchOption searchInterface;


    public EditTabInstruction() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditTabInstruction.
     */
    // TODO: Rename and change types and number of parameters
    public static EditTabInstruction newInstance(String param1, String param2) {
        EditTabInstruction fragment = new EditTabInstruction();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onListFragmentInteraction(Object item) {

    }

    public interface searchOption {
        ArrayList<Ingredient> onQuery(String querytext);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_tab_instruction, container, false);
        description = view.findViewById(R.id.edit_view);
        searchFrame = view.findViewById(R.id.edit_dish_ingredient_search_container);


        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0 && Character.isSpaceChar(s.charAt(s.length() - 1))) {
                    searchFrame.setVisibility(View.INVISIBLE);
                    return;
                }

                String[] words = s.toString().split(" ");
                String lastWord = words[words.length - 1];

                if (s.toString().contains("@") && lastWord.length() > 2) {

                    searchFrame.setVisibility(View.VISIBLE);
                    IngredientsFragment fragmentExist = (IngredientsFragment) getFragmentManager().findFragmentByTag(INGREDIENT_SEARCH_TAG);
                    if (fragmentExist != null) {

                        IngredientsFragment fragmentSearch = IngredientsFragment.newInstance(((EditActivity) getActivity()).searchData(lastWord.substring(1)));

                        getFragmentManager().beginTransaction()

                                .replace(R.id.edit_dish_ingredient_search_container, fragmentSearch, INGREDIENT_SEARCH_TAG)
                                .commit();

                    }

                    IngredientsFragment fragmentSearch = IngredientsFragment.newInstance(((EditActivity) getActivity()).searchData(lastWord.substring(1)));

                    getFragmentManager().beginTransaction()

                            .add(R.id.edit_dish_ingredient_search_container, fragmentSearch, INGREDIENT_SEARCH_TAG)
                            .commit();

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && Character.isSpaceChar(s.charAt(0))) {
                    s.append("");
                }


            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
