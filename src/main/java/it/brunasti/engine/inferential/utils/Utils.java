package it.brunasti.engine.inferential.utils;

public class Utils {

    public static void dump(String t, String[] arr) {
        System.out.println("--------" + t + "-------------");
        if (arr != null) {
            if (arr.length == 0) {
                System.out.println("-- EMPTY --");
            }
            else {
                for (int i = 0; i < arr.length; i++) {
                    System.out.println("#" + i + "='" + arr[i] + "'");
                }
            }
        } else {
            System.out.println("-- NULL --");
        }
    }


    static private int namesCounter = 0;
    static public String createName(String type) {
        namesCounter++;
        return type+"_"+(namesCounter);
    }

}
