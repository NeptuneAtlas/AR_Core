package com.example.explore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static int CAMERA_PERMISSION_CODE = 100;
    private static int VIDEO_PERMISSION_CODE = 101;


    private Uri videoPath, videoPath1;

    public static final String tag = "YOUR-TAG-NAME";
    public static final String msg = "YOUR-TAG-NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isCameraOn()) {
            Log.i("Video_Record_Tag", "Camera is detected");
            getCameraPermission();

        } else {
            Log.i("Video_Record_Tag", "Camera is not detected");
        }
    }

    public void recordVideoButtonPressed(View view) {
        recordVideo();
    }

    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_PERMISSION_CODE);
    }


    private boolean isCameraOn() { //checks if there is a camera present on phone
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            return true;
        } else {
            return false;
        }
    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//@Nullable Intent data
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VIDEO_PERMISSION_CODE) {


                if (resultCode == RESULT_OK) {//1111
                    VideoView videoView = findViewById(R.id.video_view);
                    VideoView videoView1 = findViewById(R.id.video_view1);

                    videoPath = data.getData();

                    Log.i("Video_Record_Tag", "Video is being recorded and is within the path " + videoPath);
                    //this is causing issue fix it
                    videoView.setVideoURI(videoPath); //saves recorded video
                    videoView.start(); //displays video
                    int num = 0;
                    num++;
                    data.setData(null);
                    if (num == 1) {

                        videoPath1 = data.getData();
                        videoView1.setVideoURI(videoPath1);
                        videoView1.start();

                    }
                    // data.setData(null);//data.setData(null);
                    //   videoPath1 = data.getData();
                    // videoPath1 = data.getData();
                    // videoView1.setVideoURI(videoPath1);
                    // videoView1.start();
                    //VideoView video_view = null;
                    // video_view.setVideoURI(data?.data);
                } else if (resultCode == RESULT_CANCELED) {
                    Log.i("Video_Record_Tag", "Recorded video has been cancelled " + videoPath);
                } else {
                    Log.i("Video_Record_Tag", "Error found for the video recording" + videoPath);
                }



        }
    }

  //  @Override
   // public void onActivityReenter(int resultCode, Intent data) {
    //    super.onActivityReenter(resultCode, data);


    //}
}