package org.project;

/**
 * this class allow to set the url with some given parameters (tags, query, page, page size, api key, base url ) for the api request
 */
public class urlSetter {
    private String url = "";
    private String apiKey;
    private String[] queries;
    private String[] tags;
    private String baseUrl;
    private int page;
    private int pageSize;

    /**
     * @param baseUrl1
     * @param apiKey1
     * @param page1
     * @param queries1
     * @param tags1
     * @throws IllegalArgumentException
     */
    public urlSetter(String baseUrl1, String apiKey1, int page1,int pageSize1, String[] queries1 ,String[] tags1) throws IllegalArgumentException{
        baseUrl = baseUrl1;
        apiKey = apiKey1;
        tags = tags1;
        page = page1;
        queries = queries1;
        pageSize = pageSize1;
        buildUrl();
    }
    private void buildUrl(){
        url = baseUrl;

        url += "/search?";

        if(page > pageSize){
            throw new IllegalArgumentException();
        }
        url += "page-size=" + pageSize;
        if(page != 0) {
            url += "&page=" + page;
        }
        url += "&show-fields=body,headline,wordcount";

        if(tags.length != 0) {
            url += "&tag=";
            url += tags[0];
            for (int i = 1; i < tags.length; i++) {
                url += "/"+tags[i];
            }
        }
        if(queries.length != 0) {
            url += "&q=";
            for (int i = 0; i < queries.length; i++) {
                queries[i] = queries[i].replace(" ", "%20");
                if (i == (queries.length - 1)) {
                    url += queries[i];
                } else {
                    url += queries[i] + "%20AND%20";
                }
            }
        }
        if(apiKey == ""){
            throw new IllegalArgumentException();
        }
        url += "&api-key=" + apiKey;
    }
    public void incrementPage(){
        page++;
        buildUrl();
    }

    /**
     *
     * @return the complete url
     */
    public String getUrl(){
        return url;
    }
}
