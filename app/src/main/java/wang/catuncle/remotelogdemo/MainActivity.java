package wang.catuncle.remotelogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import wang.catunclue.wslog.WSLog;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.connect).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                WSLog.getInstance().setEnable(true);
            }
        });

        findViewById(R.id.disconnect).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                WSLog.getInstance().setEnable(false);
            }
        });

        sendBtn = findViewById(R.id.send);
        sendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, System.currentTimeMillis() + "");
            }
        });
    }
}
