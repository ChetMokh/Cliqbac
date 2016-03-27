package co.thepartyon.cliqbac;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

public class PartyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String results="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Party");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new LongRunningGetIO().execute();
    }


    private class LongRunningGetIO extends AsyncTask<Void, Void, String> {

        // method which store the response from server into the results variable
        protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n > 0) {
                byte[] b = new byte[4096];
                n = in.read(b);
                if (n > 0)
                    out.append(new String(b, 0, n));
            }
            results = out.toString();
            // passing data to getData() method to parse the JSON response
            getData(results);
            return out.toString();
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
                     /*
                      * Here remember to give local address of server as 10.0.2.2 in
                      * emulator localhost not going to work HttpGetServlet is the
 * servlet that handles my request to view database HttpGetData
                      * is the .java file of HttpGetServlet servlet
                      */
            HttpGet httpGet = new HttpGet(
                    "localhost:8080/webapplication/webapi/parties");
            String text = null;
            try {
                // get the response from the server
                HttpResponse response = httpClient.execute(httpGet,
                        localContext);
                HttpEntity entity = response.getEntity();
                // call above method to obtain the response in an String
                text = getASCIIContentFromEntity(entity);
            } catch (Exception e) {
                e.printStackTrace();
                return e.getLocalizedMessage();
            }
            return text;
        }
        protected void onPostExecute(String results) {
            // this is the method that automatically calls
            // after doInBackGround() get called
        }
    }

    public void getData(String results) {

        if (results == "") {
            // if the result obtained is blank
            Toast.makeText(getApplicationContext(),
                    "Sorry No data in database", Toast.LENGTH_SHORT).show();
        } else {
            try {
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText(results);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            startActivity(new Intent(this, HomeActivity.class));
        } else if (id == R.id.live) {

        } else if (id == R.id.future) {
            startActivity(new Intent(this, PartyListActivity.class));
        } else if (id == R.id.past) {
            startActivity(new Intent(this, PartyListActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
