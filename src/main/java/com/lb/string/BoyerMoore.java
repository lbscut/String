package com.lb.string;

import java.util.HashMap;

public class BoyerMoore implements StringMatcher{

	public static void main(String[] args) {
		System.out.println(new BoyerMoore().strStr("hello", "ll"));
		System.out.println(new BoyerMoore().strStr("aaa", "a"));
		System.out.println(new BoyerMoore().strStr("mississippi", "issi"));
		System.out.println(new BoyerMoore().strStr("babbbbbabb", "bbab"));
		System.out.println(new BoyerMoore().strStr("aaaaa", "bba"));
		System.out.println(new BoyerMoore().strStr("aabaabbbaabbbbabaaab", "abaa"));
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
		//计算坏字符法则需要的辅助Map
		HashMap<Character,Integer> map = new HashMap<Character,Integer>();
		for (int i = 0; i < needle.length(); i++) {
			char ch = needle.charAt(i);
				map.put(ch, i);
		}
		//计算好后缀法则需要的辅助数组
		int[] next = new int[needle.length()];
		for (int i = needle.length()-1; i >0; i--) {
			int delta = i+1 < needle.length() ? next[i+1] : 0;
			next[i] = delta;
			int i1=i-delta-1,i2=i;
			while( i1 >= 0){
				boolean flag = true;
				while(i2 < needle.length()){
					if(needle.charAt(i1)!=needle.charAt(i2)){
						next[i] = i+1<needle.length()?next[i+1]:1;//后缀没有匹配上
						flag = false;
						break;
					}
					i1++;i2++;
				}
				if(flag){
					next[i] = i2-i1;
					break;
				}
				delta++;
				i1=i-delta-1;i2=i;
				
			}
		}
		
		int current = needle.length()-1;
		while(current < haystack.length()){
			int i1 = current,i2=needle.length()-1;
			while(i2 >= 0){
				if(haystack.charAt(i1) != needle.charAt(i2)){
					break;
				}
				i1--;i2--;
			}
			if(i2 == -1){
				return i1 + 1;
			}
			//计算坏字符和好前缀法则需要的位移
			int bad=0,good=0;
			if(map.containsKey(haystack.charAt(i1))){
				bad = i2 - map.get(haystack.charAt(i1));
			}else{
				bad = i2 + 1;
			}
			//匹配上
			if(i2 != needle.length()-1){
				good = next[i2+1];
			}else{//没匹配上
				good = 1;
			}
			current = current + (Math.max(bad, good)>0?Math.max(bad, good):1);
		}
		return -1;
	}

}
