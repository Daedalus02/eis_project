package org.example;

/**
 *
 */
public class urlSetter {
    private String url = "";

    /**
     *
     * @param baseUrl
     * @param apiKey
     * @param page
     * @param query
     * @param contents
     * @param tags
     * @throws IllegalArgumentException
     */
    public urlSetter(String baseUrl, String apiKey, int page, String query , String[] contents, String[] tags) throws IllegalArgumentException{
        url += baseUrl;
        if(contents.length == 0) {
            url += "/search?";

            url += "page=" + page;

            if(tags.length != 0) {
                url += "&tag=";
                for (int i = 0; i < tags.length; i++) {
                    url += tags[i];
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
        }else{
            for(int i = 0; i < contents.length; i++ ){
                url += "\\"+contents[i];
            }
        }
    }
    public String getUrl(){
        return url;
    }
}
