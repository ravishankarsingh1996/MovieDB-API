package com.app.pagination;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Ravi on 07-12-2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    List<Movie> movieList;
    Context context;


    public MovieAdapter(Context context, List<Movie> movieList) {
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
        holder.textViewMovieReleaseDate.setText(movie.getmStringMovieReleaseDate());
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + movie.getmStringMoviePicURL())
                .into(holder.imageViewMoviePic);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMovieName, textViewMovieReleaseDate;
        ImageView imageViewMoviePic;

        public ViewHolder(final View itemView) {
            super(itemView);
            textViewMovieName = itemView.findViewById(R.id.textView_movie_name);
            textViewMovieReleaseDate = itemView.findViewById(R.id.textView_movie_release_date);
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
