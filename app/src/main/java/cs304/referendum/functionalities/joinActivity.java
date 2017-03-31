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
import cs304.referendum.utils.Voter;
import cs304.referendum.utils.VoterRef;

public class joinActivity extends AppCompatActivity {

    TextView resultText;
    EditText query_begYr;
    EditText query_endYr;

    Integer startYear = 0;
    Integer endYear = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button sendButton = (Button) findViewById(R.id.submit_button_selection);
        resultText=(TextView) findViewById(R.id.results_text_selection);
        query_begYr=(EditText) findViewById(R.id.query_selection_start_year);
        query_endYr=(EditText) findViewById(R.id.query_selection_end_year);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startYear = Integer.parseInt(query_begYr.getText().toString());
                endYear = Integer.parseInt(query_endYr.getText().toString());
                new QueryTask().execute();
            }
        });
    }

    private class QueryTask extends AsyncTask<Void, Void, Void> {
        private String voterRef = "";

        @Override
        protected Void doInBackground(Void... arg0) {
            try{
                JDBCConnection db = new JDBCConnection();
                ArrayList<VoterRef> result = db.getVoterRef(startYear, endYear);
                for(VoterRef vr : result){
                    voterRef = voterRef + vr.getUserName() + " | " + vr.getRefDescription() + " | " + vr.getRefYear() + "\n";
                }

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            resultText.setText(voterRef);
            super.onPostExecute(aVoid);
        }
    }

}
