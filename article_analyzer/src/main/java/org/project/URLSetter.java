package org.project;

import java.net.MalformedURLException;
import java.net.URL;
/**
 * This class allows to set the url with some given parameters (tags, query, page, page size, api key, base url )
 * for the api request.
 * It should be used to keep track of the parameters and to get a new url.
 */
public class URLSetter {
    /** Representation of a URL that is built in this class.*/
    private URL URL;
    /** Value of the API key of the media Group API endpoint.*/
    private String APIKey;
    /** Queries used to search among the set of elements in the response.*/
    private String[] queries;
    /** Fields used to determine the specific set of element given in the response.*/
    private String[] tags;
    /** Base URL without any specification.*/
    private String baseURL;
    /** Current page, a generic group of elements in the response.*/
    private int page;
    /** Number of articles that should be included in the API response.*/
    private int pageSize;

    /**
     * Takes all the information needed to create a valid URL based on the passed parameters .
     *
     * @param baseURL sets {@link URLSetter#baseURL}
     * @param pageSize sets {@link URLSetter#pageSize}
     * @param APIKey sets {@link URLSetter#APIKey}
     * @param page sets {@link URLSetter#page}
     * @param queries sets {@link URLSetter#queries}
     * @param tags sets {@link URLSetter#tags}
     * @throws IllegalArgumentException if there is no coherence in pageSize and page variables.
     */
    public URLSetter(String baseURL, String APIKey, int page, int pageSize, String[] queries , String[] tags) throws IllegalArgumentException, MalformedURLException{
        this.baseURL = baseURL;
        this.APIKey = APIKey;
        this.tags = tags;
        this.page = page;
        this.queries = queries;
        this.pageSize = pageSize;
        buildUrl();
    }
    /** Used by the URLSetter to build and store the final {@link URLSetter#URL}.  */
    private void buildUrl() throws MalformedURLException{
        // Setting a base URL.
        String URLString = baseURL;

        //Setting the request type as a search.
        URLString += "/search?";

        // Setting the page and checking for coherence.
        if(page > pageSize){
            throw new IllegalArgumentException();
        }
        URLString += "page-size=" + pageSize;
        if(page != 0) {
            URLString += "&page=" + page;
        }
        URLString += "&show-fields=body,headline,wordcount";

        // Setting tags and queries.
        if(tags.length != 0) {
            URLString += "&tag=";
            URLString += tags[0];
            for (int i = 1; i < tags.length; i++) {
                URLString += "/"+tags[i];
            }
        }
        if(queries.length != 0) {
            URLString += "&q=";
            for (int i = 0; i < queries.length; i++) {
                queries[i] = queries[i].replace(" ", "%20");
                if (i == (queries.length - 1)) {
                    URLString += queries[i];
                } else {
                    URLString += queries[i] + "%20AND%20";
                }
            }
        }

        // Setting APIKey.
        if(APIKey == ""){
            throw new IllegalArgumentException();
        }
        URLString += "&api-key=" + APIKey;
        URL = new URL(URLString);
    }

    /**
     * Increment page number to show the next elements keeping the same parameter.
     */
    public void incrementPage() throws MalformedURLException{
        page++;
        buildUrl();
    }

    /**
     * Returns the elaborated URL with all the given specifications.
     *
     * @return {@link URLSetter#URL} which is the baseURL completed.
     */
    public URL getURL(){
        return URL;
    }
}
