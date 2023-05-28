package org.project;

/**
 * This class only allow to set the url with some given parameters (tags, query, page, page size, api key, base url )
 * for the api request.
 * Should be used to keep track of the parameters and eventually getting a new url by easily modifying few of them.
 */
public class URLSetter {
    /** This is the representation of a possible URL that is build in this class.*/
    private String URL = "";
    /** This is the value of the API key of the media Group API endpoint.*/
    private String APIKey;
    /** These are use to search between the set of elements in given in the response.*/
    private String[] queries;
    /** These are the fields used to determine the specific set of element given in the response.*/
    private String[] tags;
    /** This is the base URL without any specification.*/
    private String baseURL;
    /** This is the current page, so a generic group of elements in the response.*/
    private int page;
    /** This is the number of articles that should be included in the API response.*/
    private int pageSize;

    /**
     * This constructor take all the information needed to create a valid URL basing one the parameters passed.
     *
     * @param baseURL which sets {@link URLSetter#baseURL}
     * @param pageSize which sets {@link URLSetter#pageSize}
     * @param APIKey which sets {@link URLSetter#APIKey}
     * @param page which sets {@link URLSetter#page}
     * @param queries which sets {@link URLSetter#queries}
     * @param tags which sets {@link URLSetter#tags}
     * @throws IllegalArgumentException when there is no coherence in pageSize and page variables (ex: having a page over the max possible).
     */
    public URLSetter(String baseURL, String APIKey, int page, int pageSize, String[] queries , String[] tags) throws  IllegalArgumentException{
        this.baseURL = baseURL;
        this.APIKey = APIKey;
        this.tags = tags;
        this.page = page;
        this.queries = queries;
        this.pageSize = pageSize;
        buildUrl();
    }
    /** This method is internally used by the URLSetter to actually build and store the final URL {@link URLSetter#URL}.  */
    private void buildUrl(){
        // Setting a base URL.
        URL = baseURL;

        // We always want to research in the API response, there is no other purpose accepted by this URL setter.
        URL += "/search?";

        // Setting the page and page checking for them to be coherent (having a page below the max possible).
        if(page > pageSize){
            throw new IllegalArgumentException();
        }
        URL += "page-size=" + pageSize;
        if(page != 0) {
            URL += "&page=" + page;
        }
        URL += "&show-fields=body,headline,wordcount";

        // Setting tags and queries.
        if(tags.length != 0) {
            URL += "&tag=";
            URL += tags[0];
            for (int i = 1; i < tags.length; i++) {
                URL += "/"+tags[i];
            }
        }
        if(queries.length != 0) {
            URL += "&q=";
            for (int i = 0; i < queries.length; i++) {
                queries[i] = queries[i].replace(" ", "%20");
                if (i == (queries.length - 1)) {
                    URL += queries[i];
                } else {
                    URL += queries[i] + "%20AND%20";
                }
            }
        }

        // Setting APIKey (APIKey must be present to actually get a valid response from the API endpoint).
        if(APIKey == ""){
            throw new IllegalArgumentException();
        }
        URL += "&api-key=" + APIKey;
    }

    /**
     * This method is simply used to change the element viewed in the current response keeping the same parameters
     * by incrementing the variable page{@link URLSetter#page}.
     */
    public void incrementPage(){
        page++;
        buildUrl();
    }

    /**
     * This method returns the elaborated URL with all the given specifications.
     *
     * @return URL {@link URLSetter#URL}which is the baseURL completed with the specification given in the constructor.
     */
    public String getURL(){
        return URL;
    }
}
