package com.huynhngoctai.ttcm_app_altp.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.huynhngoctai.ttcm_app_altp.CommonUtils;
import com.huynhngoctai.ttcm_app_altp.Constants;
import com.huynhngoctai.ttcm_app_altp.MediaManager;
import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.databinding.DialogHelpAskHereBinding;
import com.huynhngoctai.ttcm_app_altp.view.OnDialogListener;


public class HelpAskHereDialog extends Dialog {
    public  DialogHelpAskHereBinding binding;
    private OnDialogListener onDialogListener;
    boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);

    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }

    public HelpAskHereDialog(@NonNull Context context) {
        super(context);
        binding = DialogHelpAskHereBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleThanks();
    }

    public void showAskHereAnswer(int idAnswerTrue) {
        if (!stateOfMusic) {
            getAskAnswer(idAnswerTrue);
        } else {
            MediaManager.getInstance().playSoundGame(R.raw.help_ask_01, mp -> getAskAnswerHaveSound(idAnswerTrue));
        }
    }

    private void getAskAnswerHaveSound(int idAnswerTrue) {
        switch (idAnswerTrue) {
            case 1:
                playSound(R.raw.tv1, R.drawable.ic_a, R.raw.here_a, "A", R.raw.tv2, R.drawable.ic_b, R.raw.here_b, "B", R.raw.tv3, R.drawable.ic_c, R.raw.here_a2, "A");
                break;
            case 2:
                playSound(R.raw.tv1_1, R.drawable.ic_a3, R.raw.here_c, "C", R.raw.tv2_1, R.drawable.ic_b, R.raw.here_b, "B", R.raw.tv3_1, R.drawable.ic_b3, R.raw.here_b2, "B");
                break;
            case 3:
                playSound(R.raw.tv1, R.drawable.ic_c, R.raw.here_c, "C", R.raw.tv2, R.drawable.ic_a3, R.raw.here_c2, "C", R.raw.tv3, R.drawable.ic_c, R.raw.here_c3, "C");
                break;
            case 4:
                playSound(R.raw.tv1_1, R.drawable.ic_a, R.raw.here_d, "D", R.raw.tv2_1, R.drawable.ic_b, R.raw.here_d2, "D", R.raw.tv3_1, R.drawable.ic_c, R.raw.here_a2, "A");
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void playSound(int sound1, int drawable1, int sound2, String text2, int sound3, int drawable3, int sound4, String text4, int sound5, int drawable5, int sound6, String text6) {
        MediaManager.getInstance().playSoundGame(sound1, mp -> {
            binding.ivHere1.setImageResource(drawable1);

            MediaManager.getInstance().playSoundGame(sound2, mp1 -> {
                binding.tvHere1.setText("Đáp án là: " + text2);
                binding.ivHere1.setImageResource(R.drawable.ic_a2);

                MediaManager.getInstance().playSoundGame(sound3, mp2 -> {
                    binding.ivHere2.setImageResource(drawable3);

                    MediaManager.getInstance().playSoundGame(sound4, mp3 -> {
                        binding.tvHere2.setText("Theo tôi là: " + text4);
                        binding.ivHere2.setImageResource(R.drawable.ic_b2);

                        MediaManager.getInstance().playSoundGame(sound5, mp4 -> {
                            binding.ivHere3.setImageResource(drawable5);

                            MediaManager.getInstance().playSoundGame(sound6, mp5 -> {
                                binding.tvHere3.setText("Tôi nghĩ là: " + text6);
                                binding.btThanks.setVisibility(View.VISIBLE);
                                binding.ivHere3.setImageResource(R.drawable.ic_c2);
                            });
                        });
                    });
                });
            });
        });
    }

    @SuppressLint("SetTextI18n")
    private void getAskAnswer(int idAnswerTrue) {
        String answerHelp = Constants.ANSWER_ARRAY[idAnswerTrue - 1];

        binding.tvHere1.setText("Tôi nghĩ là: " + answerHelp);
        binding.tvHere2.setText("Phương án đúng là: " + answerHelp);
        binding.tvHere3.setText("Đáp án đúng là: " + answerHelp);

        binding.ivHere1.setImageResource(R.drawable.ic_b3);
        binding.ivHere2.setImageResource(R.drawable.ic_b);
        binding.ivHere3.setImageResource(R.drawable.ic_a3);

        binding.btThanks.setVisibility(View.VISIBLE);
    }


    private void handleThanks() {
        binding.btThanks.setOnClickListener(v -> {
            dismiss();
            if (onDialogListener != null) {
                onDialogListener.onDialogClosed();
            }
        });
    }
}