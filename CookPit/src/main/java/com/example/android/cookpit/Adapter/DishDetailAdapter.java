package com.example.android.cookpit.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.TextView;

import com.example.android.cookpit.Utility;
import com.example.android.cookpit.R;
import com.example.android.cookpit.data.KitchenContract;

/**
 * Created by alexandrelevieux on 09/11/2016.
 */

public class DishDetailAdapter extends CursorTreeAdapter {


    private static final int VIEW_TYPE_GROUP = 2;
    private static final int VIEW_TYPE_GROUP_MEP = 0;
    private static final int VIEW_TYPE_GROUP_INGREDIENT = 1;


    private static final int VIEW_TYPE_CHILD = 2;
    private static final int VIEW_TYPE_CHILD_MEP = 0;
    private static final int VIEW_TYPE_CHILD_INGREDIENT = 1;


    private static final int COLUMN_DISH_ID = 0;
    private static final int COLUMN_MEP_ID = 1;
    private static final int COLUMN_MEP_NAME = 2;
    private static final int COLUMN_INGREDIENT_ID = 3;
    private static final int COLUMN_INGREDIENT_NAME = 4;
    private static final int COLUMN_QUANTITY = 5;


    public Context childcontext;

    public String[] childProjection = new String[]{
            KitchenContract.MepIngredientEntry.TABLE_NAME + "." + KitchenContract.MepIngredientEntry.COLUMN_MEP_ID,
            KitchenContract.MepEntry.TABLE_NAME + "." + KitchenContract.MepEntry._ID,
            KitchenContract.MepEntry.COLUMN_MEP_NAME,
            KitchenContract.IngredientEntry.TABLE_NAME + "." + KitchenContract.IngredientEntry._ID,
            KitchenContract.IngredientEntry.COLUMN_INGREDIENT_NAME,
            KitchenContract.DishMepIngredientEntry.COLUMN_QUANTITY,
    };

    public int ChildItemCount;
    public int itemInflated;


    public DishDetailAdapter(Cursor cursor, Context context) {

        super(cursor, context);
    }

    public int getchildViewType(Cursor cursor) {
        int typeReturn;
        if (cursor.isNull(cursor.getColumnIndex(KitchenContract.MepEntry.COLUMN_MEP_NAME))) {
            typeReturn = VIEW_TYPE_CHILD_MEP;
        } else {
            typeReturn = VIEW_TYPE_CHILD_INGREDIENT;
        }


        return typeReturn;
    }

    public int getGroupViewType(Cursor cursor) {
        int typeReturn;
        if (cursor.isNull(cursor.getColumnIndex(KitchenContract.MepEntry.COLUMN_MEP_NAME))) {
            typeReturn = VIEW_TYPE_GROUP_INGREDIENT;
        } else {
            typeReturn = VIEW_TYPE_GROUP_MEP;
        }


        return typeReturn;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        itemInflated = 0;
        ChildItemCount = 0;
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

        super.onGroupExpanded(groupPosition);
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {

        Long mepId = groupCursor.getLong(1);
        Cursor c = Utility.getChildCursorForDishDetailAdapter(childcontext, mepId, childProjection);

        return c;

    }

    @Override
    protected View newGroupView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent) {

        childcontext = context;
        int LayoutId = -1;
        int viewType = getGroupViewType(cursor);

        switch (viewType) {
            case VIEW_TYPE_GROUP_MEP: {
                LayoutId = R.layout.list_item_group_mep_dish_detail;
                break;
            }
            case VIEW_TYPE_GROUP_INGREDIENT: {

                LayoutId = R.layout.list_item_group_ingredient_dish_detail;
                break;
            }
        }

        View view = LayoutInflater.from(context).inflate(LayoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public long getCombinedGroupId(long groupId) {

        return super.getCombinedGroupId(groupId);
    }

    @Override
    protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int viewType = getGroupViewType(cursor);

        switch (viewType) {

            case VIEW_TYPE_GROUP_MEP: {
                String name = cursor.getString(COLUMN_MEP_NAME);
                String quantity = cursor.getString(COLUMN_QUANTITY);

                viewHolder.quantityView.setText(quantity);
                viewHolder.nameView.setText(name);

                break;
            }
            case VIEW_TYPE_GROUP_INGREDIENT: {

                String name = cursor.getString(COLUMN_INGREDIENT_NAME);
                String quantity = cursor.getString(COLUMN_QUANTITY);

                viewHolder.quantityView.setText(quantity);
                viewHolder.nameView.setText(name);
                break;
            }
        }


    }


    @Override
    protected View newChildView(Context context, Cursor cursor, boolean isLastChild, ViewGroup parent) {

        int LayoutId = -1;
        int viewType = getchildViewType(cursor);


        itemInflated++;
        switch (viewType) {
            case VIEW_TYPE_CHILD_MEP: {
                LayoutId = R.layout.list_item_child_ingredient_dish_detail;
                break;
            }
            case VIEW_TYPE_CHILD_INGREDIENT: {
                LayoutId = R.layout.list_item_child_mep_dish_detail;
                break;
            }
        }

        View view = LayoutInflater.from(context).inflate(LayoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);


        return view;
    }

    @Override
    protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int viewType = getGroupViewType(cursor);


        switch (viewType) {
            case VIEW_TYPE_GROUP_MEP: {

                String name = cursor.getString(COLUMN_MEP_NAME);
                String quantity = cursor.getString(COLUMN_QUANTITY);

                viewHolder.childnameView.setText(name);
                viewHolder.childquantityView.setText(quantity);

                if (cursor.isLast()) {
                    viewHolder.childMethod.setText("method :           \n  1) \n  2)  \n  3)   ");
                    viewHolder.childMethod.setVisibility(View.VISIBLE);

                } else {
                    viewHolder.childMethod.setVisibility(View.GONE);

                }
                break;
            }
            case VIEW_TYPE_GROUP_INGREDIENT: {

                String name = cursor.getString(COLUMN_INGREDIENT_NAME);
                String quantity = cursor.getString(COLUMN_QUANTITY);

                viewHolder.childnameView.setText(name);
                viewHolder.childquantityView.setText(quantity);

                if (cursor.isLast()) {

                    viewHolder.childMethod.setText("method :           \n  1) \n  2)  \n  3)   ");
                    viewHolder.childMethod.setVisibility(View.VISIBLE);

                } else {
                    viewHolder.childMethod.setVisibility(View.GONE);
                }

                break;
            }
        }
    }


    public static class ViewHolder {
        public final TextView nameView;
        public final TextView quantityView;
        public final TextView childnameView;
        public final TextView childquantityView;
        public final TextView childMethod;


        public ViewHolder(View view) {
            nameView = (TextView) view.findViewById(R.id.list_item_group_textview);
            quantityView = (TextView) view.findViewById(R.id.list_item_group_textview_quantity);
            childnameView = (TextView) view.findViewById(R.id.list_item_child_textview);
            childquantityView = (TextView) view.findViewById(R.id.list_item_child_textview_quantity);
            childMethod = (TextView) view.findViewById(R.id.child_method_textView);
        }
    }


}