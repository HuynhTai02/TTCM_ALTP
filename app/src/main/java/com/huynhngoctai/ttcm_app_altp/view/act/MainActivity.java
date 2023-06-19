package com.huynhngoctai.ttcm_app_altp.view.act;

import androidx.fragment.app.Fragment;

import android.view.View;

import com.huynhngoctai.ttcm_app_altp.CommonUtils;
import com.huynhngoctai.ttcm_app_altp.MediaManager;
import com.huynhngoctai.ttcm_app_altp.R;
import com.huynhngoctai.ttcm_app_altp.databinding.ActivityMainBinding;
import com.huynhngoctai.ttcm_app_altp.view.dialog.NoticeDialog;
import com.huynhngoctai.ttcm_app_altp.view.dialog.SettingDialog;
import com.huynhngoctai.ttcm_app_altp.view.fragment.HomeFragment;
import com.huynhngoctai.ttcm_app_altp.view.fragment.PlayGameFragment;
import com.huynhngoctai.ttcm_app_altp.view.fragment.SplashFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        showFragment(SplashFragment.TAG, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MediaManager.getInstance().pauseBG();
        MediaManager.getInstance().pauseSoundGame();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkStateBG();
        checkStateVoiceReading();
    }

    private void checkStateBG() {
        boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_BACKGROUND);
        if (!stateOfMusic) {
            MediaManager.getInstance().pauseBG();
        } else {
            MediaManager.getInstance().resumeBG();
        }
    }

    private void checkStateVoiceReading() {
        boolean stateOfSound = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);
        if (!stateOfSound) {
            MediaManager.getInstance().pauseSoundGame();
        } else {
            MediaManager.getInstance().resumeSound();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fr_container);
        if (fragment instanceof HomeFragment) {
            showQuitGameDialog();
        } else if (fragment instanceof PlayGameFragment) {
            //check isBack ? false
            if (!((PlayGameFragment) fragment).isBack) {
                ((PlayGameFragment) fragment).showBackMainDialog();
            }
        } else {
            MediaManager.getInstance().stopSoundGame();
            getSupportFragmentManager().popBackStack();
        }
    }

    public void showQuitGameDialog() {
        NoticeDialog noticeDialog = new NoticeDialog(this);
        noticeDialog.createCustomDialog(false, "thông báo"
                , "Bạn muốn thoát ứng dụng ?", "Không", "Có", v -> {
                    if (v.getId() == R.id.bt_yes) {
                        finish();
                    }
                    noticeDialog.dismiss();
                });

        noticeDialog.show();
    }
}
