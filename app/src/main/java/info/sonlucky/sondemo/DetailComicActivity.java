package info.sonlucky.sondemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class DetailComicActivity extends AppCompatActivity {
            WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView=new WebView(this);
        setContentView(webView);

        webView.getSettings().getJavaScriptEnabled();
        webView.loadUrl("https://www.truyenngan.com.vn/truyen-ngan/truyen-ngan-yeu.html");
       webView.setWebViewClient(new WebViewClient());
       webView.getSettings().setLoadWithOverviewMode(true);
       webView.getSettings().setUseWideViewPort(true);
       webView.getSettings().setBuiltInZoomControls(true);
       webView.getSettings().setDisplayZoomControls(false);

    }

    }

