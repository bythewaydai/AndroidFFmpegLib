package com.dl.androidffmpeg.util;

class FFmpegUtil {

    /**
     * 使用示例
     *
     * @param srcFile
     * @param targetFile
     */
    public static void transformAudio(String srcFile, String targetFile, FFmpegCmd.OnHandleListener onHandleListener) {
        FFmpegCmd.execute(FFmpegCommandLineUtil.transformAudio(srcFile, targetFile), onHandleListener);
//        FFmpegCmd.execute(FFmpegCommandLineUtil.transformAudio(srcFile, targetFile), new FFmpegCmd.OnHandleListener() {
//            @Override
//            public void onBegin() {
//            }
//
//            @Override
//            public void onEnd(int resultCode, String resultMsg) {
//            }
//        });
    }
}
