package com.rakibofc.downloadfiledemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DownloadCompleteListener {

    public static final String FILE_URL = "https://file-examples.com/storage/fee3d1095964bab199aee29/2017/10/file_example_PNG_500kB.png";
    private static final String DOWNLOAD_URL = "https://file-examples.com/storage/fee3d1095964bab199aee29/2017/10/file_example_PNG_500kB.png";
    private static final String FILE_NAME = "downloaded_image.png";
    private static final String DIRECTORY_PATH = "/storage/emulated/0/Android/data/com.rakibofc.downloadfiledemo/files/";
    private static final String TEST_ZIP_FILE_URL = "https://developer.android.com/shareables/icon_templates-v4.0.zip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Call the download method
        // DownloadHelper.downloadImage(this, DOWNLOAD_URL, FILE_NAME);

        String destination = Environment.getExternalStorageDirectory() + "/Android/data/" + this.getPackageName() + "/files/" + FILE_NAME;
        File mushafPath = new File(destination);

        Log.e("destination", destination);

        if (!mushafPath.exists() && !mushafPath.isDirectory()) {

            // If file not exists call the download method and pass this activity as the listener for download the file
            DownloadHelper.downloadImage(this, DOWNLOAD_URL, this);
        } else {
            Log.e("Info", "al ready download");
        }

        ViewPager viewPager = findViewById(R.id.viewPager);
        FloatingActionButton fab = findViewById(R.id.btn_fab);

        List<File> imageFiles = ImageUtils.getAllImagesFromDirectory(DIRECTORY_PATH);
        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(this, imageFiles);
        viewPager.setAdapter(adapter);

        fab.setOnClickListener(v -> {

            Log.e("File Name", getFileName(TEST_ZIP_FILE_URL));
            DownloadHelper.downloadImage(this, TEST_ZIP_FILE_URL, this);
        });
    }

    private String getFileName(String fileUrl) {
        return FileNameExtractor.extractFileNameFromUrl(fileUrl);
    }

    private static class FileNameExtractor {
        public static String extractFileNameFromUrl(String url) {
            try {
                URL fileUrl = new URL(url);
                String path = fileUrl.getPath();
                return path.substring(path.lastIndexOf('/') + 1);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void onDownloadComplete() {
        Log.e("Success", "DownloadComplete");
    }

    @Override
    public void onDownloadFailed() {
        Log.e("Failed", "DownloadFailed");
    }
}