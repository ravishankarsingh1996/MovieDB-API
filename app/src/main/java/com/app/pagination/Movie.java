package com.app.pagination;

/**
 * Created by Ravi on 07-12-2017.
 */

public class Movie {

    private String mStringMovieName;
    private String mStringMovieReleaseDate;
    private String mStringMoviePicURL;
    private String mStringMovieDescription;
    private String mStringMovieID;
    private String mStringMovieRating;
    private String mStringMovieGenres;

    public Movie(){

    }

    public Movie(String mStringMovieName, String mStringMovieReleaseDate,
                 String mStringMoviePicURL, String mStringMovieDescription, String mStringMovieID){
        this.mStringMovieName = mStringMovieName;
        this.mStringMovieReleaseDate = mStringMovieReleaseDate;
        this.mStringMovieDescription = mStringMovieDescription;
        this.mStringMoviePicURL = mStringMoviePicURL;
        this.mStringMovieID = mStringMovieID;
    }

    public String getmStringMovieName() {
        return mStringMovieName;
    }

    public void setmStringMovieName(String mStringMovieName) {
        this.mStringMovieName = mStringMovieName;
    }

    public String getmStringMovieReleaseDate() {
        return mStringMovieReleaseDate;
    }

    public void setmStringMovieReleaseDate(String mStringMovieReleaseDate) {
        this.mStringMovieReleaseDate = mStringMovieReleaseDate;
    }

    public String getmStringMoviePicURL() {
        return mStringMoviePicURL;
    }

    public void setmStringMoviePicURL(String mStringMoviePicURL) {
        this.mStringMoviePicURL = mStringMoviePicURL;
    }

    public String getmStringMovieDescription() {
        return mStringMovieDescription;
    }

    public void setmStringMovieDescription(String mStringMovieDescription) {
        this.mStringMovieDescription = mStringMovieDescription;
    }

    public String getmStringMovieID() {
        return mStringMovieID;
    }

    public void setmStringMovieID(String mStringMovieID) {
        this.mStringMovieID = mStringMovieID;
    }

    public String getmStringMovieRating() {
        return mStringMovieRating;
    }

    public void setmStringMovieRating(String mStringMovieRating) {
        this.mStringMovieRating = mStringMovieRating;
    }

    public String getmStringMovieGenres() {
        return mStringMovieGenres;
    }

    public void setmStringMovieGenres(String mStringMovieGenres) {
        this.mStringMovieGenres = mStringMovieGenres;
    }
}
