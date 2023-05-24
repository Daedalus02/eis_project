package org.project.tests;

import org.project.urlSetter;

public class urlSetter_test
{
    public static void main(String Args[]){
        try {
            urlSetter setter = new urlSetter("https://content.guardianapis.com", "your_api_key", 1,10, new String[]{"politics"}, new String[]{});
            System.out.println(setter.getUrl());
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}
