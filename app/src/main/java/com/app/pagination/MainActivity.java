package com.app.pagination;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class MainActivity extends AppCompatActivity {

    List movieList = new ArrayList<>();
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_main_activity);
        progressBar.setVisibility(View.VISIBLE);
        new GetJSONData().execute();
    }


    private class GetJSONData extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            String urlLink = "http://api.themoviedb.org/3/discover/movie?" +
                    "api_key=2c0bb3ae2ba96a469724d0c25bd4844e";
            HttpURLConnection httpsURLConnection;
            Integer result = 0;

            try {
                URL url = new URL(urlLink);
                httpsURLConnection = (HttpURLConnection) url.openConnection();
                int statusCode = httpsURLConnection.getResponseCode();
                if (statusCode == 200) {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(httpsURLConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    parseJSONData(stringBuilder.toString());
                    result = 1;
                } else {
                    result = 0;
                }

            } catch (MalformedURLException e) {
                Log.d("MalformedURLException", e.getLocalizedMessage());
            } catch (IOException e) {
                Log.d("IOException", e.getLocalizedMessage());
                //e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {

            if (integer == 1) {
                movieAdapter = new MovieAdapter(getApplicationContext(), movieList);
                recyclerView.setItemAnimator(new SlideInUpAnimator());
                recyclerView.setAdapter(movieAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            } else
                Toast.makeText(MainActivity.this, "Failed to load JSON data",
                        Toast.LENGTH_LONG).show();
        }

        private void parseJSONData(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.optJSONArray("results");
                movieList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject post = jsonArray.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setmStringMovieName(post.optString("original_title"));
                    movie.setmStringMovieDescription(post.optString("overview"));
                    movie.setmStringMovieReleaseDate(post.optString("release_date"));
                    movie.setmStringMoviePicURL(post.optString("poster_path"));
                    movie.setmStringMovieID(post.optString("id"));
                    movieList.add(movie);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
