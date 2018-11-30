package wang.catuncle.remotelogdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MonitorLogcat {

    private static MonitorLogcat sLogcatRunner;

    private ShellProcessThread mLogcatThread;

    public static MonitorLogcat getInstance() {
        if (sLogcatRunner == null) {
            synchronized (MonitorLogcat.class) {
                if (sLogcatRunner == null) {
                    sLogcatRunner = new MonitorLogcat();
                }
            }
        }
        return sLogcatRunner;
    }


    private MonitorLogcat() {

    }

    public void start(LogcatOutputCallback logcatOutputCallback) {
        try {
            if (mLogcatThread != null && mLogcatThread.isAlive()) {
                mLogcatThread.stopReader();
                mLogcatThread.setOutputCallback(null);
                mLogcatThread.interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLogcatThread = new ShellProcessThread();
        mLogcatThread.setOutputCallback(logcatOutputCallback);
        mLogcatThread.start();

    }

    public void stop() {
        try {
            if (mLogcatThread != null && mLogcatThread.isAlive()) {
                mLogcatThread.stopReader();
                mLogcatThread.setOutputCallback(null);
                mLogcatThread.interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class ShellProcessThread extends Thread {

        private volatile boolean readerLogging = true;
        private LogcatOutputCallback mOutputCallback;

        public void setOutputCallback(LogcatOutputCallback outputCallback) {
            mOutputCallback = outputCallback;
        }

        @Override
        public void run() {
            Process exec = null;
            InputStream inputStream = null;
            BufferedReader reader = null;

            try {
                exec = Runtime.getRuntime().exec("logcat -v threadtime");
                inputStream = exec.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));
                while (readerLogging) {
                    String line = reader.readLine();
                    if (mOutputCallback != null) {
                        mOutputCallback.onReaderLine(line);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (exec != null) {
                    exec.destroy();
                }
            }
        }

        public void stopReader() {
            readerLogging = false;
        }

    }

    public interface LogcatOutputCallback {

        void onReaderLine(String line);
    }
}