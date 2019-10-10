package info.sonlucky.sondemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import info.sonlucky.sondemo.adapter.ComicAdapter;

import static android.content.Intent.EXTRA_TEXT;

public class MainActivity extends AppCompatActivity {
    private ComicAdapter comicAdapter;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private ImageView imageView;
    private View main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Trang chủ");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setLogo(R.mipmap.ic_launcher);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.nav_fb:
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://www.facebook.com/hong.son.18488"));
                                startActivity(intent);
                                break;
                            case R.id.nav_share:
                                Intent intent1 = new Intent();
                                intent1.setAction(Intent.ACTION_SEND);
                                intent1.setType("text/plain");
                                startActivity(Intent.createChooser(intent1, "Share"));
                                break;
                            case R.id.nav_category:
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                break;
                            case R.id.nav_favourite:
                                Intent intent2 = new Intent();
                                intent2.setAction(Intent.ACTION_VIEW);
                                intent2.setData(Uri.parse("https://support.google.com"));
                                startActivity(intent2);
                                break;
                        }
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case  R.id.menu2:
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                break;
            case  R.id.menu3:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1, "Send FeedBack"));
                break;
        }
        return super.onOptionsItemSelected(item);
        }
    {
        new LoadComic().execute("https://www.truyenngan.com.vn/truyen-ngan/truyen-ngan-yeu.html");
    }

    private class LoadComic extends AsyncTask<String, Void, ArrayList<Comic>> {
        @Override
        protected ArrayList<Comic> doInBackground(String... strings) {
            Document document = null;
            ArrayList<Comic> comics = new ArrayList<>();
            try {
                int x = 0;
                document = Jsoup.connect(strings[0]).get();
                if (document != null) {
                    Elements sub = document.select("div.box");
                    for (Element element : sub) {
                        Element nameComicSubject = element.getElementsByTag("h3").first();
                        Element imgComicSubject = element.getElementsByTag("img").first();
                        Element descComicSubject = element.select("div.clear").first();
                        Element linkComicSubject = element.getElementsByTag("a").first();

                        Comic comic = new Comic(nameComicSubject.text(), imgComicSubject.attr("src"), descComicSubject.text(), linkComicSubject.attr("href"));
                        comics.add(comic);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return comics;
        }
        @Override
        protected void onPostExecute(ArrayList<Comic> comics) {
            super.onPostExecute(comics);
            comicAdapter = new ComicAdapter(MainActivity.this, 0, comics);
            listView.setAdapter(comicAdapter);

        }
    }
}


