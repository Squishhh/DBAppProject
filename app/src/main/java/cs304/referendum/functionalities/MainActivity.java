package cs304.referendum.functionalities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cs304.referendum.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    TextView resultText;
    EditText query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//selectionFragment button

//        resultText=(TextView) findViewById(R.id.results_text_selection);
//        query=(EditText) findViewById(R.id.query_selection);
//
//        Button querySubmit=(Button) findViewById(R.id.submit_button_selection);
//        querySubmit.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                String fromQuery=query.getText().toString();
//                //TODO something with the query
//                resultText.setText("WHERE THE FK ARE THE RESULTS");
//            }
//        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_selection) {
            // Handle the camera action
            Toast.makeText(this, "selection",Toast.LENGTH_SHORT).show();
//            selectionFragment selectionFrag= selectionFragment.newInstance("smt1","smt2");
//            replaceFragment(selectionFrag,R.id.selectionFragment);
            Intent aggregation=new Intent(MainActivity.this,selectActivity.class);
            startActivity(aggregation);
        } else if (id == R.id.nav_join) {
            Toast.makeText(this, "Join",Toast.LENGTH_SHORT).show();
//            joinFragment joinFrag= joinFragment.newInstance("smt1","smt2");
//            replaceFragment(joinFrag,R.id.joinFragment);
            Intent aggregation=new Intent(MainActivity.this,joinActivity.class);
            startActivity(aggregation);
        } else if (id == R.id.nav_division) {
            Toast.makeText(this, "Division",Toast.LENGTH_SHORT).show();
//            divisionFragment divisionFrag= divisionFragment.newInstance("smt1","smt2");
//            replaceFragment(divisionFrag,R.id.divisionFragement);
            Intent aggregation=new Intent(MainActivity.this,divisionActivity.class);
            startActivity(aggregation);
        } else if (id == R.id.nav_aggregation) {
            Toast.makeText(this, "Aggregation",Toast.LENGTH_SHORT).show();
//            aggregationFragment aggregationFrag= aggregationFragment.newInstance("smt1","smt2");
//            replaceFragment(aggregationFrag,R.id.aggregationFragment);

            Intent aggregation=new Intent(MainActivity.this,aggregationActivity.class);
            startActivity(aggregation);

        } else if (id == R.id.nav_nestedAgg) {
            Toast.makeText(this, "Nested Aggregation",Toast.LENGTH_SHORT).show();
//            nestedAggregfationFragment Frag= nestedAggregfationFragment.newInstance("smt1","smt2");
//            replaceFragment(Frag,R.id.nestedAggregationFragment);
            Intent aggregation=new Intent(MainActivity.this,nestedAggregationActivity.class);
            startActivity(aggregation);
        } else if (id == R.id.nav_delete) {
            Toast.makeText(this, "Deletion",Toast.LENGTH_SHORT).show();
//            deleteFragment delFrag= deleteFragment.newInstance("smt1","smt2");
//            replaceFragment(delFrag,R.id.deleteFragment);
            Intent aggregation=new Intent(MainActivity.this,deleteActivity.class);
            startActivity(aggregation);
        }else if (id == R.id.nav_update) {
            Toast.makeText(this, "Update",Toast.LENGTH_SHORT).show();
//            updateFragment upFrag= updateFragment.newInstance("smt1","smt2");
//            replaceFragment(upFrag,R.id.updateFragment);
            Intent aggregation=new Intent(MainActivity.this,updateActivity.class);
            startActivity(aggregation);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment, int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relative_layout_for_fragment, fragment, fragment.getTag()).commit();
    }
}
