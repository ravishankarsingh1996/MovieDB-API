package com.app.pagination.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pagination.R;
import com.app.pagination.model.Genre;
import com.app.pagination.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ravi on 07-12-2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    List<Movie> movieList;
    Context context;


    MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.textViewMovieName.setText(movie.getmStringMovieName());
        holder.textViewMovieRating.setText(movie.getmStringMovieRating());
        holder.textViewMovieGenres.setText(movie.getmStringMovieGenres());
        holder.textViewMovieDescription.setText(movie.getmStringMovieDescription());
        holder.textViewMovieReleaseDate.setText(movie.getmStringMovieReleaseDate());
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + movie.getmStringMoviePicURL())
                .into(holder.imageViewMoviePic);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMovieName, textViewMovieReleaseDate, textViewMovieRating, textViewMovieGenres, textViewMovieDescription;
        ImageView imageViewMoviePic;

        ViewHolder(final View itemView) {
            super(itemView);
            textViewMovieName = itemView.findViewById(R.id.textView_movie_name);
            textViewMovieReleaseDate = itemView.findViewById(R.id.textView_movie_release_date);
            textViewMovieRating = itemView.findViewById(R.id.textView_movie_rating);
            textViewMovieGenres = itemView.findViewById(R.id.textView_movie_genres);
            textViewMovieDescription = itemView.findViewById(R.id.textView_movie_description);
            imageViewMoviePic = itemView.findViewById(R.id.imageView_movie_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {

                        Movie clickedMovieItem = movieList.get(pos);
                        //Toast.makeText(context, clickedMovieItem.getmStringMovieName(),
                        // Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, DescriptionActivity.class);
                        intent.putExtra("name_movie",
                                clickedMovieItem.getmStringMovieName());
                        intent.putExtra("release_date_movie",
                                clickedMovieItem.getmStringMovieReleaseDate());
                        intent.putExtra("picurl_movie",
                                clickedMovieItem.getmStringMoviePicURL());
                        intent.putExtra("description_movie",
                                clickedMovieItem.getmStringMovieDescription());
                        context.startActivity(intent);
                    }
                }
            });
        }

    }
}
