package com.huynhngoctai.ttcm_app_altp.view.fragment;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huynhngoctai.ttcm_app_altp.App;
import com.huynhngoctai.ttcm_app_altp.CommonUtils;
import com.huynhngoctai.ttcm_app_altp.Constants;
import com.huynhngoctai.ttcm_app_altp.MediaManager;
import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.database.entities.Question;
import com.huynhngoctai.ttcm_app_altp.databinding.FragmentPlayGameBinding;
import com.huynhngoctai.ttcm_app_altp.view.OnDialogListener;
import com.huynhngoctai.ttcm_app_altp.view.dialog.ConfirmSelectAnsDialog;
import com.huynhngoctai.ttcm_app_altp.view.dialog.HelpAskHereDialog;
import com.huynhngoctai.ttcm_app_altp.view.dialog.HelpCallDialog;
import com.huynhngoctai.ttcm_app_altp.view.dialog.HelpPercentDialog;
import com.huynhngoctai.ttcm_app_altp.view.dialog.NoticeDialog;
import com.huynhngoctai.ttcm_app_altp.view.dialog.SaveScoreDialog;
import com.huynhngoctai.ttcm_app_altp.view.dialog.SettingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayGameFragment extends BaseFragment<FragmentPlayGameBinding> implements OnDialogListener {
    public static final String TAG = PlayGameFragment.class.getName();
    public List<Integer> listAnswerHidden = new ArrayList<>(); // To store List Answer Hidden
    public boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING); //Get state music ? true or false
    public boolean stateOfConfirm = CommonUtils.getInstance().getPrefDefaultTrue(ConfirmSelectAnsDialog.STATE_CONFIRM); //get state confirm ? true or false
    public List<Question> questionList;
    private NoticeDialog noticeTimeOutDialog;
    private NoticeDialog noticeConfirmSelectDialog;
    private NoticeDialog noticeQuitGameDialog;
    private ConfirmSelectAnsDialog confirmSettingDialog;
    private SaveScoreDialog saveScoreDialog;
    private Question question;
    private CountDownTimer countDownTimer;
    private long remainingTime; // Lưu thời gian còn lại hiện tại
    private int level;
    public boolean isBack = false;
    private boolean isUseFifty = true;
    private boolean isUseCall = true;
    private boolean isUseHere = true;
    private boolean isUsePercent = true;


    @Override
    protected FragmentPlayGameBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentPlayGameBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        addEvents();
        initQuestion();
        showMilestone(level);
    }

    private void initQuestion() {
        new Thread(() -> {
            questionList = App.getInstance().getDatabase().getQuestionDAO().get15QuestionsByLevel();
            requireActivity().runOnUiThread(() -> showQuestion(level));
        }).start();
    }

    private void addEvents() {
        binding.ivHome.setOnClickListener(this);
        binding.ivFifty.setOnClickListener(this);
        binding.ivSettingGame.setOnClickListener(this);
        binding.ivHere.setOnClickListener(this);
        binding.ivPercent.setOnClickListener(this);
        binding.ivTelephone.setOnClickListener(this);
        binding.answerA.setOnClickListener(this);
        binding.answerB.setOnClickListener(this);
        binding.answerC.setOnClickListener(this);
        binding.answerD.setOnClickListener(this);
    }

    @Override
    protected void clickView(View v) {
        super.clickView(v);
        if (v.getId() == R.id.iv_home) {
            showBackMainDialog();
        } else if (v.getId() == R.id.answer_a) {
            handleClickAnswerA();
        } else if (v.getId() == R.id.answer_b) {
            handleClickAnswerB();
        } else if (v.getId() == R.id.answer_c) {
            handleClickAnswerC();
        } else if (v.getId() == R.id.answer_d) {
            handleClickAnswerD();
        } else if (v.getId() == R.id.iv_setting_game) {
            showSettingDialog();
        } else if (v.getId() == R.id.iv_fifty) {
            handleFiftyHelp();
        } else if (v.getId() == R.id.iv_telephone) {
            handleCallHelp();
        } else if (v.getId() == R.id.iv_percent) {
            handlePercentHelp();
        } else if (v.getId() == R.id.iv_here) {
            handleAskHereHelp();
        }
    }

    private void handleAskHereHelp() {
        pauseCountDownTimer();
        isUseHere = false;
        setViewsEnabled(false);

        if (!stateOfMusic) {
            showDialogAskHere();
        } else {
            MediaManager.getInstance().playSoundGame(R.raw.ping,
                    mp -> MediaManager.getInstance().playSoundGame(R.raw.sound_chon_tu_van, mp1 -> showDialogAskHere()));
        }
    }

    private void showDialogAskHere() {
        HelpAskHereDialog helpAskHereDialog = new HelpAskHereDialog(context);
        helpAskHereDialog.setCancelable(false);
        helpAskHereDialog.showAskHereAnswer(question.answerTrue);
        helpAskHereDialog.setOnDialogListener(this);
        helpAskHereDialog.show();

        binding.ivHere.setImageResource(R.drawable.ic_here2);
    }

    private void handleCallHelp() {
        pauseCountDownTimer();
        isUseCall = false;
        setViewsEnabled(false);

        if (!stateOfMusic) {
            showDialogCall();
        } else {
            MediaManager.getInstance().playSoundGame(R.raw.ping,
                    mp -> MediaManager.getInstance().playSoundGame(R.raw.sound_goi_dien,
                            mp1 -> MediaManager.getInstance().playSoundGame(R.raw.connec_call,
                                    mp11 -> showDialogCall())));
        }
    }

    private void showDialogCall() {
        HelpCallDialog callDialog = new HelpCallDialog(context);
        callDialog.getAnswerCall(question.answerTrue);
        callDialog.setCancelable(false);
        callDialog.show();
        callDialog.setDiaLogListener(this);

        binding.ivTelephone.setImageResource(R.drawable.icon_telephone2);
    }

    private void handlePercentHelp() {
        pauseCountDownTimer();
        isUsePercent = false;
        setViewsEnabled(false);

        if (!stateOfMusic) {
            showDialogPercent();
        } else {
            MediaManager.getInstance().playSoundGame(R.raw.ping,
                    mp -> MediaManager.getInstance().playSoundGame(R.raw.sound_chon_y_kien,
                            mp1 -> MediaManager.getInstance().playSoundGame(R.raw.help_ask_01, mp11 -> {
                                showDialogPercent();
                                musicId(R.raw.help_ask_02);
                            })));
        }
    }

    private void showDialogPercent() {
        HelpPercentDialog percentDialog = new HelpPercentDialog(context);
        percentDialog.updateChartData(question.answerTrue, listAnswerHidden);
        percentDialog.setCancelable(false);
        percentDialog.show();
        percentDialog.setDiaLogListener(this);

        binding.ivPercent.setImageResource(R.drawable.ic_percent2);
    }

    private void handleFiftyHelp() {
        pauseCountDownTimer();
        isUseFifty = false;
        setViewsEnabled(false);

        if (!stateOfMusic) {
            removeRandomAnswer();
        } else {
            MediaManager.getInstance().playSoundGame(R.raw.ping,
                    mp -> MediaManager.getInstance().playSoundGame(R.raw.sound_chon_50_50,
                            mp1 -> MediaManager.getInstance().playSoundGame(R.raw.remove50,
                                    mp11 -> MediaManager.getInstance().playSoundGame(R.raw.tinh,
                                            mp111 -> removeRandomAnswer()))));
        }
    }

    private void removeRandomAnswer() {
        int answerCount = 0; // Count TextView hidden
        int trueAnswerIndex = question.answerTrue;
        Random random = new Random();

        while (answerCount < 2) {
            int randomAnswerHide = random.nextInt(4) + 1;

            if (randomAnswerHide != trueAnswerIndex && !listAnswerHidden.contains(randomAnswerHide)) {
                TextView tvHide = getAnswerSelected(randomAnswerHide); // Get position textview to hide
                tvHide.setVisibility(View.INVISIBLE);
                listAnswerHidden.add(randomAnswerHide);
                answerCount++;
                Log.i(TAG, "Count: " + answerCount);
            }
        }

        setViewsEnabled(true);
        binding.ivFifty.setImageResource(R.drawable.ic_502);
        resumeCountDownTimer();
    }

    private void handleClickAnswerA() {
        //false
        if (!stateOfConfirm) {
            selected(binding.answerA, 1);
        } else {
            showConfirmationDialog(binding.answerA, 1);
        }
    }

    private void handleClickAnswerB() {
        if (!stateOfConfirm) {
            selected(binding.answerB, 2);
        } else {
            showConfirmationDialog(binding.answerB, 2);
        }
    }

    private void handleClickAnswerC() {
        if (!stateOfConfirm) {
            selected(binding.answerC, 3);
        } else {
            showConfirmationDialog(binding.answerC, 3);
        }
    }

    private void handleClickAnswerD() {
        if (!stateOfConfirm) {
            selected(binding.answerD, 4);
        } else {
            showConfirmationDialog(binding.answerD, 4);
        }
    }

    private void showConfirmationDialog(TextView answerView, int position) {
        musicId(R.raw.ping);

        noticeConfirmSelectDialog = new NoticeDialog(context);
        noticeConfirmSelectDialog.createCustomDialog(false, "Xác nhận", "Bạn có chắc chắn chọn đáp án này không ?", "Không", "Có", v -> {
            if (v.getId() == R.id.bt_yes) {
                selected(answerView, position);
            }
            noticeConfirmSelectDialog.dismiss();
        });

        noticeConfirmSelectDialog.show();
    }

    private void showSettingDialog() {
        musicId(R.raw.ping);
        confirmSettingDialog = new ConfirmSelectAnsDialog(context);
        confirmSettingDialog.setOnConfirmationChangeListener(confirmed -> stateOfConfirm = confirmed);
        confirmSettingDialog.show();
    }


    public void showMilestone(int level) {
        binding.tvMilestone.setText(Constants.MILESTONE_ARRAY[level]);
        musicId(R.raw.tinh);
    }

    @SuppressLint("SetTextI18n")
    private void showQuestion(int level) {
        //Binding data
        try {
            question = questionList.get(level);
            Log.i(TAG, "Name: " + question.nameQuestion + "\nLevel: " + question.level + "\nAnswer True: " + question.answerTrue);
            binding.tvLevelQuestion.setText(String.format("Câu %s", level + 1));
            binding.tvNameQuestion.setText(question.nameQuestion);
            binding.answerA.setText(Constants.ANSWER_ARRAY[0] + ". " + question.answerA);
            binding.answerB.setText(Constants.ANSWER_ARRAY[1] + ". " + question.answerB);
            binding.answerC.setText(Constants.ANSWER_ARRAY[2] + ". " + question.answerC);
            binding.answerD.setText(Constants.ANSWER_ARRAY[3] + ". " + question.answerD);

            //Bg default answer
            binding.answerA.setBackgroundResource(R.drawable.bg_ans);
            binding.answerB.setBackgroundResource(R.drawable.bg_ans);
            binding.answerC.setBackgroundResource(R.drawable.bg_ans);
            binding.answerD.setBackgroundResource(R.drawable.bg_ans);

            binding.answerA.setVisibility(View.VISIBLE);
            binding.answerB.setVisibility(View.VISIBLE);
            binding.answerC.setVisibility(View.VISIBLE);
            binding.answerD.setVisibility(View.VISIBLE);

            //Turn on click
            setViewsEnabled(true);

            //Start count down time
            setCountDownTimer();

            //Play sound start question 1 -> 15
            musicId(MediaManager.START_QUESTION[level]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void musicId(int idMusic) {
        //true
        if (stateOfMusic) {
            MediaManager.getInstance().playSoundGame(idMusic, null);
        }
    }

    public void selected(TextView answerView, int position) {
        //Turn off click
        setViewsEnabled(false);

        //Stop count down time
        countDownTimer.cancel();

        //false
        if (!stateOfMusic) {
            if (position == question.answerTrue) {
                answerTrue(position, answerView);
            } else {
                answerFalse(question.answerTrue, answerView);
            }
        } else {
            answerView.setBackgroundResource(R.drawable.bg_selected);
            MediaManager.getInstance().playSoundGame(MediaManager.SELECT[position - 1],
                    mp -> MediaManager.getInstance().playSoundGame(R.raw.ans_now, mp1 -> {
                        if (position == question.answerTrue) {
                            answerTrue(position, answerView);
                        } else {
                            answerFalse(question.answerTrue, answerView);
                        }
                    }));
        }
    }

    private void answerTrue(int position, TextView answerView) {
        //false
        if (!stateOfMusic) {
            if (level == 14) {
                champion();
            } else {
                nextQuestionGame();
            }
        } else {
            //Bg view if answer choose true
            answerView.setBackgroundResource(R.drawable.bg_true);
            //Animation
            animationSelectTrueAnswer(answerView);
            loadSoundNextQuestionGame(position);
        }
    }

    private void loadSoundNextQuestionGame(int position) {
        MediaManager.getInstance().playSoundGame(MediaManager.TRUE[position - 1], mp -> {
            if (level == 4) {
                MediaManager.getInstance().playSoundGame(R.raw.pass_milestone_1, mp1 -> nextQuestionGame());
            } else if (level == 9) {
                MediaManager.getInstance().playSoundGame(R.raw.pass_milestone_2, mp12 -> nextQuestionGame());
            } else if (level == 14) {
                MediaManager.getInstance().playSoundGame(R.raw.pass_milestone_3, mp13 -> {
                    MediaManager.getInstance().playSoundGame(R.raw.win_games, null);
                    champion();
                });
            } else {
                nextQuestionGame();
            }
        });
    }

    private void nextQuestionGame() {
        if (level < 14) {
            //Level up
            level++;
            //clear list answer hidden
            listAnswerHidden.clear();
            //Show next question
            showQuestion(level);
            showMilestone(level);

            //Visible icon help ask here
            if (level == 5) {
                binding.ivHere.setVisibility(View.VISIBLE);
            }
        }
    }

    private void champion() {
        countDownTimer.cancel();
        int milestoneChampion = 250000000;
        saveScoreDialog = new SaveScoreDialog(context, callBack);
        saveScoreDialog.createSaveScoreDialog("chiến thắng", R.drawable.ic_winner, R.drawable.gif_fireworks2, milestoneChampion, v -> {
            if (v.getId() == R.id.bt_save) {
                saveScoreDialog.insertOrUpdateScore(milestoneChampion);
            } else {
                loadSoundPlayAgain();
            }
        });
        saveScoreDialog.show();
    }

    private void loadSoundPlayAgain() {
        if (stateOfMusic) {
            MediaManager.getInstance().playSoundGame(R.raw.play1, null);
        }
        saveScoreDialog.dismiss();
        callBack.showFragment(PlayGameFragment.TAG, false);
    }

    private void answerFalse(int idAnswerTrue, TextView answerView) {
        if (!stateOfMusic) {
            showGameOver();
        } else {
            //Bg view if answer choose false
            answerView.setBackgroundResource(R.drawable.bg_wrong);
            //Display bg view answer true
            getAnswerSelected(idAnswerTrue).setBackgroundResource(R.drawable.bg_true);
            animationSelectTrueAnswer(getAnswerSelected(idAnswerTrue));
            MediaManager.getInstance().playSoundGame(MediaManager.LOSE[idAnswerTrue - 1],
                    mp -> MediaManager.getInstance().playSoundGame(R.raw.sound_chiatay, mp1 -> {
                        MediaManager.getInstance().playSoundGame(R.raw.sound_fail, null);
                        showGameOver();
                    }));
        }
    }

    private void showGameOver() {
        countDownTimer.cancel();
        int milestoneGameOver = Integer.parseInt(binding.tvMilestone.getText().toString());
        saveScoreDialog = new SaveScoreDialog(context, callBack);
        saveScoreDialog.createSaveScoreDialog("thất bại", R.drawable.ic_fail, R.drawable.gif_fireworks6, milestoneGameOver, v -> {
            if (v.getId() == R.id.bt_save) {
                saveScoreDialog.insertOrUpdateScore(milestoneGameOver);
            } else {
                loadSoundPlayAgain();
            }
        });
        saveScoreDialog.show();
    }

    private TextView getAnswerSelected(int id) {
        TextView tvSelected = null;
        if (id == 1) {
            tvSelected = binding.answerA;
        } else if (id == 2) {
            tvSelected = binding.answerB;
        }
        if (id == 3) {
            tvSelected = binding.answerC;
        }
        if (id == 4) {
            tvSelected = binding.answerD;
        }
        return tvSelected;
    }

    private void animationSelectTrueAnswer(View v) {
        Animation animation = new AlphaAnimation(2.0f, 0.0f);
        animation.setDuration(100);
        animation.setRepeatCount(15);
        v.startAnimation(animation);
    }

    public void setViewsEnabled(boolean enabled) {
        binding.ivHome.setEnabled(enabled);
        binding.ivSettingGame.setEnabled(enabled);
        binding.answerA.setEnabled(enabled);
        binding.answerB.setEnabled(enabled);
        binding.answerC.setEnabled(enabled);
        binding.answerD.setEnabled(enabled);

        //Check state help question game
        if (isUseFifty) {
            binding.ivFifty.setEnabled(enabled);
        } else {
            binding.ivFifty.setEnabled(false);
        }

        if (isUsePercent) {
            binding.ivPercent.setEnabled(enabled);
        } else {
            binding.ivPercent.setEnabled(false);
        }

        if (isUseCall) {
            binding.ivTelephone.setEnabled(enabled);
        } else {
            binding.ivTelephone.setEnabled(false);
        }

        if (isUseHere) {
            binding.ivHere.setEnabled(enabled);
        } else {
            binding.ivHere.setEnabled(false);
        }
    }

    public void showBackMainDialog() {
        noticeQuitGameDialog = new NoticeDialog(context);
        noticeQuitGameDialog.createCustomDialog(false, "thông báo"
                , "Bạn có muốn dừng cuộc chơi không? \nLưu ý: Dữ liệu hiện tại sẽ không được lưu lại.", "Không", "Có", v -> {
                    if (v.getId() == R.id.bt_yes) {
                        isBack = true;
                        //Stop count down time
                        countDownTimer.cancel();
                        //Turn off click
                        setViewsEnabled(false);

                        //false
                        if (!stateOfMusic) {
                            callBack.showFragment(HomeFragment.TAG, false);
                        } else {
                            MediaManager.getInstance().playSoundGame(R.raw.sound_ket_thuc
                                    , mp -> callBack.showFragment(HomeFragment.TAG, false));
                        }

                    }
                    noticeQuitGameDialog.dismiss();
                });
        noticeQuitGameDialog.show();
        musicId(R.raw.ping);
    }

    private void setCountDownTimer() {
        long duration = getDurationForLevel(level);

        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
//                Log.i(TAG, "" + remainingTime);

                String sDuration = String.format("%s", remainingTime / 1000);
                binding.tvTime.setText(sDuration);
//                Log.i(TAG, sDuration);
            }

            @Override
            public void onFinish() {
                onTimerFinish();
            }
        };
        countDownTimer.start();
    }

    public void pauseCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void resumeCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer = new CountDownTimer(remainingTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTime = millisUntilFinished;

                    String sDuration = String.format("%s", remainingTime / 1000);
                    binding.tvTime.setText(sDuration);
                }

                @Override
                public void onFinish() {
                    onTimerFinish();
                }
            };
            countDownTimer.start();
        }
    }

    private long getDurationForLevel(int level) {
        if (level <= 4) {
            binding.ivTime.setImageResource(R.drawable.time1);
            return 16000;
        } else if (level <= 9) {
            binding.ivTime.setImageResource(R.drawable.time2);
            return 31000;
        } else if (level <= 14) {
            binding.ivTime.setImageResource(R.drawable.time3);
            return 46000;
            //TimeUnit.SECONDS.toMillis(46); s->millis
        }
        return 0;
    }

    private void onTimerFinish() {
        setViewsEnabled(false);
        closePreviousDialog();

        if (!stateOfMusic) {
            noticeTimeOutDialog = new NoticeDialog(context);
            noticeTimeOutDialog.createCustomDialog(false, "Thông báo", "Thời gian đã hết! \n Bạn đã thua cuộc rồi!", "Đóng", null, v -> {
                if (v.getId() == R.id.bt_no) {
                    showGameOver();
                    noticeTimeOutDialog.dismiss();
                }
            });
            noticeTimeOutDialog.show();
        } else {
            loadSoundTimeOut();
        }
    }

    private void loadSoundTimeOut() {
        MediaManager.getInstance().playSoundGame(R.raw.out_of_time, mp -> {
            noticeTimeOutDialog = new NoticeDialog(context);
            noticeTimeOutDialog.createCustomDialog(false, "Thông báo", "Thời gian đã hết! \n Bạn đã thua cuộc rồi!", "Đóng", null, v -> {
                if (v.getId() == R.id.bt_no) {
                    showGameOver();
                    noticeTimeOutDialog.dismiss();
                }
            });
            noticeTimeOutDialog.show();
        });
    }

    public void closePreviousDialog() {
        if (noticeQuitGameDialog != null && noticeQuitGameDialog.isShowing()) {
            noticeQuitGameDialog.dismiss();
        } else if (noticeConfirmSelectDialog != null && noticeConfirmSelectDialog.isShowing()) {
            noticeConfirmSelectDialog.dismiss();
        } else if (confirmSettingDialog != null && confirmSettingDialog.isShowing()) {
            confirmSettingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    public void onDialogClosed() {
        resumeCountDownTimer();
        setViewsEnabled(true);
    }
}