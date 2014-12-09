package com.example.xander.filebrowser;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by xander on 14-11-29.
 */
public class FileListAdapter extends ArrayAdapter<File> {

    public FileListAdapter(Context context, List<File> fileList) {
        super(context, 0, fileList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(getContext());
        File currentFile = getItem(position);
        if (currentFile.isDirectory())
            textView.setTextColor(Color.YELLOW);
        else
            textView.setTextColor(Color.WHITE);

        textView.setText(currentFile.getName());

        return textView;
    }
}
