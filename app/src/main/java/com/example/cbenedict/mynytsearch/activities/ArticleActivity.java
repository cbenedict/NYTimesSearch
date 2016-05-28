package com.example.cbenedict.mynytsearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cbenedict.mynytsearch.Article;
import com.example.cbenedict.mynytsearch.R;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        Article article = (Article)getIntent().getSerializableExtra("article");
        WebView webView = (WebView)findViewById(R.id.wvArticle);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(article.getWebUrl());
    }
}
