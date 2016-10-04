package com.mustacheproject.mustachecontability;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class NewOperation extends Activity {
    ContabilityDatabaseAdapter databaseContability;
    String account, reason, date;
    double amount;
    RadioGroup sAccount;
    EditText eTAmount, eTReason,eTData;
    RadioButton radioAccountButton;
    ImageView movementPhoto;
    Uri imageUri;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_operation);
        databaseContability = new ContabilityDatabaseAdapter(this);
        movementPhoto = (ImageView) findViewById(R.id.image_photo_taken);



        /*Data inserita staticamente per le prove*/
        date = "11/10/2009";
        sAccount = (RadioGroup) findViewById(R.id.radio_ChooseAccount);
        eTAmount = (EditText) findViewById(R.id.edit_insertAmount);
        eTReason = (EditText) findViewById(R.id.edit_insertReason);

        final boolean value = ((GlobalVariables) getApplication()).getControlPosOrNeg();


        final ImageButton addDataButton = (ImageButton) findViewById(R.id.edit_checkButton);
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eTAmount.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(), R.string.messageAmountNotInsert, Toast.LENGTH_LONG).show();
                else {
                /*Ciclo IF per controllare se il boolean del segno è negativo (false) */
                    if (value == false) {
                        amount = Double.parseDouble(eTAmount.getText().toString());
                        double prova = -1;
                        amount = amount * prova;

                    } else {
                        amount = Double.parseDouble(eTAmount.getText().toString());

                    }

                    int radioB = sAccount.getCheckedRadioButtonId();
                    radioAccountButton = (RadioButton) findViewById(radioB);
                    account = radioAccountButton.getText().toString();
                    reason = eTReason.getText().toString();

                    long id = databaseContability.InsertData(account, reason, amount, date,imageUri);

                /*Ciclo IF che usa il long id per controllare se il campo è stato inserito correttamente*/

                    if (id > 0) {
                        if (account.equals("Cash Account")) {
                            ((GlobalVariables) getApplication()).setCashAmount(amount);
                        } else {
                            ((GlobalVariables) getApplication()).setBankAmount(amount);
                        }
                        ((GlobalVariables) getApplication()).setTotalAmount();
                        ((GlobalVariables) getApplication()).setControlNewData(0);
                        Intent OpenPage = new Intent(NewOperation.this, MainActivity.class);
                        startActivity(OpenPage);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.messageError, Toast.LENGTH_LONG).show();

                    }
                }


            }
        });

        movementPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getApplicationContext(),v);
                popupMenu.inflate(R.menu.choose_picture_menu);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();
                        switch (id){
                            case R.id.action_camera:

                                break;
                            case R.id.action_gallery:
                                Intent intent=new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Select photo"),1);
                        }
                        return false;
                    }
                });
            }


        });

    }


    public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode==RESULT_OK)
            if(reqCode==1){
                imageUri=data.getData();
                movementPhoto.setImageURI(data.getData());
            }
        }
    }




