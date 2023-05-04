package org.example;

public class urlSetter_test
{
    public static void main(String Args[]){
        try {
            urlSetter setter = new urlSetter("https://content.guardianapis.com", "your_api_key", 1, "politics", new String[]{}, new String[]{});
            System.out.println(setter.getUrl());
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}
