package com.reins.centerIndex.TPTree;


public class TPTree {
    int streamId;
    long rootNode;
    public TPTree(){
        rootNode = -1;
        streamId = -1;
    }
}




class TreeUtils{
   static long[] levelTimeInterval = new long[TreeConfig.TreeLevel];
   static {
       long timeInterval = TreeConfig.TimeInterVal;
       for (int i = 1; i <= TreeConfig.TreeLevel; i++) {
           levelTimeInterval[TreeConfig.TreeLevel - i ] = timeInterval;
           timeInterval = timeInterval * TreeConfig.BlockLength;
       }
   }
}