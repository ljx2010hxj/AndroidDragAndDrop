package com.nida.recycleviewdraganddrop.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.nida.recycleviewdraganddrop.Constant;
import com.nida.recycleviewdraganddrop.R;
import com.nida.recycleviewdraganddrop.RecycleViewEditorAdapter;
import com.nida.recycleviewdraganddrop.SPManager;
import com.nida.recycleviewdraganddrop.bean.QuickItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Time:2019/7/2 16:16
 * Author:Kerwin Li
 * Description:
 **/
public class RecycleViewQuickEditor extends LinearLayout {
    private RecyclerView mRecyclerView;
    private RecycleViewEditorAdapter mAdapter;
    private boolean isShown;
    private Context mContext;

    public RecycleViewQuickEditor(Context context) {
        this(context, null);
    }

    public RecycleViewQuickEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecycleViewQuickEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.editor_layout, this);
        setOrientation(VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mAdapter = new RecycleViewEditorAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.getItemTouchHelper().attachToRecyclerView(mRecyclerView);
        GridLayoutManager layout = new GridLayoutManager(getContext(), Constant.COL_NUM);
        layout.setSpanSizeLookup(mAdapter.getSizeLookup());
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.addItemDecoration(mAdapter.getItemDecoration());
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setMoveDuration(RecycleViewEditorAdapter.MOVE_DURATION);
        mRecyclerView.setItemAnimator(animator);
    }

    public void show() {
        if (!isShown) {
            isShown = true;
            setTileSpecs();
            setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
        if (isShown) {
            isShown = false;
            save();
            setVisibility(GONE);
        }
    }

    private void setTileSpecs() {
        List<String> specStrList = new ArrayList<>();
        List<String> storedItemsSpec = getStoredItemsList();
        List<String> allItemsSpec = getAllItemsList();
        specStrList.addAll(storedItemsSpec);
        specStrList.add(null);
        allItemsSpec.removeAll(storedItemsSpec);
        specStrList.addAll(allItemsSpec);
        mAdapter.setTileSpecs(getAllQuickItemInfo(specStrList));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void save() {
        mAdapter.saveSpecs();
    }

    private QuickItemInfo getQuickItemInfoBySpec(String spec) {
        if (spec == null) {
            return null;
        }
        final QuickItemInfo itemInfo = new QuickItemInfo();
        if (spec.equals("rotation")) {
            itemInfo.spec = "rotation";
            itemInfo.titleResId = R.string.rotation_item_title;
            itemInfo.imgResId = R.drawable.ic_3d_rotation_black_24dp;
        } else if (spec.equals("accessibility")) {
            itemInfo.spec = "accessibility";
            itemInfo.titleResId = R.string.accessibility_item_title;
            itemInfo.imgResId = R.drawable.ic_accessibility_black_24dp;
        } else if (spec.equals("accountbalance")) {
            itemInfo.spec = "accountbalance";
            itemInfo.titleResId = R.string.account_balance_item_title;
            itemInfo.imgResId = R.drawable.ic_account_balance_black_24dp;
        } else if (spec.equals("shopping")) {
            itemInfo.spec = "shopping";
            itemInfo.titleResId = R.string.shopping_item_title;
            itemInfo.imgResId = R.drawable.ic_add_shopping_cart_black_24dp;
        } else if (spec.equals("alarmadd")) {
            itemInfo.spec = "alarmadd";
            itemInfo.titleResId = R.string.alarm_add_item_title;
            itemInfo.imgResId = R.drawable.ic_alarm_add_black_24dp;
        } else if (spec.equals("alarmoff")) {
            itemInfo.spec = "alarmoff";
            itemInfo.titleResId = R.string.alarm_off_item_title;
            itemInfo.imgResId = R.drawable.ic_alarm_off_black_24dp;
        } else if (spec.equals("android")) {
            itemInfo.spec = "android";
            itemInfo.titleResId = R.string.android_item_title;
            itemInfo.imgResId = R.drawable.ic_android_black_24dp;
        } else if (spec.equals("announcement")) {
            itemInfo.spec = "announcement";
            itemInfo.titleResId = R.string.announcement_item_title;
            itemInfo.imgResId = R.drawable.ic_announcement_black_24dp;
        } else if (spec.equals("return")) {
            itemInfo.spec = "return";
            itemInfo.titleResId = R.string.return_item_title;
            itemInfo.imgResId = R.drawable.ic_assignment_return_black_24dp;
        } else if (spec.equals("backup")) {
            itemInfo.spec = "backup";
            itemInfo.titleResId = R.string.backup_item_title;
            itemInfo.imgResId = R.drawable.ic_backup_black_24dp;
        } else if (spec.equals("book")) {
            itemInfo.spec = "book";
            itemInfo.titleResId = R.string.book_item_title;
            itemInfo.imgResId = R.drawable.ic_book_black_24dp;
        } else if (spec.equals("bookmark")) {
            itemInfo.spec = "bookmark";
            itemInfo.titleResId = R.string.bookmark_item_title;
            itemInfo.imgResId = R.drawable.ic_bookmark_border_black_24dp;
        } else if (spec.equals("build")) {
            itemInfo.spec = "build";
            itemInfo.titleResId = R.string.build_item_title;
            itemInfo.imgResId = R.drawable.ic_build_black_24dp;
        } else if (spec.equals("cached")) {
            itemInfo.spec = "cached";
            itemInfo.titleResId = R.string.cached_item_title;
            itemInfo.imgResId = R.drawable.ic_cached_black_24dp;
        } else if (spec.equals("camera")) {
            itemInfo.spec = "camera";
            itemInfo.titleResId = R.string.camera_item_title;
            itemInfo.imgResId = R.drawable.ic_camera_enhance_black_24dp;
        } else if (spec.equals("travel")) {
            itemInfo.spec = "travel";
            itemInfo.titleResId = R.string.travel_item_title;
            itemInfo.imgResId = R.drawable.ic_card_travel_black_24dp;
        } else if (spec.equals("history")) {
            itemInfo.spec = "history";
            itemInfo.titleResId = R.string.history_item_title;
            itemInfo.imgResId = R.drawable.ic_change_history_black_24dp;
        } else if (spec.equals("check")) {
            itemInfo.spec = "check";
            itemInfo.titleResId = R.string.check_item_title;
            itemInfo.imgResId = R.drawable.ic_check_circle_black_24dp;
        } else if (spec.equals("reader")) {
            itemInfo.spec = "reader";
            itemInfo.titleResId = R.string.reader_item_title;
            itemInfo.imgResId = R.drawable.ic_chrome_reader_mode_black_24dp;
        } else if (spec.equals("code")) {
            itemInfo.spec = "code";
            itemInfo.titleResId = R.string.code_item_title;
            itemInfo.imgResId = R.drawable.ic_code_black_24dp;
        } else if (spec.equals("arrow")) {
            itemInfo.spec = "arrow";
            itemInfo.titleResId = R.string.arrow_item_title;
            itemInfo.imgResId = R.drawable.ic_compare_arrows_black_24dp;
        } else if (spec.equals("copyright")) {
            itemInfo.spec = "copyright";
            itemInfo.titleResId = R.string.copyright_item_title;
            itemInfo.imgResId = R.drawable.ic_copyright_black_24dp;
        } else if (spec.equals("credit")) {
            itemInfo.spec = "credit";
            itemInfo.titleResId = R.string.credit_item_title;
            itemInfo.imgResId = R.drawable.ic_credit_card_black_24dp;
        } else if (spec.equals("dashboard")) {
            itemInfo.spec = "dashboard";
            itemInfo.titleResId = R.string.dashboard_item_title;
            itemInfo.imgResId = R.drawable.ic_dashboard_black_24dp;
        } else if (spec.equals("date")) {
            itemInfo.spec = "date";
            itemInfo.titleResId = R.string.date_item_title;
            itemInfo.imgResId = R.drawable.ic_date_range_black_24dp;
        } else if (spec.equals("delete")) {
            itemInfo.spec = "delete";
            itemInfo.titleResId = R.string.delete_item_title;
            itemInfo.imgResId = R.drawable.ic_delete_forever_black_24dp;
        } else if (spec.equals("description")) {
            itemInfo.spec = "description";
            itemInfo.titleResId = R.string.description_item_title;
            itemInfo.imgResId = R.drawable.ic_description_black_24dp;
        } else if (spec.equals("done")) {
            itemInfo.spec = "done";
            itemInfo.titleResId = R.string.done_item_title;
            itemInfo.imgResId = R.drawable.ic_done_all_black_24dp;
        } else if (spec.equals("event")) {
            itemInfo.spec = "event";
            itemInfo.titleResId = R.string.event_item_title;
            itemInfo.imgResId = R.drawable.ic_event_black_24dp;
        } else if (spec.equals("exit")) {
            itemInfo.spec = "exit";
            itemInfo.titleResId = R.string.exit_item_title;
            itemInfo.imgResId = R.drawable.ic_exit_to_app_black_24dp;
        } else if (spec.equals("explore")) {
            itemInfo.spec = "explore";
            itemInfo.titleResId = R.string.explore_item_title;
            itemInfo.imgResId = R.drawable.ic_explore_black_24dp;
        } else if (spec.equals("face")) {
            itemInfo.spec = "face";
            itemInfo.titleResId = R.string.face_item_title;
            itemInfo.imgResId = R.drawable.ic_face_black_24dp;
        } else if (spec.equals("favorite")) {
            itemInfo.spec = "favorite";
            itemInfo.titleResId = R.string.favorite_item_title;
            itemInfo.imgResId = R.drawable.ic_favorite_black_24dp;
        } else if (spec.equals("find")) {
            itemInfo.spec = "find";
            itemInfo.titleResId = R.string.find_item_title;
            itemInfo.imgResId = R.drawable.ic_find_in_page_black_24dp;
        }
        return itemInfo;
    }

    private List<String> getAllItemsList() {
        List<String> allItemsList = new ArrayList<>();
        String allItemsStr = mContext.getString(R.string.all_items_str);
        for (String spec : allItemsStr.split(",")) {
            allItemsList.add(spec);
        }
        return allItemsList;
    }

    public List<String> getStoredItemsList() {
        List<String> storedItemsList = new ArrayList<>();
        String defItemsStr = mContext.getString(R.string.def_items_str);
        String storedStr = SPManager.getInstance().getStringValue(Constant.CON_ITEMS);
        if (storedStr == null || storedStr.trim().length() <= 0 || storedStr.equals("default")) {
            storedStr = defItemsStr;
        }
        for (String spec : storedStr.split(",")) {
            storedItemsList.add(spec);
        }
        return storedItemsList;
    }

    private List<QuickItemInfo> getAllQuickItemInfo(List<String> stringList) {
        List<QuickItemInfo> infoList = new ArrayList<>();
        for (String spec : stringList) {
            infoList.add(getQuickItemInfoBySpec(spec));
        }
        return infoList;
    }

}
