package cs304.referendum.functionalities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import cs304.referendum.R;
import cs304.referendum.db.JDBCConnection;

public class aggregationActivity extends AppCompatActivity {

    TextView resultText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggregation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button sendButton = (Button) findViewById(R.id.submit_button_aggregation);
        resultText=(TextView) findViewById(R.id.results_text_aggregation);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new QueryTask().execute();
            }
        });
    }

    private class QueryTask extends AsyncTask<Void, Void, Void> {
        private String regUser = "";

        @Override
        protected Void doInBackground(Void... arg0) {
            try{
                JDBCConnection db = new JDBCConnection();
                Integer result = db.getRegisteredUsers();
                regUser = result.toString();

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            resultText.setText(regUser);
            super.onPostExecute(aVoid);
        }
    }
}
