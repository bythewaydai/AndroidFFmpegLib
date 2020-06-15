package com.dl.androidffmpeg.util;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * The JNI interface of handling FFmpeg command
 * Created by dl on 2020/2/3
 */
public class FFmpegCmd {

    private final static int RESULT_SUCCESS = 1;
    private final static int RESULT_ERROR = 0;

    static {
        System.loadLibrary("media-handle");
    }

    /**
     * Execute FFmpeg command
     *
     * @param commands         the String array of command
     * @param onHandleListener the callback for executing command
     */
    public static void execute(final String[] commands, final OnHandleListener onHandleListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (onHandleListener != null) {
                    onHandleListener.onBegin();
                }
                //call JNI interface to execute FFmpeg cmd
                int result = handle(commands);
                if (onHandleListener != null) {
                    onHandleListener.onEnd(result, null);
                }
            }
        }).start();
    }

    /**
     * Execute FFmpeg multi commands
     *
     * @param commands         the String array of command
     * @param onHandleListener the callback for executing command
     */
    public static void execute(final List<String[]> commands, final OnHandleListener onHandleListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (onHandleListener != null) {
                    onHandleListener.onBegin();
                }
                //call JNI interface to execute FFmpeg cmd
                int result = 0;
                int count = 0;
                for (String[] command : commands) {
                    result = handle(command);
                    count++;
                    Log.i("FFmpegCmd", count + " result=" + result);
                }
                if (onHandleListener != null) {
                    onHandleListener.onEnd(result, null);
                }
            }
        }).start();
    }

    /**
     * execute probe cmd internal
     *
     * @param commands         commands
     * @param onHandleListener onHandleListener
     */
    public static void executeProbe(final String[] commands, final OnHandleListener onHandleListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (onHandleListener != null) {
                    onHandleListener.onBegin();
                }
                //call JNI interface to execute FFprobe cmd
                String result = handleProbe(commands);
                int resultCode = !TextUtils.isEmpty(result) ? RESULT_SUCCESS : RESULT_ERROR;
                if (onHandleListener != null) {
                    onHandleListener.onEnd(resultCode, result);
                }
            }
        }).start();
    }

    /**
     * execute probe cmd with synchronization
     *
     * @param commands commands
     */
    public static String executeProbeSynchronize(final String[] commands) {
        return handleProbe(commands);
    }

    private native static int handle(String[] commands);

    private native static int fastStart(String inputFile, String outputFile);

    private native static String handleProbe(String[] commands);

    /**
     * Using FastStart to moov box in front of mdat box
     *
     * @param inputFile  inputFile
     * @param outputFile outputFile
     * @return the result of moving moov box in front of mdat box
     * 0 for success, -1 for fail
     */
    public int moveMoovAhead(String inputFile, String outputFile) {
        if (TextUtils.isEmpty(inputFile) || TextUtils.isEmpty(outputFile)) {
            return -1;
        }
        return fastStart(inputFile, outputFile);
    }

    public interface OnHandleListener {
        void onBegin();

        void onEnd(int resultCode, String resultMsg);
    }

}