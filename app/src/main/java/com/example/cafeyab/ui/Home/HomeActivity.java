package com.example.cafeyab.ui.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.cafeyab.R;
import com.example.cafeyab.ui.Home.Map.NearbyFragment;
import com.example.cafeyab.ui.Home.cafepagesinfo.CafePageInfoFragment;
import com.example.cafeyab.ui.Home.cafepagesinfo.CommentsFragment;
import com.example.cafeyab.ui.Home.events.EventFragment;
import com.example.cafeyab.ui.Login.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity implements HomeFragment.OnNormalsearchListener, HomeFragment.OnNearbyListener, HomeFragment.OnFavouriteListener, BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, CafePageInfoFragment.OnCommentspageListener {

    private Toolbar toolbar;
    private TextView title;
    DrawerLayout  drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Bottom Navigation options
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setItemIconTintList(null);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,new HomeFragment()).commit();

        //default_toolbar
//        toolbar =findViewById(R.id.default_toolbar);
//       toolbar.setTitle("");
//        setSupportActionBar(toolbar);
        title=findViewById(R.id.tite);
        initializeViews();
        toggleDrawer();
        initializeDefaultFragment(savedInstanceState,0);

    }


    private void initializeViews() {
        toolbar = findViewById(R.id.default_toolbar);
        toolbar.setTitle("toolbar_title");
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    /**
     * Checks if the savedInstanceState is null - onCreate() is ran
     * If so, display fragment of navigation drawer menu at position itemIndex and
     * set checked status as true
     * @param savedInstanceState
     * @param itemIndex
     */
    private void initializeDefaultFragment(Bundle savedInstanceState, int itemIndex){
        if (savedInstanceState == null){
            MenuItem menuItem = navigationView.getMenu().getItem(itemIndex).setChecked(true);
            onNavigationItemSelected(menuItem);
        }
    }

    /**
     * Creates an instance of the ActionBarDrawerToggle class:
     * 1) Handles opening and closing the navigation drawer
     * 2) Creates a hamburger icon in the toolbar
     * 3) Attaches listener to open/close drawer on icon clicked and rotates the icon
     */
    private void toggleDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        //Checks if the navigation drawer is open -- If so, close it
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        // If drawer is already close -- Do not override original functionality
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.home_toolbar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,new HomeFragment()).addToBackStack("Homepage").commit();
                deSelectCheckedState();
                navigationView.getMenu().getItem(0).setChecked(true);
                closeDrawer();
                break;

            case R.id.logout_toolbar:
                MainActivity.prefConfig.writeLoginStatus(false);
                MainActivity.prefConfig.writeName("User");
                Intent mainIntent =new Intent(getBaseContext(),MainActivity.class);
                startActivity(mainIntent);
                finish();
                closeDrawer();
                break;

            case R.id.about_toolbar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,new AboutFragment()).addToBackStack("Homepage").commit();
                deSelectCheckedState();
                navigationView.getMenu().getItem(2).setChecked(true);
                closeDrawer();
                break;


        }
        return true;
    }

    /**
     * Iterates through all the items in the navigation menu and deselects them:
     * removes the selection color
     */
    private void deSelectCheckedState(){
        int noOfItems = navigationView.getMenu().size();
        for (int i=0; i<noOfItems;i++){
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    /**
     * Checks if the navigation drawer is open - if so, close it
     */
    private void closeDrawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }



    //Buttom Navigation options

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;

                    switch (item.getItemId()){

                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            title.setText("HomePage");
                            title.setTextSize(22);
                            break;

                        case R.id.navigation_advanced_search:
                            selectedFragment = new AdvancedSearchFragment();
                            title.setText("Advanced Search");
                            title.setTextSize(22);
                            break;

                        case R.id.navigation_events:
                            selectedFragment = new EventFragment();
                            title.setText("Events");
                            title.setTextSize(22);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,selectedFragment).addToBackStack("HomePage").commit();
                    return true;
                }
            };



    //default toolbar menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.default_toolbar_menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected( MenuItem item) {
//        int id= item.getItemId();
//        if(id==R.id.logout_toolbar){
//           MainActivity.prefConfig.writeLoginStatus(false);
//           MainActivity.prefConfig.writeName("User");
//            Intent mainIntent =new Intent(getBaseContext(),MainActivity.class);
//            startActivity(mainIntent);
//            finish();
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    // going to normal search from home to normal search fragment
    @Override
    public void gonormalsearchpage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,new NormalSearchFragment()).addToBackStack("Homepage").commit();
    }
    // going to Nearbymap from
    @Override
    public void gonearbypage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,new NearbyFragment()).addToBackStack("Homepage").commit();
    }

    // going to favourite from home
    @Override
    public void gofavouritepage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,new FavouriteFragment()).addToBackStack("Homepage").commit();
    }


    @Override
    public void gocommentpage(String cafe_id) {
        Bundle bundle = new Bundle();
        bundle.putString("cafe_id",cafe_id);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,fragment).addToBackStack("Homepage").commit();

    }
}
