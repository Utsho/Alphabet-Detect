package com.example.alphabetgameproject;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    String[][] s1, s2;
    Bitmap[][] img;
    CustomRecentActivity mainActivity;

    public CustomAdapter (CustomRecentActivity mainActivity1, String[][] m1, String[][] m2, Bitmap[][] img1)
    {
        s1 = m1;
        s2 = m2;
        img = img1;
        mainActivity = mainActivity1;
    }

    @Override
    public int getCount() {
        return s1.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        if(row == null) {
            row = mainActivity.getLayoutInflater().inflate(R.layout.single_row, null, true);
        }

        TextView tv11 = (TextView) row.findViewById(R.id.theline11);
        TextView tv12 = (TextView) row.findViewById(R.id.theline12);
        ImageView imv1 = (ImageView) row.findViewById(R.id.theimage1);

        TextView tv21 = (TextView) row.findViewById(R.id.theline21);
        TextView tv22 = (TextView) row.findViewById(R.id.theline22);
        ImageView imv2 = (ImageView) row.findViewById(R.id.theimage2);

        TextView tv31 = (TextView) row.findViewById(R.id.theline31);
        TextView tv32 = (TextView) row.findViewById(R.id.theline32);
        ImageView imv3 = (ImageView) row.findViewById(R.id.theimage3);

        tv11.setText(s1[i][0]);
        tv12.setText(s2[i][0]);
        imv1.setImageBitmap(img[i][0]);

        tv21.setText(s1[i][1]);
        tv22.setText(s2[i][1]);
        imv2.setImageBitmap(img[i][1]);

        tv31.setText(s1[i][2]);
        tv32.setText(s2[i][2]);
        imv3.setImageBitmap(img[i][2]);

        return row;
    }
}
