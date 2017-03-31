package cs304.referendum.functionalities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cs304.referendum.R;
import cs304.referendum.db.JDBCConnection;
import cs304.referendum.utils.Voter;
import cs304.referendum.utils.VoterRef;

public class updateActivity extends AppCompatActivity {

    TextView resultText;
    EditText query_userid;
    EditText query_new_username;

    String curUserName = "";
    String newUserName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button sendButton = (Button) findViewById(R.id.submit_button_update);
        resultText=(TextView) findViewById(R.id.results_text_update);
        query_userid=(EditText) findViewById(R.id.query_update_userid);
        query_new_username=(EditText) findViewById(R.id.query_update_new_username);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curUserName = query_userid.getText().toString();
                newUserName = query_new_username.getText().toString();
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
                ArrayList<Voter> result = db.updateUserName(curUserName, newUserName);
                for(Voter v : result){
                    voters = voters + v.getName() + " | " + v.getUserName() + " | " + v.getEmail() + "\n";
                }

            } catch (Exception e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(updateActivity.this, "No Fucks Allowed :^)", Toast.LENGTH_SHORT).show();
                    }
                });
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
