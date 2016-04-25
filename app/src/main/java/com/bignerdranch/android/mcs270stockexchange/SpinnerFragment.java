package com.bignerdranch.android.mcs270stockexchange;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Martin on 4/22/16.
 */
public class SpinnerFragment extends DialogFragment{

    private Spinner mSpinner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_spinner, null);

        mSpinner = (Spinner) v.findViewById(R.id.dialog_zoom_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.weights_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.select_title).setPositiveButton(android.R.string.ok, null).create();
    }

    

}
