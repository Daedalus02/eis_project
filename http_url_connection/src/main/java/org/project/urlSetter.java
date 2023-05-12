package org.project;

/**
 * this class allow to set the url with some given parameters(tags, query, page, page size, api key, base url ) for the api request
 */
public class urlSetter {
    private String url = "";

    /**
     * @param baseUrl
     * @param apiKey
     * @param page
     * @param query
     * @param tags
     * @throws IllegalArgumentException
     */
    public urlSetter(String baseUrl, String apiKey, int page,int pageSize, String query ,  String[] tags) throws IllegalArgumentException{
        url += baseUrl;
        //there are two type of request, the search request and the request with contents

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
            if(query != "") {
                query = query.replace(" ","%20");
                url += "&q=" + "\"" + query + "\"";
            }
            if(apiKey == ""){
                throw new IllegalArgumentException();
            }
            url += "&api-key=" + apiKey;

    }
    public String getUrl(){
        return url;
    }
}
