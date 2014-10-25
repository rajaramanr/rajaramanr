package edu.cmu.mob.homework.raja.dayful;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Raja on 8/22/2014.
 */
public class HomeFragment extends ListFragment {
    List<String> dates = new ArrayList<String>();

    String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"};

    public HomeFragment() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_WEEK);
        for(int i = 0; i < 7; i++) {
            dates.add(days[day - 1]);
            day++;
            day = (day > 7) ? 1 : day;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, dates);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(v.getContext(), dates.get(position) ,Toast.LENGTH_SHORT).show();
    }
}