package com.automationexercise.media;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;

public class ScreenRecordManager {

    public final static String RECORDINGS_PATH = "test-output/recordings/";

private  static final  ThreadLocal<IVideoRecorder> recorder = new ThreadLocal<>();


    public static void startRecording() {

        //TODO : check if the recording flag is enabled  (done here using if condition)

        //first line that contain (if condition)

        if (PropertyReader.getProperty("recordTests").equalsIgnoreCase("true")) {
            try {
                // ensure the recording directory exists
                File recordingsDir = new File(RECORDINGS_PATH);
                if (!recordingsDir.exists()) {
                    recordingsDir.mkdirs();
                }




                //  TODO:   check if the execution type is local  (done here using if condition)

                //configure the recorder to use the custom directory and file name

                if(PropertyReader.getProperty("executionType").equalsIgnoreCase("local")) {

                    recorder.set(RecorderFactory.getRecorder(VideoRecorder.conf().recorderType()));
                    //start recording
                    recorder.get().start();
                    LogsManager.info("Recording Started");

                }

            } catch (Exception e) {
                LogsManager.error("Failed to start recording:", e.getMessage());


            }
        }
    }


    public static  void  stopRecording(String testMethodName){
        try {
            if (recorder.get() !=null){
                //stop the recorder and get the video file
                String videoFilePath = String.valueOf(recorder.get().stopAndSave(testMethodName));
                File videoFile = new File(videoFilePath);

                //log the file path for debugging
                LogsManager.info("Video file saved at : ",videoFile.getAbsolutePath());

                //convert the video to .mp4 format
                File mp4File = encodeRecording(videoFile);
                LogsManager.info("Recording stopped and converted to MP4",mp4File.getName());

            }

        }
        catch (Exception e){
            LogsManager.error("Failed to stop recording:",e.getMessage());

        }
        finally {
            recorder.remove();
        }
    }




    public static File encodeRecording(File sourceFile){

        File targetFile = new File(sourceFile.getParent(),sourceFile.getName().replace(".avi",".mp4"));
        try {
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("acc"); //AAC audio codec

            VideoAttributes video = new VideoAttributes();
            video.setCodec("libx264"); //H.264 video codec

            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp4"); //output format
            encodingAttributes.setAudioAttributes(audio);
            encodingAttributes.setVideoAttributes(video);

       //encode the video
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(sourceFile),targetFile,encodingAttributes);


            // delete the original .avi file after conversion
            if (targetFile.exists()){
                sourceFile.delete();
                LogsManager.info("Deleted original AVI file: ",sourceFile.getName());

            }

        }
        catch (Exception e)
        {
            LogsManager.error("Failed to convert video to MP4: ", e.getMessage());


        }
        return targetFile;
    }
}
