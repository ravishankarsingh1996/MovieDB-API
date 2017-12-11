package com.app.pagination.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.pagination.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Ravi on 07-12-2017.
 */

public class DescriptionActivity extends AppCompatActivity {

    TextView mTextViewMovieName, mTextViewMovieReleaseDate, mTextViewMovieDescription;
    ImageView mImageViewMoviePic;

    String movieName, movieReleaseDate, movieDescription, moviePicUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        mTextViewMovieName = (TextView) findViewById(R.id.dTextView_movie_name);
        mTextViewMovieReleaseDate = (TextView) findViewById(R.id.dTextView_movie_release_date);
        mTextViewMovieDescription = (TextView) findViewById(R.id.dTextView_movie_description);
        mImageViewMoviePic = (ImageView) findViewById(R.id.dimageView_movie_description_image);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(!bundle.isEmpty()){
            movieName = bundle.getString("name_movie");
            movieReleaseDate = bundle.getString("release_date_movie");
            movieDescription = bundle.getString("description_movie");
            moviePicUrl = bundle.getString("picurl_movie");

            mTextViewMovieName.setText(movieName);
            mTextViewMovieReleaseDate.setText(movieReleaseDate);
            mTextViewMovieDescription.setText(movieDescription);
            Picasso.with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w500/" + moviePicUrl)
                    .into(mImageViewMoviePic);
        } else
        Toast.makeText(DescriptionActivity.this, "Activity Data passing Error",
                Toast.LENGTH_LONG).show();

    }
}
