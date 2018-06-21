package com.example.android.scorekeeper.ui.activities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.scorekeeper.R;
import com.example.android.scorekeeper.customviews.CustomButton;
import com.example.android.scorekeeper.listeners.Callbacks;
import com.example.android.scorekeeper.ui.dialogs.MyAlertDialog;
import com.example.android.scorekeeper.utils.AppConstantStrings;
import com.example.android.scorekeeper.utils.AppKeys;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_team_a)
    TextView tvTeamA;
    @BindView(R.id.tv_team_a_score)
    TextView tvTeamAscore;
    @BindView(R.id.btn_team_a_add_three)
    CustomButton btnTeamAaddThree;
    @BindView(R.id.btn_team_a_add_two)
    CustomButton btnTeamAaddTwo;
    @BindView(R.id.btn_team_a_add_one)
    CustomButton btnTeamAaddOne;
    @BindView(R.id.tv_team_b)
    TextView tvTeamB;
    @BindView(R.id.tv_team_b_score)
    TextView tvTeamBscore;
    @BindView(R.id.btn_team_b_add_three)
    CustomButton btnTeamBaddThree;
    @BindView(R.id.btn_team_b_add_two)
    CustomButton btnTeamBaddTwo;
    @BindView(R.id.btn_team_b_add_one)
    CustomButton btnTeamBaddOne;
    @BindView(R.id.btn_result)
    CustomButton btnResult;
    @BindView(R.id.btn_reset)
    CustomButton btnReset;

    //Score of team A
    private int scoreTeamA = 0;
    //Score of team B
    private int scoreTeamB = 0;
    //Views / Controllers need to be set for common purpose
    private View[] views;
    //True if result dialog is currently being shown otherwise false
    private boolean result;
    //True if reset alert dialog is currently being shown otherwise false
    private boolean hasResetAlert;
    //MyAlertDialog
    private MyAlertDialog myAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Restoring values after activity gets re-created
        if (savedInstanceState != null) {
            scoreTeamA = savedInstanceState.getInt(AppKeys.INT_SCORE_TEAM_A);
            scoreTeamB = savedInstanceState.getInt(AppKeys.INT_SCORE_TEAM_B);
            result = savedInstanceState.getBoolean(AppKeys.BOOLEAN_RESULT);
            hasResetAlert = savedInstanceState.getBoolean(AppKeys.BOOLEAN_RESET_ALERT);
        }

        views = new View[]{btnResult, btnTeamAaddThree, btnTeamAaddTwo, btnTeamAaddOne, btnTeamBaddThree, btnTeamBaddTwo, btnTeamBaddOne};
        displayScore(tvTeamAscore, scoreTeamA);
        displayScore(tvTeamBscore, scoreTeamB);

        if (result) {
            showResult();
        }

        if (hasResetAlert){
            showResetAlert();
        }
    }

    /*
    * Saving state just before activity going to get destroyed and get re-created
    * */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(AppKeys.INT_SCORE_TEAM_A, scoreTeamA);
        outState.putInt(AppKeys.INT_SCORE_TEAM_B, scoreTeamB);
        outState.putBoolean(AppKeys.BOOLEAN_RESULT, result);
        outState.putBoolean(AppKeys.BOOLEAN_RESET_ALERT, hasResetAlert);
        if (myAlertDialog != null){
            myAlertDialog.dismiss();
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Displays the given score for given team
     */
    private void displayScore(TextView tv, int score) {
        tv.setText(String.valueOf(score));
    }

    /**
     * Displays tvResult
     */
    private void showResult() {
        //Dismissing last dialog if any already being shown to resolve the conflicts of stack
        if (myAlertDialog != null){
            myAlertDialog.dismiss();
        }
        disableUserInputs(views);
        myAlertDialog = new MyAlertDialog()
                .setTitle(getString(R.string.label_result))
                .setContentMsg(getResultMsg())
                .setBtnLeftText(getString(R.string.label_reset))
                .setBtnRightText(getString(R.string.label_continue))
                .setOnDialogBtnClick(new Callbacks.OnDialogBtnClick() {
                    @Override
                    public void onLeftBtnClick() {
                        showResetAlert();
                        result = false;
                    }

                    @Override
                    public void onRightBtnClick() {
                        dismissDialog(myAlertDialog);
                        result = false;
                        enableUserInputs();
                    }
                });
        myAlertDialog.show(getSupportFragmentManager(), AppConstantStrings.STR_DIALOG_RESULT);
        result = true;
    }

    /*
    * Shows alert dialog while end user is going to reset the game
    * */
    private void showResetAlert(){
        //Dismissing last dialog if any already being shown to resolve the conflicts of stack
        if (myAlertDialog != null){
            myAlertDialog.dismiss();
        }
        disableUserInputs(views);
        hasResetAlert = true;
        myAlertDialog = new MyAlertDialog()
                .setTitle(getString(R.string.label_alert))
                .setTitleColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
                .setContentMsg(getResetAlertMsg())
                .setBtnLeftText(getString(R.string.label_yes))
                .setBtnRightText(getString(R.string.label_no))
                .setOnDialogBtnClick(new Callbacks.OnDialogBtnClick() {
                    @Override
                    public void onLeftBtnClick() {
                        resetScores();
                        hasResetAlert = false;
                    }

                    @Override
                    public void onRightBtnClick() {
                        dismissDialog(myAlertDialog);
                        hasResetAlert = false;
                        enableUserInputs();
                    }
                });
        myAlertDialog.show(getSupportFragmentManager(), AppConstantStrings.STR_DIALOG_RESET_ALERT);
    }

    /*
    * Gives reset alert content message to display
    * */
    private String getResetAlertMsg(){
        return getString(R.string.label_are_you_sure_reset);
    }

    /*
    * Dismisses given dialogFragment
    * */
    public void dismissDialog(DialogFragment dialogFragment){
        if (dialogFragment != null){
            dialogFragment.dismiss();
        }
    }

    /*
    * Gives result content message to show in dialog fragment
    * */
    private String getResultMsg(){
        String contentMsg = "";
        if (scoreTeamA > scoreTeamB) {
            contentMsg = (getString(R.string.label_win).replace("#", getString(R.string.label_team_a)));
        } else if (scoreTeamB > scoreTeamA) {
            contentMsg = (getString(R.string.label_win).replace("#", getString(R.string.label_team_b)));
        } else {
            contentMsg = (getString(R.string.label_tie));
        }
        return contentMsg;
    }

    /**
     * Disables all user input / controllers
     */
    private void disableUserInputs(View[] views) {
        for (View view : views) {
            view.setEnabled(false);
        }
    }

    /**
     * Resets the score to 0 for both team to start it over again
     */
    public void resetScores() {
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayScore(tvTeamAscore, 0);
        displayScore(tvTeamBscore, 0);
        enableUserInputs();
        result = false;
    }

    /**
     * Enables all user input to start or continue the match
     */
    private void enableUserInputs() {
        for (View view : views) {
            view.setEnabled(true);
        }
    }

    /**
     * Increases the current score of respective team by given increment
     */
    public void addPoints(TextView tv, int score, int increment) {
        score = score + increment;
        displayScore(tv, score);
    }

    @OnClick({R.id.btn_team_a_add_three, R.id.btn_team_a_add_two, R.id.btn_team_a_add_one, R.id.btn_team_b_add_three, R.id.btn_team_b_add_two, R.id.btn_team_b_add_one, R.id.btn_result, R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_team_a_add_three:
                addPoints(tvTeamAscore, scoreTeamA, 3);
                scoreTeamA += 3;
                break;
            case R.id.btn_team_a_add_two:
                addPoints(tvTeamAscore, scoreTeamA, 2);
                scoreTeamA += 2;
                break;
            case R.id.btn_team_a_add_one:
                addPoints(tvTeamAscore, scoreTeamA, 1);
                scoreTeamA += 1;
                break;
            case R.id.btn_team_b_add_three:
                addPoints(tvTeamBscore, scoreTeamB, 3);
                scoreTeamB += 3;
                break;
            case R.id.btn_team_b_add_two:
                addPoints(tvTeamBscore, scoreTeamB, 2);
                scoreTeamB += 2;
                break;
            case R.id.btn_team_b_add_one:
                addPoints(tvTeamBscore, scoreTeamB, 1);
                scoreTeamB += 1;
                break;
            case R.id.btn_result:
                showResult();
                break;
            case R.id.btn_reset:
                showResetAlert();
                break;
        }
    }
}
