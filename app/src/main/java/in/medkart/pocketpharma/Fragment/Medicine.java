package in.medkart.pocketpharma.Fragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import in.medkart.pocketpharma.Adapter.TabsPagerAdapter;
import in.medkart.pocketpharma.R;


public class Medicine extends FragmentActivity implements ActionBar.TabListener, View.OnClickListener {
    private CharSequence mTitle;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private View Counter;
    private Button Cart;
    private EditText Quantity;
    private ImageView Plus, Minus;
    static int mNotifCount = 0;
    // Tab titles
    private String[] tabs = {"Information", "Substitutes", "Interactions"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("med_name");
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());


        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        Plus = (ImageView) findViewById(R.id.plus);
        Minus = (ImageView) findViewById(R.id.minus);
        Quantity = (EditText) findViewById(R.id.quantity);
        Cart = (Button) findViewById(R.id.add_to_cart);
        Cart.setOnClickListener(this);
        Plus.setOnClickListener(this);
        Minus.setOnClickListener(this);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_actions, menu);
        MenuItem searchItem = menu.findItem(R.id.search_action);
        searchItem.setVisible(false);
      //  Counter = menu.findItem(R.id.cart_counter).getActionView();
        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {


    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
    private void setNotifCount(int count){
        mNotifCount = count;
        invalidateOptionsMenu();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_cart:
                Intent intent = new Intent(this, in.medkart.pocketpharma.Activity.Cart.class);
                String qty;
                qty = Quantity.getText().toString();
                if (Cart.getText().equals(getResources().getString(R.string.add_to_cart))) {
//                    Counter.setText(qty);

                    Toast.makeText(this, mTitle + " Added to cart", Toast.LENGTH_LONG).show();
                    Cart.setText("Proceed to cart");
                } else {
                    intent.putExtra("med_name", mTitle.toString());
                    startActivity(intent);
                    setNotifCount(1);
                }
                break;
            default:
                break;
        }

    }
}
