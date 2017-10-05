package com.gotcreations.emojilibrary.controller;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gotcreations.emojilibrary.R;
import com.gotcreations.emojilibrary.adapter.EmojiTabAdapter;
import com.gotcreations.emojilibrary.model.Emoji;
import com.gotcreations.emojilibrary.model.OnEmojiClickListener;
import com.gotcreations.emojilibrary.model.layout.EmojiCompatFragment;
import com.gotcreations.emojilibrary.model.layout.EmojiEditText;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

class EmojiKeyboardForFragment implements OnEmojiClickListener {
    private static final String TAG = "EmojiKeyboardForFragment";

    private EmojiCompatFragment mFragment;

    private ImageView[] mTabIcons = new ImageView[6];
    private RelativeLayout mEmojiKeyboardLayout;
    private EmojiEditText mInput;
    private ImageView mBackspace;

    // CONSTRUCTOR
    EmojiKeyboardForFragment(EmojiCompatFragment fragment, EmojiEditText input) {
        this.mInput = input;
        this.mFragment = fragment;
        this.mEmojiKeyboardLayout = (RelativeLayout) mFragment.getView().findViewById(R.id.emoji_keyboard);
        this.initEmojiKeyboardViewPager();
        this.setBackspaceBehaviour();
    }

    // INTIALIZATIONS
    private void initEmojiKeyboardViewPager() {
        final EmojiTabAdapter adapter = new EmojiTabAdapter(mFragment.getActivity().getSupportFragmentManager());
        adapter.setOnEmojiClickListener(this);
        final ViewPager viewPager = (ViewPager) mFragment.getView().findViewById(R.id.emoji_viewpager);
        viewPager.setAdapter(adapter);

        final SmartTabLayout viewPagerTab = (SmartTabLayout) mFragment.getView().findViewById(R.id.emoji_tabs);

        final LayoutInflater inf = LayoutInflater.from(mFragment.getActivity());
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                ImageView icon = (ImageView) inf.inflate(R.layout.rsc_emoji_tab_icon_view, container, false);
                switch (position) {
                    case 0:
                        EmojiKeyboardForFragment.this.mTabIcons[0] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        break;
                    case 1:
                        EmojiKeyboardForFragment.this.mTabIcons[1] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_people_light_normal);
                        break;
                    case 2:
                        EmojiKeyboardForFragment.this.mTabIcons[2] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        break;
                    case 3:
                        EmojiKeyboardForFragment.this.mTabIcons[3] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        break;
                    case 4:
                        EmojiKeyboardForFragment.this.mTabIcons[4] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_places_light_normal);
                        break;
                    case 5:
                        EmojiKeyboardForFragment.this.mTabIcons[5] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 6:
                        icon.setImageResource(R.drawable.sym_keyboard_delete_holo_dark);
                        break;
                }
                return icon;
            }
        });

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        // adapter.updateRecentEmojis();
                        EmojiKeyboardForFragment.this.mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_activated);
                        EmojiKeyboardForFragment.this.mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 1:
                        EmojiKeyboardForFragment.this.mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_activated);
                        EmojiKeyboardForFragment.this.mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 2:
                        EmojiKeyboardForFragment.this.mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_activated);
                        EmojiKeyboardForFragment.this.mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 3:
                        EmojiKeyboardForFragment.this.mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_activated);
                        EmojiKeyboardForFragment.this.mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 4:
                        EmojiKeyboardForFragment.this.mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_activated);
                        EmojiKeyboardForFragment.this.mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 5:
                        EmojiKeyboardForFragment.this.mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_normal);
                        EmojiKeyboardForFragment.this.mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_activated);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPagerTab.setViewPager(viewPager);
    }

    private void setBackspaceBehaviour() {
        this.mBackspace = (ImageView) mFragment.getView().findViewById(R.id.backspace);
        this.mBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmojiKeyboardForFragment.this.mInput.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            }
        });
    }

    //GETTERS AND SETTERS
    RelativeLayout getEmojiKeyboardLayout() {
        return mEmojiKeyboardLayout;
    }

    @Override
    public void onEmojiClicked(Emoji emojicon) {
        int start = this.mInput.getSelectionStart();
        int end = this.mInput.getSelectionEnd();

        if (start < 0) {
            this.mInput.append(emojicon.getEmoji());
        } else {
            this.mInput.getText().replace(Math.min(start, end), Math.max(start, end), emojicon.getEmoji(), 0, emojicon.getEmoji().length());
        }
    }
}
