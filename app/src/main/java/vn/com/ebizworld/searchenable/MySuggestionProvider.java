package vn.com.ebizworld.searchenable;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by TRANTUAN on 01-Feb-18.
 */

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "vn.com.ebizworld.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}