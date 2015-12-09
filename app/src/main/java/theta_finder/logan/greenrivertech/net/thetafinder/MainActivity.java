package theta_finder.logan.greenrivertech.net.thetafinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText x_coord = (EditText) findViewById(R.id.x_coordinate);
        EditText y_coord = (EditText) findViewById(R.id.y_coordinate);

        x_coord.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                EditText x_coord = (EditText) findViewById(R.id.x_coordinate);
                EditText y_coord = (EditText) findViewById(R.id.y_coordinate);

                // temporary text
                TextView answer = (TextView) findViewById(R.id.result);
                answer.setText("Calculating. . .");

                String stringUrl = "http://logan.greenrivertech.net/IT/405/theta.php?x="
                        + x_coord.getText().toString() + "&y=" + y_coord.getText().toString();

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    // fetch data
                    new DownloadWebpageTask().execute(stringUrl);

                } else {
                    // display error
                    answer.setText("No Connection");
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        y_coord.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                EditText x_coord = (EditText) findViewById(R.id.x_coordinate);
                EditText y_coord = (EditText) findViewById(R.id.y_coordinate);

                // temporary text
                TextView answer = (TextView) findViewById(R.id.result);
                answer.setText("Calculating. . .");

                String stringUrl = "http://logan.greenrivertech.net/IT/405/theta.php?x="
                        + x_coord.getText().toString() + "&y=" + y_coord.getText().toString();

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    // fetch data
                    new DownloadWebpageTask().execute(stringUrl);

                } else {
                    // display error
                    answer.setText("No Connection");
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid: " + e.getMessage();
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            TextView answer = (TextView) findViewById(R.id.result);
            answer.setText(result);
        }
    }

    public String downloadUrl(String url) throws IOException
    {
        URL resource;
        HttpURLConnection connection;

        try {
            resource = new URL(url);
            connection = (HttpURLConnection) resource.openConnection();

            InputStream in = new BufferedInputStream(connection.getInputStream());

            String response = readIt(in);
            connection.disconnect();

            return response;
        }
        catch (MalformedURLException e){
            throw new IOException("Malformed URL");
        }
    }

    public String readIt(InputStream in) throws IOException {
        String stringResult;

        // throws exception if out of bounds.
        byte[] buffer = new byte[100];

        while (in.read(buffer) != -1);

        stringResult = new String(buffer, "UTF-8");

        if (!stringResult.toLowerCase().contains("undefined"))
            stringResult += "°";

        return stringResult;
    }

    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
