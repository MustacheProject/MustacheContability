package com.mustacheproject.mustachecontability;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Eugenio on 17/11/2015.
 */
public class DialogCheck extends DialogFragment implements View.OnClickListener {
    Button yes,no;
    ContabilityDatabaseAdapter databaseContability;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        databaseContability=new ContabilityDatabaseAdapter(this.getActivity());
        View view= inflater.inflate(R.layout.dialog_check,null);
        yes=(Button)view.findViewById(R.id.yesButton);
        no=(Button)view.findViewById(R.id.noButton);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.yesButton){

            Toast.makeText(getActivity(), "Erasing Data...", Toast.LENGTH_SHORT);
            databaseContability.deleteAllData();
            dismiss();


        }
        else {
            dismiss();

        }
    }
}
