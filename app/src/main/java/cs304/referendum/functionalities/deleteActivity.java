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

public class deleteActivity extends AppCompatActivity {
    TextView resultText;
    EditText query;

    String userName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button sendButton = (Button) findViewById(R.id.submit_button_delete);
        resultText=(TextView) findViewById(R.id.results_text_delete);
        query=(EditText) findViewById(R.id.query_delete);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = query.getText().toString();
                new QueryTask().execute();
            }
        });
    }

    private class QueryTask extends AsyncTask<Void, Void, Void> {
        private String voters = "";

        @Override
        protected Void doInBackground(Void... arg0) {
            try{
                JDBCConnection db = new JDBCConnection();
                ArrayList<Voter> result = db.deleteUserQuery(userName);
                for(Voter v : result){
                    voters = voters + v.getName() + " | " + v.getUserName() + " | " + v.getEmail() + "\n";
                }

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            resultText.setText(voters);
            super.onPostExecute(aVoid);
        }
    }

}
