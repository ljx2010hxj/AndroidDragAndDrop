package com.nida.gridviewdraganddrop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.nida.gridviewdraganddrop.view.DragGridViewAdapter;
import com.nida.gridviewdraganddrop.view.DragItem;
import com.nida.gridviewdraganddrop.view.GridViewEditor;

import java.util.List;

public class GridViewActivity extends AppCompatActivity {
    private GridView mGridView;
    private DragGridViewAdapter mAdapter;
    private EditorData mEditorData;
    private GridViewEditor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_main);
        initData();
        initView();
    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.grid_view);
        mAdapter = new DragGridViewAdapter(this);


        mAdapter.setItemsList(mEditorData.loadStoredItemsList());
        mGridView.setAdapter(mAdapter);

        mEditor = (GridViewEditor) findViewById(R.id.grid_view_editor);

        TextView tv = (TextView) findViewById(R.id.grid_view_txt);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditor != null && mEditor.getVisibility() == View.GONE) {
                    showEditor();
                } else {
                    hideEditor();
                }
            }
        });
    }

    private void showEditor() {
        List<DragItem> aboveItemsList = mEditorData.loadStoredItemsList();
        List<DragItem> allItemsList = mEditorData.loadAllItemsList();
        allItemsList.removeAll(aboveItemsList);
        mEditor.setAboveDragGridViewList(aboveItemsList);
        mEditor.setBelowDragGridView(allItemsList);
        mGridView.setVisibility(View.GONE);
        mEditor.show();
    }

    private void hideEditor() {
        mEditor.hide();
        mAdapter.setItemsList(mEditorData.loadStoredItemsList());
        mGridView.setVisibility(View.VISIBLE);
    }

    private void initData() {
        mEditorData = EditorData.getInstance(this);
    }
}
