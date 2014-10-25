package edu.cmu.mob.homework.raja.dayful;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Raja on 8/22/2014.
 */
public class SettingsFragment extends Fragment {

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedBundle){
        super.onCreate(savedBundle);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) getView().findViewById(R.id.autoCompleteTextView3);
        String[] countries = getResources().
                getStringArray(R.array.day_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,countries);
        autoCompleteTextView.setAdapter(adapter);
    }
}