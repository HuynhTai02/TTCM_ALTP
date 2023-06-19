package com.huynhngoctai.ttcm_app_altp.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.huynhngoctai.ttcm_app_altp.App;
import com.huynhngoctai.ttcm_app_altp.CommonUtils;
import com.huynhngoctai.ttcm_app_altp.MediaManager;
import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.database.entities.ScoreUser;
import com.huynhngoctai.ttcm_app_altp.databinding.DialogSaveScoreBinding;
import com.huynhngoctai.ttcm_app_altp.view.OnMainCallBack;
import com.huynhngoctai.ttcm_app_altp.view.fragment.HomeFragment;

import java.text.DecimalFormat;

public class SaveScoreDialog extends Dialog {
    private final DialogSaveScoreBinding binding;
    private Context context;
    private final String TAG = SaveScoreDialog.class.getName();
    public boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);
    public OnMainCallBack callBack;

    public SaveScoreDialog(@NonNull Context context, OnMainCallBack callBack) {
        super(context);
        binding = DialogSaveScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.callBack = callBack;
        setCancelable(false);
    }

    @SuppressLint("SetTextI18n")
    public void createSaveScoreDialog(String title, int idPicture, int idGif, int milestone, View.OnClickListener onClickListener) {
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        binding.tvTitle.setText(title);
        binding.ivTitle.setImageResource(idPicture);
        binding.gifWin.setImageResource(idGif);
        binding.btAgain.setText("Chơi lại");

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formatNumber = decimalFormat.format(milestone);
        binding.tvMilestone.setText(formatNumber + " VND");

        if (milestone == 0) {
            binding.edtName.setVisibility(View.GONE);
            binding.btSave.setText("Thoát");
            binding.btSave.setOnClickListener(v -> loadSoundEnd());
        } else {
            binding.edtName.setVisibility(View.VISIBLE);
            binding.btSave.setText("Lưu điểm");
            binding.btSave.setOnClickListener(v -> {
                String name = binding.edtName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Bạn chưa nhập tên", Toast.LENGTH_SHORT).show();
                } else {
                    insertOrUpdateScore(milestone);
                }
            });
        }
        binding.btAgain.setOnClickListener(onClickListener);
    }

    public void insertOrUpdateScore(int milestone) {
        String name = binding.edtName.getText().toString();
        //Remove spaces
        name = name.trim().replaceAll("\\s+", " ");
        //To UpperCase First Character of each letter
        char[] charArray = name.toCharArray();
        boolean foundSpace = true;

        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLetter(charArray[i])) {
                if (foundSpace) {
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            } else {
                foundSpace = true;
            }
        }
        //Convert char[] to string
        name = String.valueOf(charArray);

        //Variable used in lambda expression should be final or effectively final
        String effectiveFinalName = name;

        new Thread(() -> {
            checkNameExist(effectiveFinalName, milestone);
            loadSoundEnd();
        }).start();
    }

    private void checkNameExist(String name, int milestone) {
        ScoreUser existingUser = App.getInstance().getDatabase().getScoreUserDAO().getScoreUserByName(name);

        if (existingUser != null) {
            int oldScore = existingUser.score;
            if (oldScore < milestone) {
                App.getInstance().getDatabase().getScoreUserDAO().updateScoreUserByName(name, milestone);
                Log.i(TAG, "Cập nhật điểm thành công");
                Log.i(TAG, existingUser.nameUser + milestone);
            }
        } else {
            App.getInstance().getDatabase().getScoreUserDAO().insertScoreUser(new ScoreUser(name, milestone));
            Log.i(TAG, "Thêm mới");
            Log.i(TAG, name + milestone);
        }

        Log.i(TAG, App.getInstance().getDatabase().getScoreUserDAO().getAllScoreUser().toString());
    }

    private void loadSoundEnd() {
        if (!stateOfMusic) {
            dismiss();
            callBack.showFragment(HomeFragment.TAG, false);
        } else {
            MediaManager.getInstance().playSoundGame(R.raw.sound_ket_thuc, mp -> {
                dismiss();
                callBack.showFragment(HomeFragment.TAG, false);
            });
        }
        hideSoftKeyboard();
    }

    private void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}