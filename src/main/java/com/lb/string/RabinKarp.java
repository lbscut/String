package com.lb.string;

public class RabinKarp implements StringMatcher{

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
		int targetHash = 0;
		int multiplyValue = 1;
		for (int i = 0; i < needle.length(); i++) {
			targetHash = targetHash * 31 + needle.charAt(i);
			multiplyValue *= 31;
		}
		int hash = 0;
		int i = 0;
		while(i < haystack.length()){
			hash = hash * 31;
			if(i-needle.length()>=0){
				//减最前面的位
				hash = hash - (haystack.charAt(i-needle.length()) * multiplyValue);
			}
			//加最后的位
			hash = hash + haystack.charAt(i);
			if( i >= needle.length()-1 && hash == targetHash){
				//检查
				boolean flag = true;
				for (int j = i,k = needle.length()-1; k>=0; j--,k--) {
					if(needle.charAt(k)!=haystack.charAt(j)){
						flag = false;
						break;
					}
				}
				if(flag)return i-needle.length()+1;
			}
			i++;
		}
		
		return -1;
	}

}
