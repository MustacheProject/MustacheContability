package com.mustacheproject.mustachecontability;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class SettingMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_menu);

        Button backToMain= (Button)findViewById(R.id.backMain);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingMenu.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showCheckDialog(View v){

        FragmentManager manager=getFragmentManager();
        DialogCheck dialogCheck=new DialogCheck();
        dialogCheck.show(manager, "DialogCheck");
        ((GlobalVariables)getApplication()).setBankAmount(0.00);
        ((GlobalVariables)getApplication()).setCashAmount(0.00);
        ((GlobalVariables)getApplication()).setTotalAmount();

    }




}
