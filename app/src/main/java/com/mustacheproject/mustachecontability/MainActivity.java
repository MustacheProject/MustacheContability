package com.mustacheproject.mustachecontability;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    ContabilityDatabaseAdapter databaseContability;
    public static boolean pos=true;
    /*creazione lista, prendendo come base la classe DataProvider, per la stampa*/
    List<DataProvider> Movements= new ArrayList<DataProvider>();
    ListView movementsListView;
    /*variaibli per tentativo riempimento lista da database*/
    int cid;
    String DBAccount,DBDate,DBReason;
    Double DBAmount;
    Cursor cursor;
    Double totalAmount=0.00,cashAmount=0.00,bankAmount=0.00;
    ArrayAdapter<DataProvider> movementAdapter;
    int longClickedItemIndex,controlNewData, clickedItemIndex;
    private static final int EDIT=0, DELETE=1;
    Uri DBImageUri = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseContability = new ContabilityDatabaseAdapter(this);
        movementsListView=(ListView)findViewById(R.id.movementsListView);
        controlNewData=((GlobalVariables)getApplication()).getControlNewData();
        if(controlNewData==0){
            updateAllAmount();
            ((GlobalVariables)getApplication()).setControlNewData(0);
        }if(controlNewData==1){
            updateLastDataInsert();
        }
        String prova= String.valueOf(controlNewData);
        Log.i("PROVA",prova);
           /*inserimento valori da variabili globali*/

        ((GlobalVariables)getApplication()).setTotalAmount();
        totalAmount=((GlobalVariables)getApplication()).getTotalAmount();
        bankAmount=((GlobalVariables)getApplication()).getBankAmount();
        cashAmount=((GlobalVariables)getApplication()).getCashAmount();

        TextView textCashAmount = (TextView) findViewById(R.id.cashAmount);
        TextView textBankAmount = (TextView) findViewById(R.id.bankAmount);
        TextView textTotalAmount = (TextView) findViewById(R.id.totalAmount);

        textTotalAmount.setText(totalAmount.toString());
        textBankAmount.setText(bankAmount.toString());
        textCashAmount.setText(cashAmount.toString());


        /*metodo per il longclick*/

        registerForContextMenu(movementsListView);
        movementsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });

        /* Metodo per la visualizzazione dell'immagine legata al movimento, se non esiste
        * va ad aprirsi l'immagine dell' emptyicon presente nel drawable*/
        movementsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               clickedItemIndex=position;


            }
        });



         /*Dopo aver creato la tabhost nel layout riporto qui i codici per attivarla Prima si inizializza
         * la TabHost principale che contiene le varie sezioni. Poi vanno create la sezioni usando la TabSpec*/

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Main");
        tabSpec.setContent(R.id.mainTab);
        tabSpec.setIndicator("Main");
        tabHost.addTab(tabSpec);

        /*Possiamo usare la stessa variabile TabSpec inizializzata prima*/

        tabSpec = tabHost.newTabSpec("Movements");
        tabSpec.setContent(R.id.movementTab);
        tabSpec.setIndicator("Movements");
        tabHost.addTab(tabSpec);



        /*Inizializzazione e creazione bottone con intent per andare alla pagina nuova
        * operazione con valore POSITIVO*/

        ImageButton OpenAddOperationPage = (ImageButton) findViewById(R.id.plusButton);
        OpenAddOperationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GlobalVariables)getApplication()).setControlPosOrNeg(true);
                Intent OpenPage = new Intent(MainActivity.this, NewOperation.class);
                startActivity(OpenPage);
            }
        });

        /*Inizializzazione e creazione bottone con intent per andare alla pagina nuova
        * operazione con valore NEGATIVO*/
        ImageButton OpenLessOperationPage = (ImageButton) findViewById(R.id.lessButton);
        OpenLessOperationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GlobalVariables)getApplication()).setControlPosOrNeg(false);
                Intent OpenPage = new Intent(MainActivity.this, NewOperation.class);
                startActivity(OpenPage);
            }
        });
    }


    public void populateList() {
        movementAdapter = new MovementListAdapter();
        movementsListView.setAdapter(movementAdapter);
        movementAdapter.notifyDataSetChanged();
    }

    public void addMovement(int id,String account, Double amount, String data, String reason,Uri imageUri) {
        Movements.add(new DataProvider(id,account, amount, data, reason,imageUri));
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);

        menu.setHeaderIcon(R.drawable.edit_icon);
        menu.setHeaderTitle(R.string.movementOptions);
        menu.add(Menu.NONE, EDIT, menu.NONE, R.string.editMovement);
        menu.add(Menu.NONE, DELETE, menu.NONE, R.string.deleteMovement);

    }

    public boolean onContextItemSelected(MenuItem item){
        switch(item.getItemId()){
            case EDIT:
                // TODO: Implement editing movement
                DataProvider dp;
                dp=Movements.get(longClickedItemIndex);
                ((GlobalVariables)getApplication()).setDp(dp);
                Intent openEditActivity = new Intent(this,EditContact.class);
                startActivity(openEditActivity);
            case DELETE:
                dp=Movements.get(longClickedItemIndex);
                databaseContability.deleteMovement(Movements.get(longClickedItemIndex));
                Movements.remove(longClickedItemIndex);
                movementAdapter.notifyDataSetChanged();
                String checkAccount=dp.get_account();
                double cancelAmount=dp.get_amount();
                if(checkAccount.equals("Cash account")){
                    ((GlobalVariables)getApplication()).cancelFromCashAccount(cancelAmount);
                }else {
                    ((GlobalVariables)getApplication()).cancelFromBankAccount(cancelAmount);
                }
                TextView textTotalAmount = (TextView) findViewById(R.id.totalAmount);

                textTotalAmount.setText(totalAmount.toString());


            break;
        }
        return super.onContextItemSelected(item);
    }

    private class MovementListAdapter extends ArrayAdapter<DataProvider> {
        public MovementListAdapter() {
            super(MainActivity.this, R.layout.row_layout, Movements);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.row_layout, parent, false);
            DataProvider currentMovement = Movements.get(position);

            TextView account = (TextView) view.findViewById(R.id.text_account_operation);
            account.setText(currentMovement.get_account());
            TextView amount = (TextView) view.findViewById(R.id.text_amount_operation);
            amount.setText(currentMovement.get_amount().toString());
            TextView data = (TextView) view.findViewById(R.id.text_data_operation);
            data.setText(currentMovement.get_data());
            TextView reason = (TextView) view.findViewById(R.id.text_reason_operation);
            reason.setText(currentMovement.get_reason());

            return view;
        }

    }

    public void updateAllAmount() {
        cursor = databaseContability.getAllData();
        if (cursor != null) {
            while (cursor.moveToNext()){
                cid = cursor.getInt(0);
                DBAccount = cursor.getString(1);
                DBAmount = cursor.getDouble(2);
                DBDate = cursor.getString(3);
                DBReason = cursor.getString(4);
                if (DBAccount.equals("Cash Account")) {
                    cashAmount = cashAmount + DBAmount;
                    DBAccount = "Cash";

                } else {
                    bankAmount = bankAmount + DBAmount;
                    DBAccount = "Bank";
                }


                addMovement(cid, DBAccount, DBAmount, DBDate, DBReason,DBImageUri);
                populateList();

            }
            ((GlobalVariables)getApplication()).setCashAmount(cashAmount);
            ((GlobalVariables)getApplication()).setBankAmount(bankAmount);
            totalAmount=((GlobalVariables)getApplication()).getTotalAmount();
            bankAmount=((GlobalVariables)getApplication()).getBankAmount();
            cashAmount=((GlobalVariables)getApplication()).getCashAmount();

            TextView textCashAmount = (TextView) findViewById(R.id.cashAmount);
            TextView textBankAmount = (TextView) findViewById(R.id.bankAmount);
            TextView textTotalAmount = (TextView) findViewById(R.id.totalAmount);

            textTotalAmount.setText(totalAmount.toString());
            textBankAmount.setText(bankAmount.toString());
            textCashAmount.setText(cashAmount.toString());
        }

    }

    public void updateLastDataInsert() {
        cursor = databaseContability.getAllData();
        if (cursor != null) {
            cursor.moveToLast();
                cid = cursor.getInt(0);
                DBAccount = cursor.getString(1);
                DBAmount = cursor.getDouble(2);
                DBDate = cursor.getString(3);
                DBReason = cursor.getString(4);
                if (DBAccount.equals("Cash Account")) {
                    cashAmount = cashAmount + DBAmount;
                    DBAccount = "Cash";

                } else {
                    bankAmount = bankAmount + DBAmount;
                    DBAccount = "Bank";

                }
                addMovement(cid, DBAccount, DBAmount, DBDate, DBReason,DBImageUri);
                populateList();

            }


        }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.action_settings:
                Intent intent=new Intent(MainActivity.this,SettingMenu.class);
                startActivity(intent);
                break;

        }
        return false;
    }







}





