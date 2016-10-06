package com.mustacheproject.mustachecontability;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditContact extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        DataProvider dp= ((GlobalVariables)getApplication()).getDp();
        EditText eTAmount,eTReason,eTData;
        RadioGroup eTAccount;
        RadioButton eTRadioAccountButton;
        //TODO implements the photo managment system
        ImageView eTmovementPhoto;
        Uri imageUri;
        String newAccount,newReason,account;
        double newAmount;
        eTAccount= (RadioGroup) findViewById(R.id.edit_radio_ChooseAccount);

        account=dp.get_account();
        if(account=="Cash Account"){
            //eTRadioAccountButton.setChecked(true);
        }



        int radioC=eTAccount.getCheckedRadioButtonId();
        eTRadioAccountButton=(RadioButton)findViewById(radioC);
        newAccount=eTRadioAccountButton.getText().toString();

        eTData=(EditText)findViewById(R.id.editInsertData);
        eTData.setText(dp.get_data());
        eTReason=(EditText)findViewById(R.id.edit_insertReason);
        eTReason.setText(dp.get_reason());
        eTAmount=(EditText)findViewById(R.id.edit_insertAmount);
        eTAmount.setText(dp.get_amount().toString());
      //  eTmovementPhoto=


    }

}
