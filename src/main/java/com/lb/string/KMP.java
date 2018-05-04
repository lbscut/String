package com.lb.string;

public class KMP implements StringMatcher {
	
	public int[] calNext(String str){
		int[] next = new int[str.length()];//对于i，截至第i位，当前字符串可匹配到前缀的第next[i]个元素
		next[0] = -1;//特殊情况，第一位自己与自己相同不算
		int matchIndex = -1;//当前匹配到第几个字符
		for (int i = 1; i < next.length; i++) {
			//前面已经匹配上，但是当前字符匹配不上
			while(matchIndex>-1 && str.charAt(matchIndex+1)!=str.charAt(i)){
				//此时0->matchIndex与(i-1-matchIndex)->(i-1)是一致的
				//但是第i位不一致
				//由于0->matchIndex的最长前缀是next[matchIndex]
				//所以(i-1-matchIndex)->(i-1)的最长前缀是next[matchIndex]
				//所以可以直接从next[matchIndex]后开始匹配
				matchIndex = next[matchIndex];
			}
			//当前字符匹配上
			if(str.charAt(matchIndex+1)==str.charAt(i)){
				matchIndex++;
			}
			next[i] = matchIndex;
		}
		return next;
	}
	
	public int strStr(String haystack, String needle) {
		if(haystack.length()<needle.length()){
			return -1;
		}
		if(haystack.length()==needle.length()){
			return haystack.equals(needle)?0:-1;
		}
		if(needle.length()==0){
			return 0;
		}
        int[] next = calNext(needle);
        int matchIndex = -1;//当前匹配到第几个字符
        for (int i = 0; i < haystack.length(); i++) {
        	while(matchIndex>-1 && haystack.charAt(i)!=needle.charAt(matchIndex+1)){
        		matchIndex = next[matchIndex];
        	}
			if(haystack.charAt(i)==needle.charAt(matchIndex+1)){
				matchIndex++;
			}
			if(matchIndex == needle.length()-1){
				return i - matchIndex;
			}
		}
		
		return -1;
    }
}
