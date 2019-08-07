package com.nida.gridviewdraganddrop;

import android.content.Context;

import com.nida.gridviewdraganddrop.view.DragItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Time:2019/7/3 17:22
 * Author:Kerwin Li
 * Description:
 **/
public class EditorData {
    private Context mContext;
    private static EditorData mInstance;
    public static final String STORED_ITEM_STR = "stored_item_str";

    private EditorData(Context context) {
        mContext = context;
    }

    public static EditorData getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new EditorData(context.getApplicationContext());
        }
        return mInstance;
    }

    public List<DragItem> loadStoredItemsList() {
        List<DragItem> storedItemsList = new ArrayList<>();
        String storedItemsStr = SPManager.getInstance().getStringValue(STORED_ITEM_STR);
        String defItemsStr = mContext.getString(R.string.def_items_str);
        if (storedItemsStr == null || storedItemsStr.trim().length() <= 0 || storedItemsStr.equals("default")) {
            storedItemsStr = defItemsStr;
        }
        for (String spec : storedItemsStr.split(",")) {
            storedItemsList.add(createDragItemBySpec(spec));
        }
        return storedItemsList;
    }

    public List<DragItem> loadAllItemsList() {
        List<DragItem> allList = new ArrayList<>();
        String allItemsStr = mContext.getString(R.string.all_items_str);
        for (String spec : allItemsStr.split(",")) {
            allList.add(createDragItemBySpec(spec));
        }
        return allList;
    }

    private DragItem createDragItemBySpec(String spec) {
        final DragItem item = new DragItem();
        if (spec.equals("rotation")) {
            item.spec = "rotation";
            item.titleResId = R.string.rotation_item_title;
            item.imgResId = R.drawable.ic_3d_rotation_black_24dp;
        } else if (spec.equals("accessibility")) {
            item.spec = "accessibility";
            item.titleResId = R.string.accessibility_item_title;
            item.imgResId = R.drawable.ic_accessibility_black_24dp;
        } else if (spec.equals("accountbalance")) {
            item.spec = "accountbalance";
            item.titleResId = R.string.account_balance_item_title;
            item.imgResId = R.drawable.ic_account_balance_black_24dp;
        } else if (spec.equals("shopping")) {
            item.spec = "shopping";
            item.titleResId = R.string.shopping_item_title;
            item.imgResId = R.drawable.ic_add_shopping_cart_black_24dp;
        } else if (spec.equals("alarmadd")) {
            item.spec = "alarmadd";
            item.titleResId = R.string.alarm_add_item_title;
            item.imgResId = R.drawable.ic_alarm_add_black_24dp;
        } else if (spec.equals("alarmoff")) {
            item.spec = "alarmoff";
            item.titleResId = R.string.alarm_off_item_title;
            item.imgResId = R.drawable.ic_alarm_off_black_24dp;
        } else if (spec.equals("android")) {
            item.spec = "android";
            item.titleResId = R.string.android_item_title;
            item.imgResId = R.drawable.ic_android_black_24dp;
        } else if (spec.equals("announcement")) {
            item.spec = "announcement";
            item.titleResId = R.string.announcement_item_title;
            item.imgResId = R.drawable.ic_announcement_black_24dp;
        } else if (spec.equals("return")) {
            item.spec = "return";
            item.titleResId = R.string.return_item_title;
            item.imgResId = R.drawable.ic_assignment_return_black_24dp;
        } else if (spec.equals("backup")) {
            item.spec = "backup";
            item.titleResId = R.string.backup_item_title;
            item.imgResId = R.drawable.ic_backup_black_24dp;
        } else if (spec.equals("book")) {
            item.spec = "book";
            item.titleResId = R.string.book_item_title;
            item.imgResId = R.drawable.ic_book_black_24dp;
        } else if (spec.equals("bookmark")) {
            item.spec = "bookmark";
            item.titleResId = R.string.bookmark_item_title;
            item.imgResId = R.drawable.ic_bookmark_border_black_24dp;
        } else if (spec.equals("build")) {
            item.spec = "build";
            item.titleResId = R.string.build_item_title;
            item.imgResId = R.drawable.ic_build_black_24dp;
        } else if (spec.equals("cached")) {
            item.spec = "cached";
            item.titleResId = R.string.cached_item_title;
            item.imgResId = R.drawable.ic_cached_black_24dp;
        } else if (spec.equals("camera")) {
            item.spec = "camera";
            item.titleResId = R.string.camera_item_title;
            item.imgResId = R.drawable.ic_camera_enhance_black_24dp;
        } else if (spec.equals("travel")) {
            item.spec = "travel";
            item.titleResId = R.string.travel_item_title;
            item.imgResId = R.drawable.ic_card_travel_black_24dp;
        } else if (spec.equals("history")) {
            item.spec = "history";
            item.titleResId = R.string.history_item_title;
            item.imgResId = R.drawable.ic_change_history_black_24dp;
        } else if (spec.equals("check")) {
            item.spec = "check";
            item.titleResId = R.string.check_item_title;
            item.imgResId = R.drawable.ic_check_circle_black_24dp;
        } else if (spec.equals("reader")) {
            item.spec = "reader";
            item.titleResId = R.string.reader_item_title;
            item.imgResId = R.drawable.ic_chrome_reader_mode_black_24dp;
        } else if (spec.equals("code")) {
            item.spec = "code";
            item.titleResId = R.string.code_item_title;
            item.imgResId = R.drawable.ic_code_black_24dp;
        } else if (spec.equals("arrow")) {
            item.spec = "arrow";
            item.titleResId = R.string.arrow_item_title;
            item.imgResId = R.drawable.ic_compare_arrows_black_24dp;
        } else if (spec.equals("copyright")) {
            item.spec = "copyright";
            item.titleResId = R.string.copyright_item_title;
            item.imgResId = R.drawable.ic_copyright_black_24dp;
        } else if (spec.equals("credit")) {
            item.spec = "credit";
            item.titleResId = R.string.credit_item_title;
            item.imgResId = R.drawable.ic_credit_card_black_24dp;
        } else if (spec.equals("dashboard")) {
            item.spec = "dashboard";
            item.titleResId = R.string.dashboard_item_title;
            item.imgResId = R.drawable.ic_dashboard_black_24dp;
        } else if (spec.equals("date")) {
            item.spec = "date";
            item.titleResId = R.string.date_item_title;
            item.imgResId = R.drawable.ic_date_range_black_24dp;
        } else if (spec.equals("delete")) {
            item.spec = "delete";
            item.titleResId = R.string.delete_item_title;
            item.imgResId = R.drawable.ic_delete_forever_black_24dp;
        } else if (spec.equals("description")) {
            item.spec = "description";
            item.titleResId = R.string.description_item_title;
            item.imgResId = R.drawable.ic_description_black_24dp;
        } else if (spec.equals("done")) {
            item.spec = "done";
            item.titleResId = R.string.done_item_title;
            item.imgResId = R.drawable.ic_done_all_black_24dp;
        } else if (spec.equals("event")) {
            item.spec = "event";
            item.titleResId = R.string.event_item_title;
            item.imgResId = R.drawable.ic_event_black_24dp;
        } else if (spec.equals("exit")) {
            item.spec = "exit";
            item.titleResId = R.string.exit_item_title;
            item.imgResId = R.drawable.ic_exit_to_app_black_24dp;
        } else if (spec.equals("explore")) {
            item.spec = "explore";
            item.titleResId = R.string.explore_item_title;
            item.imgResId = R.drawable.ic_explore_black_24dp;
        } else if (spec.equals("face")) {
            item.spec = "face";
            item.titleResId = R.string.face_item_title;
            item.imgResId = R.drawable.ic_face_black_24dp;
        } else if (spec.equals("favorite")) {
            item.spec = "favorite";
            item.titleResId = R.string.favorite_item_title;
            item.imgResId = R.drawable.ic_favorite_black_24dp;
        } else if (spec.equals("find")) {
            item.spec = "find";
            item.titleResId = R.string.find_item_title;
            item.imgResId = R.drawable.ic_find_in_page_black_24dp;
        }
        return item;
    }

}
