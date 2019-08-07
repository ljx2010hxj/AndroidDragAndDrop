package com.nida.recycleviewdraganddrop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nida.recycleviewdraganddrop.view.QuickGroup;
import com.nida.recycleviewdraganddrop.view.QuickItem;
import com.nida.recycleviewdraganddrop.view.RecycleViewQuickEditor;

import java.util.List;


public class RecycleViewDragActivity extends AppCompatActivity {
    private QuickGroup mQuickGroup;
    private RecycleViewQuickEditor mRecyclerViewQuickEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_main);
        initView();
    }

    private void initView() {
        TextView tv = (TextView) findViewById(R.id.recycle_view_txt);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecyclerViewQuickEditor != null) {
                    if (mRecyclerViewQuickEditor.getVisibility() == View.GONE) {
                        mQuickGroup.setVisibility(View.GONE);
                        mRecyclerViewQuickEditor.show();
                    } else if (mRecyclerViewQuickEditor.getVisibility() == View.VISIBLE) {
                        hideEditor();
                    }
                }
            }
        });
        mQuickGroup = (QuickGroup) findViewById(R.id.quick_group);
        mRecyclerViewQuickEditor = (RecycleViewQuickEditor) findViewById(R.id.recycle_view_editor);
        addItemToQuickGroup();

        ImageView img = (ImageView) findViewById(R.id.editor_back);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideEditor();
            }
        });
    }

    private void hideEditor() {
        mRecyclerViewQuickEditor.hide();
        mQuickGroup.setVisibility(View.VISIBLE);
        addItemToQuickGroup();
    }

    private void addItemToQuickGroup() {
        if (mQuickGroup == null) {
            return;
        }
        mQuickGroup.removeAllViews();
        List<String> specList = mRecyclerViewQuickEditor.getStoredItemsList();
        for (String spec : specList) {
            Log.d("nida", "addItemToQuickGroup spec=" + spec);
            mQuickGroup.addView(generateQuickItemBySpec(spec));
        }
        Log.d("nida", "mQuickGroup height=" + mQuickGroup.getHeight());
    }

    private QuickItem generateQuickItemBySpec(String spec) {
        QuickItem itemInfo = new QuickItem(this);
        if (spec.equals("rotation")) {
            itemInfo.setItemTitle(R.string.rotation_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_3d_rotation_black_24dp);
        } else if (spec.equals("accessibility")) {
            itemInfo.setItemTitle(R.string.accessibility_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_accessibility_black_24dp);
        } else if (spec.equals("accountbalance")) {
            itemInfo.setItemTitle(R.string.account_balance_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_account_balance_black_24dp);
        } else if (spec.equals("shopping")) {
            itemInfo.setItemTitle(R.string.shopping_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_add_shopping_cart_black_24dp);
        } else if (spec.equals("alarmadd")) {
            itemInfo.setItemTitle(R.string.alarm_add_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_alarm_add_black_24dp);
        } else if (spec.equals("alarmoff")) {
            itemInfo.setItemTitle(R.string.alarm_off_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_alarm_off_black_24dp);
        } else if (spec.equals("android")) {
            itemInfo.setItemTitle(R.string.android_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_android_black_24dp);
        } else if (spec.equals("announcement")) {
            itemInfo.setItemTitle(R.string.announcement_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_announcement_black_24dp);
        } else if (spec.equals("return")) {
            itemInfo.setItemTitle(R.string.return_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_assignment_return_black_24dp);
        } else if (spec.equals("backup")) {
            itemInfo.setItemTitle(R.string.backup_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_backup_black_24dp);
        } else if (spec.equals("book")) {
            itemInfo.setItemTitle(R.string.book_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_book_black_24dp);
        } else if (spec.equals("bookmark")) {
            itemInfo.setItemTitle(R.string.bookmark_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_bookmark_border_black_24dp);
        } else if (spec.equals("build")) {
            itemInfo.setItemTitle(R.string.build_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_build_black_24dp);
        } else if (spec.equals("cached")) {
            itemInfo.setItemTitle(R.string.cached_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_cached_black_24dp);
        } else if (spec.equals("camera")) {
            itemInfo.setItemTitle(R.string.camera_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_camera_enhance_black_24dp);
        } else if (spec.equals("travel")) {
            itemInfo.setItemTitle(R.string.travel_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_card_travel_black_24dp);
        } else if (spec.equals("history")) {
            itemInfo.setItemTitle(R.string.history_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_change_history_black_24dp);
        } else if (spec.equals("check")) {
            itemInfo.setItemTitle(R.string.check_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_check_circle_black_24dp);
        } else if (spec.equals("reader")) {
            itemInfo.setItemTitle(R.string.reader_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_chrome_reader_mode_black_24dp);
        } else if (spec.equals("code")) {
            itemInfo.setItemTitle(R.string.code_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_code_black_24dp);
        } else if (spec.equals("arrow")) {
            itemInfo.setItemTitle(R.string.arrow_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_compare_arrows_black_24dp);
        } else if (spec.equals("copyright")) {
            itemInfo.setItemTitle(R.string.copyright_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_copyright_black_24dp);
        } else if (spec.equals("credit")) {
            itemInfo.setItemTitle(R.string.credit_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_credit_card_black_24dp);
        } else if (spec.equals("dashboard")) {
            itemInfo.setItemTitle(R.string.dashboard_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_dashboard_black_24dp);
        } else if (spec.equals("date")) {
            itemInfo.setItemTitle(R.string.date_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_date_range_black_24dp);
        } else if (spec.equals("delete")) {
            itemInfo.setItemTitle(R.string.delete_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_delete_forever_black_24dp);
        } else if (spec.equals("description")) {
            itemInfo.setItemTitle(R.string.description_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_description_black_24dp);
        } else if (spec.equals("done")) {
            itemInfo.setItemTitle(R.string.done_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_done_all_black_24dp);
        } else if (spec.equals("event")) {
            itemInfo.setItemTitle(R.string.event_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_event_black_24dp);
        } else if (spec.equals("exit")) {
            itemInfo.setItemTitle(R.string.exit_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_exit_to_app_black_24dp);
        } else if (spec.equals("explore")) {
            itemInfo.setItemTitle(R.string.explore_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_explore_black_24dp);
        } else if (spec.equals("face")) {
            itemInfo.setItemTitle(R.string.face_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_face_black_24dp);
        } else if (spec.equals("favorite")) {
            itemInfo.setItemTitle(R.string.favorite_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_favorite_black_24dp);
        } else if (spec.equals("find")) {
            itemInfo.setItemTitle(R.string.find_item_title);
            itemInfo.setItemImgRes(R.drawable.ic_find_in_page_black_24dp);
        }
        return itemInfo;
    }

    @Override
    public void onBackPressed() {
        if (mRecyclerViewQuickEditor != null && mRecyclerViewQuickEditor.getVisibility() == View.VISIBLE) {
            hideEditor();
            return;
        }
        super.onBackPressed();
    }
}
