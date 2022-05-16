package com.reins.centerIndex.bloomFilter;


public class FNV {

    private static final int FNV_32_PRIME = 0x01000193;
    public static int getHash(String input, int max){
        int hval = 0x811c9dc5;

        byte[] bytes = input.getBytes();

        int size = bytes.length;

        for (int i = 0; i < size; i++){
            hval *= FNV_32_PRIME;
            hval ^= bytes[i];

        }
        return ((hval % max) + max)%max;
    }
    public static int getHash(int input, int max){
        int hval = 0x811c9dc5;

        hval *= FNV_32_PRIME;
        hval ^= input;
        return ((hval % max) + max)%max;
    }
//    public static int getHash(String input, String seed){
//        int prev = getHash(seed);
//
//        int hval = 0x811c9dc5;
//
//        byte[] bytes = input.getBytes();
//
//        int size = bytes.length;
//
//        for (int i = 0; i < size; i++){
//            hval *= prev;
//            hval ^= bytes[i];
//
//        }
//        return hval;
//    }
}