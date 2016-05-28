package com.example.cbenedict.mynytsearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.cbenedict.mynytsearch.Article;
import com.example.cbenedict.mynytsearch.ArticleArrayAdapter;
import com.example.cbenedict.mynytsearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText etQuery;
    GridView gvResults;
    Button btnSearch;

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
    }

    public void setupViews(){
        etQuery = (EditText)findViewById(R.id.etQuery);
        gvResults = (GridView)findViewById(R.id.gvResults);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);
        //hook up listener for grid click
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public  void  onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Create an intent to display the article
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                //get the article to display
                Article article = articles.get(position);
                //pass that article into the intent
                i.putExtra("article", article);
                //launch the activity
                startActivity(i);
            }
        });
    }


    public void onArticleSearch(View view) {
        String query = etQuery.getText().toString();
        //Toast.makeText(this,"Searching for" + query, Toast.LENGTH_LONG).show();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        //params.put("api-key", "e863004d8ddc2900d7111aa358a8bdbb:19:4701790");
        params.put("api-key", "269ef11054dd4bf6b98665a772d602a7");
        params.put("page", 0);
        params.put("q", query);
        client.get(url,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;
                try{
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJSONArray(articleJsonResults));
                    //adapter.notifyDataSetChanged();
                    Log.d("DEBUG", articleJsonResults.toString());

                }catch (JSONException e){

                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search,menu);
        return true;
        }
}
