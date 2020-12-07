package api.entities;

import java.util.List;

/**
 * @author vineetkumar
 * created 07/12/2020
 */

public class MovieList {
    private List<Movie> Search;
    private int totalResults;
    private boolean response;

    public List<Movie> getSearch() {
        return Search;
    }

    public void setSearch(List<Movie> search) {
        this.Search = search;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "MoviesList{" +
                "search=" + Search +
                ", totalResults=" + totalResults +
                ", response=" + response +
                '}';
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public static class Movie {
        public String Title;
        public String Year;
        public String imdbID;
        public String Type;
        public String Poster;

        public String getTitle() {
            return Title;
        }
        @Override
        public String toString() {
            return "Movie{" +
                    "Title='" + Title + '\'' +
                    ", Year='" + Year + '\'' +
                    ", imdbID='" + imdbID + '\'' +
                    ", Type=" + Type +
                    ", Poster='" + Poster + '\'' +
                    '}';
        }
    }

}