package com.reins.centerIndex.bloomFilter;


import lombok.Data;
import org.bson.BsonBinary;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static com.google.common.base.Charsets.UTF_8;


@Data
public class BloomFilterConvert {
//    static int[] seeds = new int[]{7, 11, 13};
//    public static int hash(String p, int seed){
//        return FNV.getHash(p, Integer.valueOf(seed).toString());
//    }
//    public static int hash(int x, int seed){
//        return FNV.getHash(Integer.valueOf(x).toString(), Integer.valueOf(seed).toString());
//    }

//    public static BsonBinary convert(String queryValue, int bitLength){
//        byte[] bytes= new byte[bitLength/8];
//
//        for(int seed: seeds){
//            int index = (hash(queryValue, seed)  % bitLength + bitLength) % bitLength;
//            bytes[index/8] |= 1<< (7 - (index%8));
//        }
//
////        return Base64.getEncoder().encodeToString(bytes);
//        return new BsonBinary(bytes);
//    }
    public static int hash(int x, int max){
        return FNV.getHash(x, max);
    }
    public static int hash(String str, int max){
        return FNV.getHash(str, max);
    }
    static int PREFIX_LENGTH_INT = 8 ;

    static boolean checkBit(byte[] bytes, int index){

        return (bytes[index / 8] & 1 << (7 - (index % 8))) != 0;

    }

    public static boolean stringCheck(String str, String reg ,List<byte[]> filters){
        int maxLength = filters.size();
        String[] splitPrefix = str.split(reg);
        if(splitPrefix[0].equals("")){
            splitPrefix = str.substring(reg.length()).split(reg);
        }
        for( int i=0; i< splitPrefix.length && i <maxLength; i++){
            String prefix_temp = String.join(reg, Arrays.copyOf(splitPrefix, i+1));
            if(! checkBit(filters.get(i), hash(prefix_temp, filters.get(i).length * 8))){
                return false;
            }
        }
        return true;
    }

    public static boolean rangeCheck(int queryStart, int queryEnd, List<byte[]> filters){

//        return Base64.getEncoder().encodeToString(bytes);
        return check_range_int_loop(queryStart, queryEnd, 0, filters, 0);
    }
    static boolean doubt(byte[] bytes, int check_value){
        int index = hash(check_value, bytes.length * 8);
        return checkBit(bytes, index);
    }
//
//    index = FNV.get_hash_int(check_value, len(self.bitArrays[l]))
//            return self.bitArrays[l][index] == 1
//    }
    public static boolean check_range_int_loop(int start, int end, int pre, List<byte[]> filters,int level){
        int prefix_base = pre << PREFIX_LENGTH_INT;
        int filter_length = filters.size();
        int shift = (filter_length - level - 1) * PREFIX_LENGTH_INT;
        int interval = (1<< shift) -1;
        int loopStart = Math.max(0, (start/(interval+1)) - prefix_base);
        int loopEnd =  Math.min(end/(interval+1) - prefix_base, (1<<PREFIX_LENGTH_INT)-1);
        for (int i=loopStart; i <= loopEnd; i++){
            int checkNum = prefix_base + i;
            int checkRangeLeft = checkNum << shift;
            int checkRangeRight = (checkNum << shift) + interval;
            if(checkRangeLeft >= start && checkRangeRight <= end){
                if(doubt(filters.get(level), checkNum)) return true;
            }else if(doubt(filters.get(level), checkNum)){
                    boolean testRes = check_range_int_loop(start, end, checkNum,filters,level+1);
                    if(testRes) return true;
                }
        }
        return false;
    }


}
//TODO:https://github.com/OldPanda/bloomfilter