package com.example.android.scorekeeper.ui.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.android.scorekeeper.R;
import com.example.android.scorekeeper.customviews.CustomButton;
import com.example.android.scorekeeper.listeners.Callbacks;
import com.example.android.scorekeeper.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyAlertDialog extends DialogFragment {
    @BindView(R.id.btn_tv_title)
    CustomButton btnTvTitle;
    @BindView(R.id.btn_tv_content_msg)
    CustomButton btnTvContentMsg;
    @BindView(R.id.btn_left)
    CustomButton btnLeft;
    @BindView(R.id.btn_right)
    CustomButton btnRight;
    Unbinder unbinder;

    //Interface callback to notify which option end user has selected from this dialog
    private Callbacks.OnDialogBtnClick onDialogBtnClick;
    //title to be set as the title of this dialog
    private String title;
    //content to be set for this dialog
    private String contentMsg;
    //text on left button to be set
    private String btnLeftText;
    //text on right button to be set
    private String btnRightText;

    /**
     * Gives the {@link #titleColor} to be set as {@link android.support.v7.widget.AppCompatTextView#setTextColor(int)}
     *
     * @since 1.0
     */
    public int getTitleColor() {
        return titleColor;
    }

    /**
     * Sets the {@link #titleColor} as {@link android.support.v7.widget.AppCompatTextView#setTextColor(int)} on {@link #btnTvTitle}
     *
     * @since 1.0
     */
    public MyAlertDialog setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    //color to set for alert dialog title
    private int titleColor;

    /**
     * Gives the {@link #title} to be set as {@link android.support.v7.widget.AppCompatTextView#setText(CharSequence)} on {@link #btnTvTitle}
     *
     * @since 1.0
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the {@link #title} as {@link android.support.v7.widget.AppCompatTextView#setText(CharSequence)} on {@link #btnTvTitle}
     *
     * @since 1.0
     */
    public MyAlertDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Gives the {@link #contentMsg} to be set as {@link android.support.v7.widget.AppCompatTextView#setText(CharSequence)} on {@link #btnTvContentMsg}
     *
     * @since 1.0
     */
    public String getContentMsg() {
        return contentMsg;
    }

    /**
     * Sets the {@link #contentMsg} as {@link android.support.v7.widget.AppCompatTextView#setText(CharSequence)} on {@link #btnTvContentMsg}
     *
     * @since 1.0
     */
    public MyAlertDialog setContentMsg(String contentMsg) {
        this.contentMsg = contentMsg;
        return this;
    }

    /**
     * Gives the {@link #btnLeftText} to be set as {@link android.support.v7.widget.AppCompatTextView#setText(CharSequence)} on {@link #btnLeft}
     *
     * @since 1.0
     */
    public String getBtnLeftText() {
        return btnLeftText;
    }

    /**
     * Sets the {@link #btnLeftText} as {@link android.support.v7.widget.AppCompatTextView#setText(CharSequence)} on {@link #btnLeft}
     *
     * @since 1.0
     */
    public MyAlertDialog setBtnLeftText(String btnLeftText) {
        this.btnLeftText = btnLeftText;
        return this;
    }

    /**
     * Gives the {@link #btnRightText} to be set as {@link android.support.v7.widget.AppCompatTextView#setText(CharSequence)} on {@link #btnRight}
     *
     * @since 1.0
     */
    public String getBtnRightText() {
        return btnRightText;
    }

    /**
     * Sets the {@link #btnRightText} as {@link android.support.v7.widget.AppCompatTextView#setText(CharSequence)} on {@link #btnRight}
     *
     * @since 1.0
     */
    public MyAlertDialog setBtnRightText(String btnRightText) {
        this.btnRightText = btnRightText;
        return this;
    }

    /**
     * Sets the {@link Callbacks.OnDialogBtnClick} as interface callback on this dialog fragment
     *
     * @since 1.0
     */
    public MyAlertDialog setOnDialogBtnClick(Callbacks.OnDialogBtnClick onDialogBtnClick) {
        this.onDialogBtnClick = onDialogBtnClick;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_alert, container);
        unbinder = ButterKnife.bind(this, view);
        setValues();
        getDialog().setCanceledOnTouchOutside(false);
        return view;
    }

    /**
     * Sets the values on views
     *
     * @since 1.0
     */
    private void setValues() {
        if (getTitleColor() != 0) {
            btnTvTitle.setBtnTxtColor(getTitleColor());
        }
        btnTvTitle.setText(getTitle());
        btnLeft.setText(getBtnLeftText());
        btnRight.setText(getBtnRightText());
        btnTvContentMsg.setText(getContentMsg());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getShowsDialog()) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.60f; //Existed background host screen will be dimmed by given float value. Hight the float value, higher it will be dimmed.
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND; //Requires this flag to dim host
            window.setAttributes(layoutParams);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //To remove default rectangle background
            window.setLayout((int) (ScreenUtils.getScreenWidthHeight(getActivity())[0] * 0.90), WindowManager.LayoutParams.WRAP_CONTENT); //Sets width and height of this dialog as per given value
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                if (onDialogBtnClick != null) {
                    onDialogBtnClick.onLeftBtnClick();
                }
                dismiss();
                break;
            case R.id.btn_right:
                if (onDialogBtnClick != null) {
                    onDialogBtnClick.onRightBtnClick();
                }
                dismiss();
                break;
        }
    }
}
