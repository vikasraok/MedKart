package in.medkart.pocketpharma.Activity;

import android.app.*;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import in.medkart.pocketpharma.Fragment.ConatctUs;
import in.medkart.pocketpharma.Fragment.Favourite;
import in.medkart.pocketpharma.Fragment.Home;
import in.medkart.pocketpharma.Model.NavDrawerItem;
import in.medkart.pocketpharma.R;
import in.medkart.pocketpharma.Fragment.SetLocation;
import in.medkart.pocketpharma.Fragment.Share;

import java.util.ArrayList;
import java.util.List;


public class DrawerActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    private int menuPosSelected = 0;
    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private FrameLayout mframe;
    private CharSequence actionbarTitle;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.menu_item_array);
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        mframe = (FrameLayout) findViewById(R.id.content_frame);
        // enabling action bar app icon and behaving it as toggle button
        if (mDrawerLayout != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
            mDrawerLayout.setScrimColor(getResources().getColor(R.color.scrimcolor));
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.drawable.ic_drawer, //nav menu toggle icon
                    R.string.app_name, // nav drawer open - description for accessibility
                    R.string.app_name // nav drawer close - description for accessibility
            ) {
                public void onDrawerClosed(View view) {
                    getActionBar().setTitle(mTitle);
                    // calling onPrepareOptionsMenu() to show action bar icons
                    invalidateOptionsMenu();
                    InputMethodManager inputMethodManager = (InputMethodManager) DrawerActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(DrawerActivity.this.getCurrentFocus().getWindowToken(), 0);
                }

                public void onDrawerOpened(View drawerView) {
                    getActionBar().setTitle(mDrawerTitle);
                    InputMethodManager inputMethodManager = (InputMethodManager) DrawerActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(DrawerActivity.this.getCurrentFocus().getWindowToken(), 0);
                    // calling onPrepareOptionsMenu() to hide action bar icons
                    invalidateOptionsMenu();
                }

                public void onDrawerSlide(View drawerView, float slideOffset) {
                    float moveFactor = (mDrawerList.getWidth() * slideOffset);
                    mframe.setTranslationX(moveFactor);
                }
            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);
        }
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        } else {
            if (savedInstanceState.getBoolean("isDrawerOpen")) {
                mTitle = savedInstanceState.getCharSequence("title");
            } else {
                setTitle(savedInstanceState.getCharSequence("title"));
            }
            menuPosSelected = savedInstanceState.getInt("SELECTED", menuPosSelected);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SELECTED", menuPosSelected);
        outState.putCharSequence("title", mTitle);
        if (mDrawerLayout != null) {
            outState.putBoolean("isDrawerOpen", mDrawerLayout.isDrawerOpen(mDrawerList));
        }
        // outState.putBoolean("DRAWER_OPEN", isDrawerOpen);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        DialogFragment dialogFragment = null;
        switch (position) {
            case 0:
                fragment = new Home();
                break;
            case 1:
                fragment = new Favourite();
                break;
            case 2:
                fragment = new SetLocation();
                break;
            case 3:
                dialogFragment = new Share();
                break;
            case 4:
                fragment = new ConatctUs();
                break;
            case 5:
                Intent intent = new Intent(DrawerActivity.this, SignUp.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        if (fragment != null || dialogFragment !=null) {
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            FragmentManager fragmentManager = getFragmentManager();
            if (mDrawerLayout != null && position != 3) {
                mDrawerLayout.closeDrawer(mDrawerList);
            }

            if(position !=3) {

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();
            }
            else{
                dialogFragment.show(fragmentManager, "dialog");
            }
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position);
            menuPosSelected = position;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.badge:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
	 */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        if (mDrawerLayout != null) {
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
            menu.findItem(R.id.badge).setVisible(!drawerOpen);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {

        mTitle = title;
        if (mDrawerLayout != null) {
            getActionBar().setTitle(mTitle);
        }
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (mDrawerLayout != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        if (mDrawerLayout != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawers();
                return;
            } else handleBackPress();
        } else handleBackPress();
    }

    private void handleBackPress() {
        if (menuPosSelected != 0) { //If we are not in Home, lets got there
            displayView(0);
            menuPosSelected = 0;
        } else {
            if (doubleBackToExitPressedOnce) {
                this.finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    private class NavDrawerListAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<NavDrawerItem> navDrawerItems;

        public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
            this.context = context;
            this.navDrawerItems = navDrawerItems;
        }

        @Override
        public int getCount() {
            return navDrawerItems.size();
        }

        @Override
        public Object getItem(int position) {
            return navDrawerItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater)
                        context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            }

            ImageView imgIcon = (ImageView) convertView.findViewById(R.id.menuIcon);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.tvHeader);

            imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
            txtTitle.setText(navDrawerItems.get(position).getTitle());
            if (position == menuPosSelected) {
                convertView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
            } else {
                convertView.setBackgroundColor(0x00000000);
            }
            return convertView;
        }

    }

    private void ShareWithFriends() {
        Resources resources = getResources();
        PackageManager pm = getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if (packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if (packageName.contains("twitter")) {
                    intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_twitter));
                } else if (packageName.contains("facebook")) {
                    // Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
                    // One workaround is to use the Facebook SDK to post, but that doesn't allow the user to choose how they want to share. We can also make a custom landing page, and the link
                    // will show the <meta content ="..."> text from that page with our link in Facebook.
                    intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_facebook));
                }
                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        // convert intentList to array
        LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);
        sendIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        startActivity(sendIntent);
    }
}
