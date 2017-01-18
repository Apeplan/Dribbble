package com.simon.dribbble.ui.shots;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.simon.agiledevelop.mvpframe.BaseActivity;
import com.simon.agiledevelop.mvpframe.Presenter;
import com.simon.dribbble.R;
import com.simon.dribbble.widget.richeditor.RichEditor;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/9 11:02
 */

public class RichEditorActivity extends BaseActivity {

    private int[] top = {R.drawable.undo, R.drawable.redo, R.drawable.bold, R.drawable.italic, R
            .drawable.subscript, R.drawable.superscript, R.drawable.strikethrough, R.drawable
            .underline, R.drawable.h1, R.drawable.h2, R.drawable.h3, R.drawable.h4, R.drawable
            .h5, R.drawable.h6, R.drawable.txt_color, R.drawable.bg_color, R.drawable.indent, R
            .drawable.outdent, R.drawable.justify_left, R.drawable.justify_center, R.drawable
            .justify_right, R.drawable.bullets, R.drawable.numbers, R.drawable.blockquote, R
            .drawable.insert_image, R.drawable.insert_link};

    private RecyclerView mTopOption;
    private RichEditor mEditor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_richeditor;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected View getLoadingView() {
        return null;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setCommonBackToolBack(toolbar, "编辑评论");

        mTopOption = (RecyclerView) findViewById(R.id.rlv_top_option);
        mEditor = (RichEditor) findViewById(R.id.editor);

        mTopOption.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));

    }

    @Override
    protected void initEventAndData() {

        mTopOption.setAdapter(new TopOptionAdapter());

        mEditor.setEditorHeight(1000);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.RED);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        mEditor.setBackgroundResource(R.drawable.bg);
        //    mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("Insert text here...");

    }

    class TopOptionAdapter extends RecyclerView.Adapter<TopOptionAdapter.OptionHolder> {

        @Override
        public OptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            View v = LayoutInflater.from(context).inflate(R.layout.item_rich_option, parent, false);
            return new OptionHolder(v);
        }

        @Override
        public void onBindViewHolder(OptionHolder holder, int position) {
            holder.mOption_icon.setImageResource(top[position]);
        }

        @Override
        public int getItemCount() {
            return top.length;
        }

        class OptionHolder extends RecyclerView.ViewHolder {

            private final ImageView mOption_icon;

            public OptionHolder(View itemView) {
                super(itemView);
                mOption_icon = (ImageView) itemView.findViewById(R.id.imv_option_icon);
            }
        }
    }
}
