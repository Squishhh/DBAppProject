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

public class selectActivity extends AppCompatActivity {

    TextView resultText;
    EditText query_description;
    EditText query_year;

    String description = "";
    Integer year = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button sendButton = (Button) findViewById(R.id.submit_button_selection);
        resultText=(TextView) findViewById(R.id.results_text_selection);

        query_description=(EditText) findViewById(R.id.query_aggegation_description);
        query_year = (EditText) findViewById(R.id.query_aggegation_year);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = query_description.getText().toString();
                year = Integer.parseInt(query_year.getText().toString());
                new QueryTask().execute();
            }
        });
    }

    private class QueryTask extends AsyncTask<Void, Void, Void> {
        private String desc = "";

        @Override
        protected Void doInBackground(Void... arg0) {
            try{
                JDBCConnection db = new JDBCConnection();
                ArrayList<String> result = db.getDescription(description, year);
                for(String d : result){
                    desc = desc + d + "\n";
                }

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            resultText.setText(desc);
            super.onPostExecute(aVoid);
        }
    }

}
