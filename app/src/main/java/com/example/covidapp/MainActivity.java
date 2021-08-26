package com.example.covidapp;
import com.example.covidapp.models.CovidRecord;
import com.example.covidapp.data.HistoryEntity;
import com.example.covidapp.data.HistoryRepository;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.covidapp.services.CovidService;

public class MainActivity extends AppCompatActivity{

    TextView txtCountry, txtLastUpdate, txtActive, txtConfirmed, txtRecovered,txtDeaths;
    EditText editCountry;
    Button btnHistory;
    HistoryRepository db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCountry = findViewById(R.id.txtCountry);
        txtLastUpdate = findViewById(R.id.txtLastUpdate);
        txtActive = findViewById(R.id.txtActive);
        txtConfirmed = findViewById(R.id.txtConfirmed);
        txtRecovered = findViewById(R.id.txtRecovered);
        editCountry = findViewById(R.id.editCountry);
        btnHistory = findViewById(R.id.btnHistory);
        txtDeaths = findViewById(R.id.txtDeaths);

        btnHistory.setOnClickListener(onHistoryClick);
        editCountry.setOnEditorActionListener(onCountryChanged);

        db = new HistoryRepository(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.loadInitial();
    }

    private void loadInitial() {
        HistoryEntity last = db.getLast();
        if (last != null) {
            GetCovidTask getTask = new GetCovidTask();
            getTask.setSave(false);
            getTask.execute(last.getCountry());
        }
    }

    private View.OnClickListener onHistoryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(MainActivity.this, HistoryActivity.class);
            MainActivity.this.startActivity(myIntent);
        }
    };

    private EditText.OnEditorActionListener onCountryChanged = new EditText.OnEditorActionListener(){

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != EditorInfo.IME_ACTION_DONE) {
                return false;
            }

            String country = editCountry.getText().toString();
            if(country.isEmpty() || country == null){
                return false;
            }

            new GetCovidTask().execute(country);

            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            return true;
        }
    };

    private void displayError(String error){
        txtLastUpdate.setText("");
        txtCountry.setText(error);
        txtLastUpdate.setText("");
        txtActive.setText("");
        txtConfirmed.setText("");
        txtRecovered.setText("");
        txtDeaths.setText("");
    }

    private class GetCovidTask extends AsyncTask<String,Void, CovidRecord>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        private boolean save = true;

        public void setSave(boolean save) {
            this.save = save;
        }

        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            pd.setTitle("Executing...");
            pd.show();
        }

        @Override
        protected CovidRecord doInBackground(String... params) {
            String country = params[0];
            CovidService http = new CovidService();
            return http.getData(country);
        }

        @Override
        protected void onPostExecute(CovidRecord covidRecord) {
            super.onPostExecute(covidRecord);

            if(covidRecord == null){
                pd.dismiss();
                displayError("Could Not Find Country");
                return;
            }

            if(save){
                save(covidRecord);
            }

            show(covidRecord);
            pd.dismiss();
        }

        private void save(CovidRecord map){
            HistoryEntity entity = new HistoryEntity();
            entity.setUpdateDate(map.getLastUpdate());
            entity.setCountry(map.getCountry());

            db.insert(entity);
        }

        private void show(CovidRecord map){
            txtCountry.setText(map.getCountry());
            txtLastUpdate.setText(map.getLastUpdate());
            txtActive.setText("Active: " +map.getActive());
            txtConfirmed.setText("Confirmed: "+map.getConfirmed());
            txtRecovered.setText("Recovered: "+map.getRecovered());
            txtDeaths.setText("Deaths: "+map.getDeaths());
            editCountry.setText("");
        }
    }
}