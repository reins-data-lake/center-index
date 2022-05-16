package com.reins.centerIndex.TPTree;

public interface TreeConfig {
//    static final long MILLISECOND = 1;
//    static final long SECOND = 1000 * MILLISECOND;
    static final long SECOND = 1;
    static final long MINUTE = 60 * SECOND;
    static final long HOUR = 60 * MINUTE;
    static final long DAY = 24 * HOUR;
    static final long YEAR = 356 * DAY;
    static final long TimeInterVal = 1 * MINUTE;
    static final long MinimumTime = 40 * YEAR;//from 2010
    static final long MaxTime = 60 * YEAR;//to 2030
    static final int BlockLength = 16;
    static final int TreeLevel = (int)Math.ceil(Math.log((double)(MaxTime - MinimumTime)/ (double)TimeInterVal)/ Math.log((double)BlockLength));
    static final String TreeRootId = "";
}