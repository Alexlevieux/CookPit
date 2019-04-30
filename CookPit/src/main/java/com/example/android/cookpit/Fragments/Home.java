package com.example.android.cookpit.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.android.cookpit.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int FRAGMENT_MAIN = 1;
    private static final int FRAGMENT_GALLERY = 2;
    private static final int FRAGMENT_CREATOR = 3;
    FragmentMain fragmentMain;
    FragmentManager fragmentManager;
    private ImageButton.OnClickListener FmButtonListener;
    private ImageButton.OnClickListener GalleryButtonListener;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public Home() {
        GalleryButtonListener = new ImageButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                onButtonPressed(FRAGMENT_GALLERY
                );

            }
        };

        FmButtonListener = new ImageButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                onButtonPressed(FRAGMENT_MAIN);

            }


        };
    }


    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (fragmentManager == null) {
            fragmentManager = getFragmentManager();
        }

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton mainFragmentButton = view.findViewById(R.id.main_fragment_button);
        ImageButton galleryButton = view.findViewById(R.id.gallery_button);
        galleryButton.setOnClickListener(GalleryButtonListener);
        mainFragmentButton.setOnClickListener(FmButtonListener);

        return view;


    }


    public void onButtonPressed(int fragmentType) {
        if (mListener != null) {

            mListener.onFragmentInteraction(fragmentType);


        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(int FragmentType);
    }
}
