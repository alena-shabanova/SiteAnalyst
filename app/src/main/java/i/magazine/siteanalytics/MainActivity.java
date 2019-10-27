package i.magazine.siteanalytics;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TableLayout;

import org.jsoup.Jsoup;
 import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public Elements content; //
    public Element table;
    public Elements idx;
    public ArrayList<String> titleContent = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private GridView gridvw;
    ArrayList<String> list = new ArrayList<String>();


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      // listView = (ListView) findViewById(R.id.listview);
        gridvw = findViewById(R.id.gridview);
        new NewContent().execute();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

    }
    public class NewContent extends AsyncTask <String, Void, String>
    {
        @Override
        protected String doInBackground(String... arg) {

            Document doc = null;
            try {
                doc =  Jsoup.connect("https://telemetr.me/channels/?order_column=views_per_post&order_direction=DESC").get();
                // content = doc.select(".th_header");
                table  = doc.select(".table-responsive").first();
                // разбиваем строки по тегу
                idx     =  table.select("tr");
               // titleContent.clear();
                for (int i =0; i < idx.size(); i++){
                    Element row = idx.get(i); // строки
                    Elements cols = row.select("td"); // столбцы
                    for (Element e: cols) {
                        list.add(e.text().toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute (String result) {
           gridvw.setAdapter(adapter);
        }
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
