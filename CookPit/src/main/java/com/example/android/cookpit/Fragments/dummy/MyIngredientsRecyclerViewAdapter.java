package com.example.android.cookpit.Fragments.dummy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.cookpit.R;
import com.example.android.cookpit.Model.pojoClass.Ingredient;
import com.example.android.cookpit.Model.pojoClass.Mep;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link IngredientContent.DummyItem} and makes a call to the
 * specified {@link IngredientsFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyIngredientsRecyclerViewAdapter extends RecyclerView.Adapter<MyIngredientsRecyclerViewAdapter.ViewHolder> {

    private final List mValues;
    private final IngredientsFragment.OnListFragmentInteractionListener mListener;

    public MyIngredientsRecyclerViewAdapter(List items, IngredientsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ingredients, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);


        Class className = holder.mItem.getClass();


        switch (className.getName()) {


            case "com.example.android.cookpit.Model.pojoClass.Mep":

                Mep mep = (Mep) holder.mItem;
                holder.mIdView.setText(String.valueOf(mep.getId()));
                holder.mContentView.setText(mep.getName());
                break;

            case "com.example.android.cookpit.Model.pojoClass.Ingredient":
                Ingredient ingredient = (Ingredient) holder.mItem;
                holder.mIdView.setText(String.valueOf(ingredient.getId()));
                holder.mContentView.setText(ingredient.getName());

                break;
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Object mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}