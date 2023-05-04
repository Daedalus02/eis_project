package org.example;

public class urlSetter_test
{
    public static void main(String Args[]){
        try {
            urlSetter setter = new urlSetter("https://content.guardianapis.com", "c9d442dd-66ec-43a8-aa3d-26047fa8780e", 1, "politics", new String[]{}, new String[]{});
            System.out.println(setter.getUrl());
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}
