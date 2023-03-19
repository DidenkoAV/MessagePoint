package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SearchPojo {
    @JsonProperty("Search")
    public ArrayList<SearchPojo> search;
    public String totalResults;
    @JsonProperty("Response")
    public String response;
    @JsonProperty("Title")
    public String title;
    @JsonProperty("Year")
    public String year;
    public String imdbID;
    @JsonProperty("Type")
    public String type;
    @JsonProperty("Poster")
    public String poster;
}


