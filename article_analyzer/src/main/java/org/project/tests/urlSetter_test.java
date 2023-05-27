package org.project.tests;

import org.project.URLSetter;

public class urlSetter_test
{
    public static void main(String Args[]){
        try {
            URLSetter setter = new URLSetter("https://content.guardianapis.com", "your_api_key", 1,10, new String[]{"politics"}, new String[]{});
            System.out.println(setter.getUrl());
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}
