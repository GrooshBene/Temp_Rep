package kr.applepi.phonecall;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2015-08-09.
 */
public class PersonAdapter extends BaseAdapter {
    private LayoutInflater li;
    private ArrayList<Person> arrayList;
    private int layout;

    public PersonAdapter(Context context, int layout, ArrayList<Person> person) {
        li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arrayList = person;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = li.inflate(layout, parent, false);
        }

        Person person = arrayList.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(person.getName());

        TextView phoneNum = (TextView) convertView.findViewById(R.id.phoneNum);
        phoneNum.setText(person.getPhoneNum());

        return convertView;
    }



}
