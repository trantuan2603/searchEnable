package vn.com.ebizworld.searchenable;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ListView friendListView;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private FriendListAdapter friendListAdapter;
    private ArrayList<String> friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent  = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }
        setActionBar();
        initFriendList();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MainActivity.this.finish();
                MainActivity.this.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                MainActivity.hideSoftKeyboard(this);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getApplicationContext().getSystemService(activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Set action bar
     *      1. properties
     *      2. title with custom font
     */
    private void setActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Friends");

        //Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/vegur_2.otf");
//int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
//TextView actionBarTitle = (TextView) (this.findViewById(titleId));
//actionBarTitle.setTextColor(getResources().getColor(R.color.white));
        //actionBarTitle.setTypeface(typeface);
    }

    /**
     * Initialize friend list
     */
    private void initFriendList() {
        friendList = new ArrayList<>();
        friendList.add("1");
        friendList.add("2");
        friendList.add("3");
        friendList.add("4");
        friendList.add("5");
        friendList.add("6");
        friendList.add("6");
        friendList.add("7");
        friendList.add("8");
        friendList.add("9");
        friendList.add("10");
        friendList.add("1");
        friendList.add("2");
        friendList.add("3");
        friendList.add("4");
        friendList.add("5");
        friendList.add("6");
        friendList.add("6");
        friendList.add("7");
        friendList.add("8");
        friendList.add("9");
        friendList.add("10");

        friendListView = (ListView) findViewById(R.id.friend_list);
        friendListAdapter = new FriendListAdapter(this, friendList);

        // add header and footer for list
        View headerView = View.inflate(this, R.layout.list_header, null);
        View footerView = View.inflate(this, R.layout.list_header, null);
        friendListView.addHeaderView(headerView);
        friendListView.addFooterView(footerView);
        friendListView.setAdapter(friendListAdapter);
        friendListView.setTextFilterEnabled(false);

        // use to enable search view popup text
        friendListView.setTextFilterEnabled(true);

        // set up click listener
        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>0 && position <= friendList.size()) {
                    handelListItemClick((String)friendListAdapter.getItem(position - 1));
                }
            }
        });
    }

    /**
     * Navigate to share activity form here
     * @param user user
     */
    private void handelListItemClick(String user) {
        // close search view if its visible
        if (searchView.isShown()) {
            searchMenuItem.collapseActionView();
            searchView.setQuery("", false);
        }

        // pass selected user and sensor to share activity
//        Intent intent = new Intent(this, ShareActivity.class);
//        intent.putExtra("com.score.senzors.pojos.User", user);
//        intent.putExtra("com.score.senzors.pojos.Sensor", application.getCurrentSensor());
//        this.startActivity(intent);
        this.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        Toast.makeText(this, "i love you", Toast.LENGTH_SHORT).show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        friendListAdapter.getFilter().filter(newText);

        // use to enable search view popup text
        if (TextUtils.isEmpty(newText)) {
            friendListView.clearTextFilter();
        }
        else {
            friendListView.setFilterText(newText.toString());
        }

        return true;
    }
}
