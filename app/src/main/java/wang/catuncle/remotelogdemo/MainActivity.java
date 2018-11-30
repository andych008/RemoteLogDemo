package wang.catuncle.remotelogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import wang.catuncle.remotelogdemo.MonitorLogcat.LogcatOutputCallback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String wsUrl = "ws://192.168.1.112:8999";
    private WebSocket mWebSocket;
    private OkHttpClient mOkHttpClient;

    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebSocket();

        findViewById(R.id.connect).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                initWebSocket();
            }
        });

        findViewById(R.id.disconnect).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect();
            }
        });

        sendBtn = findViewById(R.id.send);
        sendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, System.currentTimeMillis() + "");
            }
        });

        MonitorLogcat.getInstance().start(new LogcatOutputCallback() {
            @Override
            public void onReaderLine(String line) {
                log2Server(line);
            }
        });
    }


    private void initWebSocket() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        }

        mOkHttpClient.dispatcher().cancelAll();

        mOkHttpClient.newWebSocket(
            new Request
                .Builder()
                .url(wsUrl)
                .build(),
            new WebSocketListener() {

                @Override
                public void onOpen(WebSocket webSocket, final Response response) {
                    mWebSocket = webSocket;
                    sendBtn.setClickable(true);
                    sendBtn.setVisibility(View.VISIBLE);
                }
            });
    }


    private void disconnect() {
        if (mOkHttpClient != null) {
            mOkHttpClient.dispatcher().cancelAll();
        }
        if (mWebSocket != null) {
            mWebSocket.close(1000, "normal");
        }

        sendBtn.setClickable(false);
    }

    private boolean log2Server(String msg) {
        if (mWebSocket != null) {
            return mWebSocket.send(msg);
        }
        return false;
    }
}
