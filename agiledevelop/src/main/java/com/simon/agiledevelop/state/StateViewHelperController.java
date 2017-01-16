package com.simon.agiledevelop.state;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simon.agiledevelop.R;
import com.simon.agiledevelop.state.loading.Loading;

/**
 * describe: Controller class for state view
 *
 * @author Simon Han
 * @date 2015.10.25
 * @email hanzx1024@gmail.com
 */

public class StateViewHelperController {
    private StateViewHelper helper;

    public StateViewHelperController(View view) {
        this(new StateCoverHeplerImpl(view));
    }

    public StateViewHelperController(StateViewHelper helper) {
        super();
        this.helper = helper;
    }

    public void showStateNetworkError(View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        textView.setText(R.string.state_network_exception);

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.state_net_error);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        } else {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        helper.showLayout(layout);
    }

    public void showStateError(String errorMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!TextUtils.isEmpty(errorMsg)) {
            textView.setText(errorMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string
                    .state_error_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.state_error);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        } else {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        helper.showLayout(layout);
    }

    public void showStateEmpty(String emptyMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string
                    .state_empty_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.state_empty);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        } else {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        helper.showLayout(layout);
    }

    public void showStateLoading(String msg) {
        View layout = helper.inflate(R.layout.loading);
        Loading loading = (Loading) layout.findViewById(R.id.loading_progress);
//        loading.start();
        if (!TextUtils.isEmpty(msg)) {
            TextView textView = (TextView) layout.findViewById(R.id.loading_msg);
            textView.setText(msg);
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        helper.showLayout(layout);
    }

    public void restore() {
        helper.restoreView();
    }
}
