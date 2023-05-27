package org.project.tests;

import static java.lang.Character.UnicodeBlock.LATIN_1_SUPPLEMENT;

public class xmlInvalid {
    public static void main(String Args[]){
        char test = (char) 0x80;
        String test1 = "abcodefghilmnopqrstuvzxywkj " + test ;
// XML 1.1
// [#x1-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]
        String xml11pattern = "[\u0001-\u0008\u000B-\u000C\u000E-\u001F\u007F-\u0084\u0086-\u009F\uFDD0-\uFDDF]";
        test1 = test1.replaceAll(xml11pattern,"");
        System.out.println(test1);
    }
}
