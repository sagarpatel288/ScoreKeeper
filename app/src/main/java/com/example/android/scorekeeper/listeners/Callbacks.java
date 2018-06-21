package com.example.android.scorekeeper.listeners;

import com.example.android.scorekeeper.ui.dialogs.MyAlertDialog;

public abstract class Callbacks {
    /**
     * Interface callback from {@link MyAlertDialog} to host
     * <p>
     * Gives callback to host for whether {@link MyAlertDialog#btnLeft} or {@link MyAlertDialog#btnRight} has clicked
     * <p>
     * Being implemented by {@link com.example.android.scorekeeper.ui.activities.MainActivity}
     *
     * @since 1.0
     */
    public interface OnDialogBtnClick {
        void onLeftBtnClick();

        void onRightBtnClick();
    }
}
