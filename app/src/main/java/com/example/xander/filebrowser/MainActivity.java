package com.example.xander.filebrowser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class MainActivity extends Activity {

    private File mRootDirectory;
    private FileListAdapter mFileListAdapter;
    private File mSelectedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootDirectory = Environment
                .getExternalStorageDirectory();

        File[] filesArray = mRootDirectory.listFiles();
        List<File> files = new ArrayList<File>();

        for(File file : filesArray) {
            files.add(file);
        }

        mFileListAdapter = new FileListAdapter(this, files);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mFileListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedFile = mFileListAdapter.getItem(position);
                if (mSelectedFile.isDirectory()) {
                    mFileListAdapter.clear();
                    mFileListAdapter.addAll(mSelectedFile.listFiles());
                    mFileListAdapter.notifyDataSetChanged();
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    MimeTypeMap map = MimeTypeMap.getSingleton();
                    String ext = MimeTypeMap.getFileExtensionFromUrl(mSelectedFile.getName());
                    String type = map.getMimeTypeFromExtension(ext);

                    if (type == null) {
                        type = "*/*";
                    }

                    Uri data = Uri.fromFile(mSelectedFile);

                    intent.setDataAndType(data, type);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (mSelectedFile.equals(mRootDirectory)) {
            super.onBackPressed();
        } else {
            File parent = mSelectedFile.getParentFile();
            mFileListAdapter.clear();
            mFileListAdapter.addAll(parent.listFiles());
            mFileListAdapter.notifyDataSetChanged();
            mSelectedFile = parent;
        }

    }
}
