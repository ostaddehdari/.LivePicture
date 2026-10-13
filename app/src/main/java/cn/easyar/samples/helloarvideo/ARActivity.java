package cn.easyar.samples.helloarvideo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.easyar.CameraDevice;
import cn.easyar.Engine;
import cn.easyar.ImageTracker;
import cn.easyar.VideoPlayer;

/**
 * صفحهٔ اصلی: با باز کردن برنامه دوربین فعال می‌شود؛ EasyAR عکس را پیدا و ردیابی می‌کند
 * و فیلم را دقیقاً روی آن پخش می‌کند.
 */
public class ARActivity extends Activity
{
    /*
     * کلید لایسنس EasyAR Sense 4.x (Personal) — ساخته‌شده در easyar.com برای Package Name: com.miss.hashemi
     * اگر شناسهٔ برنامه (applicationId) عوض شود، باید کلید تازه برای همان شناسه ساخته شود.
     */
    private static final String KEY = "o5KoSaeBsFW/5+0hv0w8xKojY2HYamRUVyWAMpOgnmKnsJh/k72PMtzxl3mCupN/heDIJ6awlH2cq9Vzib7ZPMS+mmOStolbg6qydMTpyjzEv5Jzg72IdZXxwUud8ZlliLeXda+3iDLciKY8xKWaYo+ylWSV8cFLxLCUfYumlXmSqtlNyvGLfIennX+Uvogy3IjZZ4+9n3+RoNk8xL6ac8SO1zKLvJ9liraIMtyI2WODvYh1yJqWcYG2r2KHsJB5iLTZPMSgnn6VttVTiryOdLS2mH+BvZJkj7yVMsrxiHWIoJ4+tLaYf5S3kn6B8dcylbaVY4P9tHKMtphksqGac426lXfE/9ljg72IdciAjmKAsph1sqGac426lXfE/9ljg72IdciAi3GUoJ5DlrKPeYe/tnGW8dcylbaVY4P9tn+SupR+sqGac426lXfE/9ljg72IdciXnn6Vtqhgh6eScYqemmDE/9ljg72IdciQulSyoZpzjbqVd8SO1zKDq4t5lLaveYu2qGSHvosy3L2OfIr/2XmVn5Rzh7/ZKoCyl2ODrtdrxLGOfoK/nlmCoNkqvfGYf4v9lnmVoNV4h6CTdYu62U3K8Y1xlLqafpKg2Sq98Zh/i76Ofo+ngjK7/9lgirKPdomhlmPE6aAyh72fYom6nzK7/9l9ibeOfIOg2Sq98Yh1iKCePq++mneDh4lxhbiSfoHx1zKVtpVjg/24fImmn0KDsJR3iLqPeYm92TzEoJ5+lbbVQoOwlGKCupV3xP/ZY4O9iHXInJl6g7CPRJSymHuPvZwyyvGIdYignj61pol2h7CeRJSymHuPvZwyyvGIdYignj61o5pilbaoYIenknGKnppgxP/ZY4O9iHXInpRkj7yVRJSymHuPvZwyyvGIdYignj6itpVjg4CLcZK6mnyrsosyyvGIdYignj6lkr9ElLKYe4+9nDK7/9l1nqOSYoOHkn2DgI9xi6PZKoiml3zK8ZJjqryYcYrxwXaHv4h1m/+AMoSmlXSKtrJ0lfHBS8TxpjzEpZpij7KVZJXxwUvEsJR9i6aVeZKq2U3K8Yt8h6edf5S+iDLciNl5iaDZTcrxln+Cppd1lfHBS8Sgnn6VttVZi7KcdbKhmnONupV3xP/ZY4O9iHXIkJd/k7epdYW8nH6Pp5J/iPHXMpW2lWOD/al1hbyJdI+9nDLK8Yh1iKCePqmxkXWFp69ih7CQeYi02TzEoJ5+lbbVQ5OhnXGFtq9ih7CQeYi02TzEoJ5+lbbVQ5ayiWODgItxkrqafKuyizLK8Yh1iKCePqu8j3mJva9ih7CQeYi02TzEoJ5+lbbVVIO9iHW1o5pkj7KXXYej2TzEoJ5+lbbVU6eXr2KHsJB5iLTZTcrxnmiWuol1srqWdbWnmn2W8cF+k7+XPMS6iFyJsJp8xOmdcYqgnm27rpwI4YDkGLV6bbKkH4ZQS/dCd6XbaW2PfgxVl4DEvtMGkAi9KDNFfw5B5QJTXnU8Ag/qkXkByO6ZUZxSdr3sB2t6KR0Ul3o3EBD2hRZr8G1iZG7CYahHOTSrjrrVko7uXp4xeVSnvuN6F1OxpJAc/s3v88Rlyb7IMuASWDQDIBZZkxFPzCDAKCEswatenRdHWENPCYCMPyPRtkKX498W2iER8Il2Rzysahqrb6Sw9rfS2zz21ev8faiwAwzHzYC8zJmq6prsvkbXg5ZGwqXtWaYoZtBkVZpqJ5dkSIqClVpeAzNhLmJK3Ls42L2L9z6RJOsIcwe40/esVU9HNubT+xA=";

    private GLView glView;
    private TextView hint;
    private Button muteButton;
    private final Handler ui = new Handler(Looper.getMainLooper());
    private boolean lastTracking = false;

    /** هر ۲۵۰ میلی‌ثانیه: پیام راهنما فقط وقتی عکس دیده نمی‌شود نمایش داده شود */
    private final Runnable statusPoll = new Runnable() {
        @Override
        public void run() {
            boolean tracking = HelloAR.tracking;
            if (tracking != lastTracking) {
                lastTracking = tracking;
                hint.setVisibility(tracking ? View.GONE : View.VISIBLE);
            }
            ui.postDelayed(this, 250);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        hint = findViewById(R.id.hint);
        muteButton = findViewById(R.id.btnMute);
        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelloAR.muted = !HelloAR.muted;
                muteButton.setText(HelloAR.muted ? R.string.unmute : R.string.mute);
            }
        });
        muteButton.setText(HelloAR.muted ? R.string.unmute : R.string.mute);

        if (!Engine.initialize(this, KEY)) {
            Log.e("HelloAR", "Initialization Failed: " + Engine.errorMessage());
            showError("EasyAR: " + Engine.errorMessage());
            return;
        }
        if (!CameraDevice.isAvailable()) {
            showError("CameraDevice not available.");
            return;
        }
        if (!ImageTracker.isAvailable()) {
            showError("ImageTracker not available.");
            return;
        }
        if (!VideoPlayer.isAvailable()) {
            showError("VideoPlayer not available.");
            return;
        }

        glView = new GLView(this);

        requestCameraPermission(new PermissionCallback() {
            @Override
            public void onSuccess() {
                ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            @Override
            public void onFailure() {
                showError(getString(R.string.camera_permission_needed));
            }
        });
    }

    private void showError(String message)
    {
        hint.setText(message);
        hint.setVisibility(View.VISIBLE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private interface PermissionCallback
    {
        void onSuccess();
        void onFailure();
    }
    private PermissionCallback pendingPermission;

    @TargetApi(23)
    private void requestCameraPermission(PermissionCallback callback)
    {
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            pendingPermission = callback;
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            callback.onSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == 1 && pendingPermission != null) {
            PermissionCallback callback = pendingPermission;
            pendingPermission = null;
            boolean granted = grantResults.length > 0;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) { granted = false; }
            }
            if (granted) { callback.onSuccess(); } else { callback.onFailure(); }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (glView != null) { glView.onResume(); }
        ui.post(statusPoll);
    }

    @Override
    protected void onPause()
    {
        ui.removeCallbacks(statusPoll);
        if (glView != null) { glView.onPause(); }
        super.onPause();
    }
}
