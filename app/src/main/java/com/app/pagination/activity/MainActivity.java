package com.app.pagination.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.pagination.R;
import com.app.pagination.model.Genre;
import com.app.pagination.model.Movie;
import com.app.pagination.utils.ConnectionChecker;

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
    List genreList = new ArrayList<>();
    List<Genre> genresList ;

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    ProgressBar progressBarMainContentLoading, progressBarPaginationLoading;
    private boolean loading = true;
    LinearLayoutManager mLayoutManager;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    Integer page = 1, pagination = 0;
    Integer result = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    Movie movie;
    private boolean mNetworkStateCheck;
    String genreIntentJSONObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        genreIntentJSONObject = getIntent().getStringExtra("GENRES_JSON_OBJECT");
        parseJSONGenreData(genreIntentJSONObject);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        ConnectionChecker cc = new ConnectionChecker();
        mNetworkStateCheck = cc.checkInternetConenction(connectivityManager);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_main_activity);
        progressBarMainContentLoading = (ProgressBar) findViewById(R.id.progressBar_main_activity);
        progressBarPaginationLoading = (ProgressBar) findViewById(R.id.progressBar_main_activity_pagination);
        progressBarMainContentLoading.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mNetworkStateCheck) {
                    page = 1;
                    pagination = 0;
                    movieList = new ArrayList<>();
                    //genresList = new ArrayList<>();
                    new GetJSONData().execute();
                } else
                    showCustomToastAlert();

            }
        });
        if (mNetworkStateCheck) {
            new GetJSONData().execute();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) {
                        visibleItemCount = mLayoutManager.getChildCount();
                        totalItemCount = mLayoutManager.getItemCount();
                        pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                        Log.v("...", "Loggggg");

                        if (loading) {
                            if ((visibleItemCount + pastVisibleItems) == totalItemCount) {
                                //loading = false;
                                //Toast.makeText(MainActivity.this, "Last", Toast.LENGTH_LONG).show();
                                //TODO pagination &  fetch new data
                                progressBarPaginationLoading.setVisibility(View.VISIBLE);
                                if (mNetworkStateCheck) {
                                    page = page + 1;
                                    pagination = 1;
                                    new GetJSONData().execute();
                                } else
                                    showCustomToastAlert();
                            }
                        }
                    }
                }
            });
        } else {
            showCustomToastAlert();
            //progressBarMainContentLoading.setVisibility(View.INVISIBLE);
        }
    }

    private void parseJSONGenreData(String genreJSONObject) {

        try {
            JSONObject jsonObject = new JSONObject(genreJSONObject);
            JSONArray jsonArray = jsonObject.optJSONArray("genres");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectGenre = jsonArray.getJSONObject(i);
                Genre genre = new Genre();
                genre.setmStringGenreId(jsonObjectGenre.optString("id"));
                genre.setmStringGenreName(jsonObjectGenre.optString("name"));
                Log.d("GenreID",jsonObjectGenre.optString("id"));
                Log.d("GenreName",jsonObjectGenre.optString("name"));
                genreList.add(genre);
                genresList = genreList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void showCustomToastAlert() {

        Context context = getApplicationContext();
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams")
        View toastRoot = inflater.inflate(R.layout.toast_for_connection_error, null);
        Toast toast = new Toast(context);
        toast.setView(toastRoot);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
                0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }


    @SuppressLint("StaticFieldLeak")
    public class GetJSONData extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            String urlLink = "http://api.themoviedb.org/3/discover/movie?" +
                    "api_key=2c0bb3ae2ba96a469724d0c25bd4844e&page=" + page;
            HttpURLConnection httpsURLConnection;
            result = 0;

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
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Integer integer) {

            if (integer == 1 && pagination == 0) {
                movieAdapter = new MovieAdapter(getApplicationContext(), movieList);
                recyclerView.setItemAnimator(new SlideInUpAnimator());
                // movieAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(movieAdapter);
                //movieAdapter.notifyItemInserted(totalItemCount);
                progressBarMainContentLoading.setVisibility(View.INVISIBLE);
                progressBarPaginationLoading.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

            } else if (integer == 1 && pagination == 1) {
                //commented below line to avoid the data reload and view from start every time
                // and create a new instance of MovieAdapter
                //movieAdapter = new MovieAdapter(getApplicationContext(), movieList);
                recyclerView.setItemAnimator(new SlideInUpAnimator());
                recyclerView.swapAdapter(movieAdapter, false);
                // movieAdapter.notifyItemInserted(totalItemCount);
                progressBarMainContentLoading.setVisibility(View.INVISIBLE);
                progressBarPaginationLoading.setVisibility(View.GONE);
            } else
                Toast.makeText(MainActivity.this, "Failed to load JSON data",
                        Toast.LENGTH_LONG).show();
        }

        private void parseJSONData(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.optJSONArray("results");
                //if ( pagination == 1)
                // movieList = new ArrayList<>();

                String genres = "";

                for (int i = 0; i < jsonArray.length(); i++) {
                    String mStringGenres="";
                    JSONObject post = jsonArray.getJSONObject(i);
                    movie = new Movie();
                    movie.setmStringMovieName(post.optString("original_title"));
                    movie.setmStringMovieDescription(post.optString("overview"));
                    movie.setmStringMovieReleaseDate(post.optString("release_date"));
                    movie.setmStringMoviePicURL(post.optString("poster_path"));
                    movie.setmStringMovieRating(post.optString("vote_average"));
                    movie.setmStringMovieID(post.optString("id"));
                    movie.setmStringMovieBackgroundImagePath(post.optString("backdrop_path"));
                    JSONArray genresArray = post.getJSONArray("genre_ids");
                    //TODO check for multiple asssignments in genre value on swipe refresh
                   // movie.setmStringMovieGenres("Horror");
                    for (int j = 0; j < genresArray.length(); j++) {
                       // genres = genres + genresArray.optString(i);
                        //Log.d("Genre", genre.getmStringGenreName());

                            for (int k = 0; k < genresList.size(); k++) {
                                Genre genre = genresList.get(k);

                                if(j == genresArray.length()-1){
                                if (genresArray.optString(j).equals(genre.getmStringGenreId())) {
                                    mStringGenres = mStringGenres + genre.getmStringGenreName() ;
                                    Log.d("Genre2", genresArray.optString(j));
                                }
                                } else {
                                    if (genresArray.optString(j).equals(genre.getmStringGenreId())) {
                                        mStringGenres = mStringGenres + genre.getmStringGenreName()+ ",";
                                        Log.d("Genre", genresArray.optString(j) );

                                    }
                                }
                                Log.d("K", ""+k );
                            }
                        Log.d("Genre", mStringGenres);
                        Log.d("j", ""+genresArray.length());

                    }
                    //mStringGenres = mStringGenres.substring(0, mStringGenres.length() - 1);
                    movie.setmStringMovieGenres(mStringGenres);

                    movieList.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
