package com.app.pagination.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.pagination.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Ravi on 07-12-2017.
 */

public class DescriptionActivity extends AppCompatActivity {

    TextView mTextViewMovieName, mTextViewMovieReleaseDate, mTextViewMovieDescription;
    ImageView mImageViewMoviePic, mImageViewMovieBackgroundPic;

    String movieName, movieReleaseDate, movieDescription, moviePicUrl, movieBackgroundPicUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        mTextViewMovieName = (TextView) findViewById(R.id.dTextView_movie_name);
        mTextViewMovieReleaseDate = (TextView) findViewById(R.id.dTextView_movie_release_date);
        mTextViewMovieDescription = (TextView) findViewById(R.id.dTextView_movie_description);
        mImageViewMoviePic = (ImageView) findViewById(R.id.dimageView_movie_description_image);
        mImageViewMovieBackgroundPic = (ImageView) findViewById(R.id.image_view_description_activity);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(!bundle.isEmpty()){
            movieName = bundle.getString("name_movie");
            movieReleaseDate = bundle.getString("release_date_movie");
            movieDescription = bundle.getString("description_movie");
            moviePicUrl = bundle.getString("picurl_movie");
            movieBackgroundPicUrl = bundle.getString("backdrop_path");
            mTextViewMovieName.setText(movieName);
            mTextViewMovieReleaseDate.setText(movieReleaseDate);
            mTextViewMovieDescription.setText(movieDescription);
            Picasso.with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w500/" + movieBackgroundPicUrl)
                    .into(mImageViewMoviePic);
            Picasso.with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w500/" + moviePicUrl)
                   // .fit()
                    .centerCrop()
                    .resize(250,500)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            mImageViewMovieBackgroundPic.setBackgroundDrawable(new BitmapDrawable(
                                    getApplicationContext().getResources(), bitmap));
                            getBitmapColor(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        } else
        Toast.makeText(DescriptionActivity.this, "Activity Data passing Error",
                Toast.LENGTH_LONG).show();

    }
    public void getBitmapColor(Bitmap bitmap){

        Palette.from(bitmap)
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch swatch = palette.getDominantSwatch();
                        mImageViewMovieBackgroundPic.setColorFilter(swatch.getRgb());
                    }
                });
    }
}
