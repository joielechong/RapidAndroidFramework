package com.soi.rapidandroidapp.ui.common;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.soi.rapidandroidapp.managers.AnalyticsManager;
import com.soi.rapidandroidapp.managers.EnvironmentManager;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class BaseActivity extends Activity {

    @Inject
    protected EnvironmentManager environmentManager;

    /**
     * The current action bar of the screen if it's
     * exist
     */
    protected ActionBar actionBar;

    /**
     * The title of the actionbar if it's exist
     */
    protected String actionBarTitle;

    /**
     * The screen name you want to track in google analytics
     * or logging
     */
    protected String SCREEN_NAME = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initActionBar();
        if (SCREEN_NAME != null && environmentManager.canTrackGA())
            AnalyticsManager.getInstance().trackScreenView(SCREEN_NAME);
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        ButterKnife.inject(this);
        if (this.actionBarTitle != null && this.actionBar != null) {
            this.actionBar.setTitle(actionBarTitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializes action bar if it exists with some defaults
     */
    public void initActionBar() {

        this.actionBar = getActionBar();

        if (this.actionBar != null && this.actionBar.isShowing()) {
            this.actionBar.setDisplayHomeAsUpEnabled(true);
            this.actionBar.setDisplayShowHomeEnabled(true);
            this.actionBar.setDisplayUseLogoEnabled(true);

        }
    }
}
