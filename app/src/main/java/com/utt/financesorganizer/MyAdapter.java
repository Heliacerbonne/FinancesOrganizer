package com.utt.financesorganizer;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<MouvMoney> {
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<MouvMoney> values){
        super(context, R.layout.listview_item, values);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TextView date;
        TextView cat;
        TextView name;
        TextView amount;

        if(convertView==null){
            convertView = (LinearLayout) inflater.inflate(R.layout.listview_item, parent, false);
        }
        date = (TextView) convertView.findViewById(R.id.exListDate);
        cat = (TextView) convertView.findViewById(R.id.exListCat);
        name = (TextView) convertView.findViewById(R.id.exListName);
        amount = (TextView) convertView.findViewById(R.id.exListAmount);

        MouvMoney obj = this.getItem(position);
        name.setText(obj.getName());
        amount.setText(obj.getAmount()+"");
        date.setText(obj.getDate());
        cat.setText(obj.getCat());

        return convertView;
    }

}
