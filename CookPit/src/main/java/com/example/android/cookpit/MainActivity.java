package com.example.android.cookpit;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cookpit.Fragments.FragmentMain;
import com.example.android.cookpit.Fragments.Home;
import com.example.android.cookpit.Controller.Adapter.DishViewPagerAdapter;
import com.example.android.cookpit.DetailActivities.DetailDish;
import com.example.android.cookpit.Fragments.Dish_detail_fragment;
import com.example.android.cookpit.Model.Cloud;
import com.example.android.cookpit.Model.UtilityPojo;
import com.example.android.cookpit.Fragments.TabFragments.EditDishFragments.EditTabInstruction;
import com.example.android.cookpit.Fragments.TabFragments.Tab_dish;
import com.example.android.cookpit.Fragments.TabFragments.Tab_menu;
import com.example.android.cookpit.Fragments.TabFragments.Tab_menu_content;
import com.example.android.cookpit.Model.data.KitchenContract;
import com.example.android.cookpit.Model.pojoClass.Dish;
import com.example.android.cookpit.Model.pojoClass.Ingredient;
import com.example.android.cookpit.Model.pojoClass.Mep;
import com.example.android.cookpit.Model.pojoClass.cookPitUser;
import com.example.android.cookpit.Model.pojoClass.sequence;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;

import android.support.design.widget.NavigationView;

public class MainActivity extends AppCompatActivity implements GalleryActivity.Callback, EditTabInstruction.searchOption, Tab_menu.Callback, FragmentMain.Callback, Cloud.cloudLoadCallBack, Home.OnFragmentInteractionListener {

    public static final String TAB_MENU_FRAGMENT_TAG = "TMFTAG";
    public static final String DISHFRAGMENT_TAG = "DFTAG";
    public static final String DETAILDISHFRAGMENT_TAG = "DDFTAG";
    public static final String TWOPANETAG = "TWOPANE";
    public static final String ONLINE = "Online";
    public static final String OFFLINE = "Offline";

    public static final String TAB_MENU = "MENU";
    public static final String TAB_DISH = "DISH";
    public static final String TAB_MEP_PREP = "PREP and METHOD";
    public static final String TAB_INGREDIENT = "INGREDIENT";
    public static final String TAB_SEARCH = "SEARCH";
    public static final String SEARCH_TYPE = "search_type";

    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    public static final int RC_SIGN_IN = 1;
    public cookPitUser mUser;
    public String username;

    public String connectStatus;
    boolean authFlag = false;
    private boolean mTwoPane;
    private boolean isSearchResult;
    android.support.v7.app.ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    FragmentManager fragmentManager;

    TextView menuTitle;
    TextView detailTitle;
    SearchView searchview;
    View progressArc;

    Cloud cloud;


    String tab;
    String[] selectionArgs;

    public ViewPager viewPager;
    public DishViewPagerAdapter pagerAdapter;


    boolean isConnected;


    public ArrayList<com.example.android.cookpit.Model.pojoClass.Menu> menuSearch;
    public ArrayList<Dish> dishSearch;
    public ArrayList<Mep> mepSearch;
    public ArrayList<Ingredient> ingredientSearch;
    private ArrayList searchArray;
    int searchType;

    Home home;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthstateListener;

    @Override
    protected void onPause() {
        fragmentManager = getSupportFragmentManager();

        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mAuth != null) mAuth.removeAuthStateListener(mAuthstateListener);
        if (cloud != null) cloud.close();


        super.onSaveInstanceState(outState);

    }


    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        setIntent(intent);



    }

    @Override
    public void onStart() {
        super.onStart();
        if (isConnected && mAuth != null) {
            mAuth.addAuthStateListener(mAuthstateListener);


        }


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        preferences = getPreferences(MODE_PRIVATE);
        editor = preferences.edit();
        progressArc = findViewById(R.id.arc_progress);
        setCustomActionBar();
        NavigationView navigationView = findViewById(R.id.left_drawer);


        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.button_text_save) {
            @Override
            public void onDrawerOpened(View drawerView) {

                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);

            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {


                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {

                            case R.id.sign_out_button:
                                Log.v("sign out", "test");
                                authFlag = false;
                                username = "";
                                cloud = null;
                                mAuth.signOut();


                                return true;

                        }

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });


        isSearchResult = false;


        mAuthstateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    connectStatus = ONLINE;


                } else {
                    if (!authFlag) {
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                                        .setIsSmartLockEnabled(true)
                                        .build(),
                                RC_SIGN_IN);
                        authFlag = true;

                    }

                }

            }
        };

        ConnectivityManager cm =
                (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if (savedInstanceState == null) {
//execute or not new database  "taskdatabase.execute();"//
            Home home = Home.newInstance(null, null);

            if (fragmentManager == null) {
                fragmentManager = getSupportFragmentManager();
            }
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, home);

            fragmentTransaction.commit();



            if (isConnected) {
                mAuth = FirebaseAuth.getInstance();
                if (mAuth.getUid() == null) {
                    mAuth.signOut();
                    username = "";
                    connectStatus = OFFLINE;

                }
                connectStatus = ONLINE;
                createCloud();

            }



        } else {


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Home.newInstance(null, null))
                    .commit();

            mAuth = FirebaseAuth.getInstance();
            progressArc = findViewById(R.id.arc_progress);
            cloud = Cloud.startCloud(mAuth, progressArc, this);
            cloud.defineCookPitUser();

            onDataInserted();

        }

        if (findViewById(R.id.dish_detail_container) != null) {
            viewPager = findViewById(R.id.dish_detail_container);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                }


                @Override
                public void onPageSelected(int position) {
                    Tab_menu fragment = (Tab_menu) getSupportFragmentManager().findFragmentById(R.id.realtabcontent);

                    if (fragment != null) {


                        Tab_menu_content menuFragment = (Tab_menu_content) fragment.getChildFragmentManager().findFragmentById(R.id.Tab_menu_content);

                        if (menuFragment != null) {

                            menuFragment.onPageScroll(position);
                        }
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            mTwoPane = true;


            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.dish_detail_container, new Dish_detail_fragment(), DETAILDISHFRAGMENT_TAG)
                        .commit();


            }
        } else {
            mTwoPane = false;


        }


    }

    public void createCloud() {
        if (cloud == null) {

            cloud = Cloud.startCloud(mAuth, progressArc, this);

            cloud.defineCookPitUser();
            onDataInserted();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Home.newInstance(null, null))
                    .commit();


        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();

                createCloud();



                return;
            }
            if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }
    }

    @Override
    public void onStop() {

        super.onStop();

    }

    @Override
    protected void onResume() {

        super.onResume();
        Intent intent = getIntent();

        if (intent.getAction().equals(Intent.ACTION_SEARCH) && intent.getStringExtra(SEARCH_TYPE) != null) {

            String SearchType = intent.getStringExtra(SEARCH_TYPE);
            isSearchResult = true;
            String query = intent.getStringExtra(SearchManager.QUERY);
            query = query + "*";
            selectionArgs = new String[]{query};
            ArrayList arraylist = new ArrayList();
            Log.v("SearchType", String.valueOf(SearchType));
            searchType = 0;
            switch (SearchType) {

                case TAB_MENU: {
                    Uri FTS_Menu_Uri = KitchenContract.FtsMenuEntry.buildFtsMenuUri(query);

                    Cursor c = getContentResolver().query(
                            FTS_Menu_Uri,
                            null,
                            null,
                            selectionArgs,
                            null
                    );
                    Log.v(String.valueOf(c.getCount()), "test");
                    menuSearch = UtilityPojo.getMenu(c);

                    if (menuSearch.size() > 0) {
                        for (int i = 0; i < menuSearch.size(); i++) {

                            Log.v(menuSearch.get(i).getId() + "  " + menuSearch.get(i).getName(), menuSearch.get(i).getUsername());
                        }
                        View view = this.getCurrentFocus();

                        arraylist = menuSearch;
                        searchType = 1;

                    }


                    c.close();
                    break;

                }

                case TAB_DISH: {

                    Uri Fts_Dish_Uri = KitchenContract.FtsDishEntry.buildFtsDishUri(query);

                    Cursor c = getContentResolver().query(
                            Fts_Dish_Uri,
                            null,
                            null,
                            selectionArgs,
                            null
                    );
                    Log.v(String.valueOf(c.getCount()), "test");
                    dishSearch = UtilityPojo.getDish(c);

                    if (dishSearch.size() > 0) {
                        for (int i = 0; i < dishSearch.size(); i++) {
                            Log.v(dishSearch.get(i).getId() + "  " + dishSearch.get(i).getName(), dishSearch.get(i).getDescription());

                        }
                        arraylist = dishSearch;
                        searchType = 2;
                    }
                    c.close();
                    break;
                }
                case TAB_MEP_PREP: {

                    Uri Fts_Mep_Uri = KitchenContract.FtsMepEntry.buildFtsMepUri(query);

                    Cursor c = getContentResolver().query(
                            Fts_Mep_Uri,
                            null,
                            null,
                            selectionArgs,
                            null
                    );


                    mepSearch = UtilityPojo.getMep(c);

                    Log.v(String.valueOf(mepSearch.size()), "test");
                    if (mepSearch.size() > 0) {

                        for (int i = 0; i < mepSearch.size(); i++) {
                            String description;
                            if (mepSearch.get(i).getDescription().isEmpty()) {
                                description = "null";

                            } else description = mepSearch.get(i).getDescription();

                            Log.v(mepSearch.get(i).getId() + "  " + mepSearch.get(i).getName(), description);
                            arraylist = mepSearch;
                            searchType = 3;

                        }

                    }
                    c.close();
                    break;
                }
                case TAB_INGREDIENT: {

                    Uri Fts_Ingredient_Uri = KitchenContract.FtsIngredientEntry.buildFtsIngredientUri(query);

                    Cursor c = getContentResolver().query(
                            Fts_Ingredient_Uri,
                            null,
                            null,
                            selectionArgs,
                            null
                    );


                    ingredientSearch = UtilityPojo.getIngredient(c);

                    Log.v(String.valueOf(ingredientSearch.size()), "test");
                    if (ingredientSearch.size() > 0) {

                        for (int i = 0; i < ingredientSearch.size(); i++) {


                            Log.v(ingredientSearch.get(i).getId() + "  " + ingredientSearch.get(i).getName(), "test");

                        }
                        arraylist = ingredientSearch;
                        searchType = 4;
                    }
                    c.close();
                    break;
                }

                case TAB_SEARCH: {


                    break;
                }

            }
            searchArray = arraylist;
            if (fragmentManager == null) {
                fragmentManager = getSupportFragmentManager();
            }


            FragmentMain fragmentMain = (FragmentMain)
                    fragmentManager.findFragmentByTag(TAB_MENU_FRAGMENT_TAG);
            if (fragmentMain != null) {
                fragmentMain.onSearchresult(searchArray);
            }


        }



        Dish_detail_fragment fragment = (Dish_detail_fragment) getSupportFragmentManager().findFragmentByTag(MainActivity.DISHFRAGMENT_TAG);

        if (fragment != null) {
            fragment.updatePicture();
        }

    }

    @Override
    public void onItemSelected(ArrayList<Uri> Uris, String dishName, int position, String category) {

        if (mTwoPane == true) {

            detailTitle.setText(dishName);


            pagerAdapter = new DishViewPagerAdapter(getSupportFragmentManager(), Uris);

            viewPager.setAdapter(pagerAdapter);

            pagerAdapter.notifyDataSetChanged();
            viewPager.setCurrentItem(position);


        } else {
            Intent intent = new Intent(this, DetailDish.class)
                    .putParcelableArrayListExtra(Tab_menu.DETAIL_URI, Uris)
                    .putExtra(Tab_menu.POSITION, position)
                    .putExtra("category", category);

            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();


    }


    @Override
    public void OnTabchange(String tabName, Boolean isSearchResult) {
        Log.v(tab, String.valueOf(isSearchResult));
        if (isSearchResult == false) {
            tab = tabName;
        }

        if (tab == "SEARCH") {

            tab = " all";
        }

        if (searchview != null && isSearchResult == false) {
            searchview.setQueryHint(getResources().getString(R.string.search_hint, tab));
        }
        if (menuTitle != null && isSearchResult == false) {
            menuTitle.setText(tabName);
        }
        Log.v("tab name", tab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);


        return true;
    }


    @SuppressLint("RestrictedApi")
    public void setCustomActionBar() {
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);

        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar, null);
        mActionBar.setCustomView(v);


        mActionBar.setDisplayShowCustomEnabled(true);

        menuTitle = v.findViewById(R.id.title_text);
        detailTitle = v.findViewById(R.id.detail_text);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        tab = "MENU";
        searchview = v.findViewById(R.id.searchView);

        searchview.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchview.setQueryHint(getResources().getString(R.string.search_hint, tab));

        searchview.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    menuTitle.setVisibility(View.GONE);
                } else {
                    searchview.onActionViewCollapsed();
                    menuTitle.setVisibility(View.VISIBLE);
                }
            }

        });
        searchview.setSubmitButtonEnabled(true);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                Intent searchIntent = new Intent(getApplicationContext(), MainActivity.class);
                searchIntent.setAction(Intent.ACTION_SEARCH);
                searchIntent.putExtra(SearchManager.QUERY, query);
                searchIntent.putExtra(SEARCH_TYPE, tab);
                startActivity(searchIntent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() >= 4) {
                    Intent searchIntent = new Intent(getApplicationContext(), MainActivity.class);
                    searchIntent.setAction(Intent.ACTION_SEARCH);
                    searchIntent.putExtra(SearchManager.QUERY, newText);
                    searchIntent.putExtra(SEARCH_TYPE, tab);
                    startActivity(searchIntent);


                }
                return false;

            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {


            return true;

        }
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSequenceLoaded(sequence[] sequence) {
        cloud.insertSequencesToSqlite();


    }


    @Override
    public void onDataInserted() {
        FragmentMain fragment = (FragmentMain) getSupportFragmentManager().findFragmentByTag(TAB_MENU_FRAGMENT_TAG);
        if (fragment != null) {
            Tab_menu childFragment = (Tab_menu) fragment.getChildFragmentManager().findFragmentByTag("Tab One");
            childFragment.getLoaderManager().restartLoader(Tab_menu.MENU_LOADER, fragment.getArguments(), childFragment);

            Tab_dish fragmentDish = (Tab_dish) fragment.getChildFragmentManager().findFragmentByTag("Tab Two");
            if (fragmentDish != null) {
                fragmentDish.getLoaderManager().restartLoader(Tab_dish.DISH_LOADER, fragmentDish.getArguments(), fragmentDish);

            }


        }
    }

    @Override
    public ArrayList<Ingredient> onQuery(String querytext) {
        Uri Fts_Ingredient_Uri = KitchenContract.FtsIngredientEntry.buildFtsIngredientUri(querytext);

        Cursor c = getContentResolver().query(
                Fts_Ingredient_Uri,
                null,
                null,
                selectionArgs,
                null
        );


        ingredientSearch = UtilityPojo.getIngredient(c);

        Log.v(String.valueOf(ingredientSearch.size()), "test");
        if (ingredientSearch.size() > 0) {

            for (int i = 0; i < ingredientSearch.size(); i++) {


                Log.v(ingredientSearch.get(i).getId() + "  " + ingredientSearch.get(i).getName(), "test");

            }

        }
        c.close();
        return ingredientSearch;
    }

    @Override
    public void onFragmentInteraction(int fragmentType) {
        Log.v("fragment number:", String.valueOf(fragmentType));

        switch (fragmentType) {
            case 1:
                if (fragmentManager == null) {
                    fragmentManager = getSupportFragmentManager();
                }
                FragmentMain fm = FragmentMain.newInstance(false, null);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fm, TAB_MENU_FRAGMENT_TAG);
                transaction.addToBackStack(null);
                transaction.commit();


                break;
            case 2:
                Intent intent = new Intent(this, GalleryActivity.class);

                startActivity(intent);
                break;
            case 3:
                break;


        }

    }


    @Override
    public void onGalleryOpening() {

    }

    @Override
    public void onImageadded(ArrayList<Uri> ImageUris) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference picturesRef = storageRef.child("Pictures");

        for (int i = 0; i < ImageUris.size(); i++) {

            StorageReference pictureRef = picturesRef.child(ImageUris.get(i).getLastPathSegment());
            UploadTask uploadTask = pictureRef.putFile(ImageUris.get(i));

// Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });


        }


    }
}
