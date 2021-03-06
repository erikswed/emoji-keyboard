package com.gotcreations.emojikeyboard.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gotcreations.emojikeyboard.R;
import com.gotcreations.emojikeyboard.fragments.WhatsUPFragment;
import com.gotcreations.emojilibrary.controller.WhatsAppPanelForFragment;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class ActivityWhatsAppForFragment extends AppCompatActivity {

    public static final String TAG = "ActivityWhatsAppForFragment";

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private WhatsAppPanelForFragment mBottomPanel;

    // CALLBACKS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.act_whatsapp_fragment);
        this.initToolbar();
        this.initDrawerMenu();
        this.setWhatsAppTheme();
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.drawer_layout, new WhatsUPFragment(), TAG);
        mFragmentTransaction.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_toogle:
                Intent intent = new Intent(this, ActivityTelegramForFragment.class);
                startActivity(intent);
                finish();
                break;
            case android.R.id.home:
                if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    this.mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    this.mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // INITIALIZATIONS
    private void initDrawerMenu() {
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.findViewById(R.id.github_thumbnail);

        CircularImageView thumbnail = (CircularImageView) this.findViewById(R.id.github_thumbnail);

        Picasso.with(this)
                .load(R.drawable.github)
                .resize(60, 60)
                .centerCrop()
                .into(thumbnail);

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/instachat/emoji-library"));
                startActivity(browserIntent);
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                this.mDrawerLayout,
                ActivityWhatsAppForFragment.this.mToolbar,
                R.string.drawer_open,
                R.string.drawer_close
        )

        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };

        this.mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initToolbar() {
        this.mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.mToolbar.setTitleTextColor(0xFFFFFFFF);
        this.mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        this.getWindow().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.telegram_bkg));
        this.setSupportActionBar(this.mToolbar);
        this.getSupportActionBar().setTitle("Whats App Fragment");
    }

    private void setWhatsAppTheme() {
        this.getWindow().setBackgroundDrawable(ActivityWhatsAppForFragment.this.getResources().getDrawable(R.drawable.whatsapp_bkg));
        this.mToolbar.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryWhatsApp));
        if (Build.VERSION.SDK_INT >= 21) {
            this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkWhatsApp));
        }
    }

    @Override
    public void onBackPressed() {
        final Fragment frag = getSupportFragmentManager().findFragmentByTag(TAG);
        if(((WhatsUPFragment)frag).onBackPress()) {
            Intent intent = new Intent(this, LauncherActivity.class);
            startActivity(intent);
            super.onBackPressed();
        }
    }
}
