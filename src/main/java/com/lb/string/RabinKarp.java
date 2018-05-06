package com.lb.string;

/**
 * 滚动哈希
 * @author lb
 *
 */
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
		// 计算目标字符串的hash值，用于后面的比较
		// （只要两个字符串的hash值相等，两个字符串很有可能相等）
		// 此处采用的hash算法是string的hashcode算法，每一位字符乘以31的n-1次方
		int targetHash = 0;
		int multiplyValue = 1;
		for (int i = 0; i < needle.length(); i++) {
			targetHash = targetHash * 31 + needle.charAt(i);
			multiplyValue *= 31;
		}
		// 遍历要检查的字符串，计算每一段的hash值进行比较
		int hash = 0;
		int i = 0;
		while(i < haystack.length()){
			// 如何求hash值：
			// 只要减去第一位，再加上最后一位即可
			// 借助了之前的计算结果，对于每一个字符只计算一次
			hash = hash * 31;
			if(i-needle.length()>=0){
				//减最前面的位
				hash = hash - (haystack.charAt(i-needle.length()) * multiplyValue);
			}
			//加最后的位
			hash = hash + haystack.charAt(i);
			
			// 如果该段字符串的hash值与目标字符串的hash值相等，则做进一步检查
			if( i >= needle.length()-1 && hash == targetHash){
				//检查
				boolean flag = true;
				// 遍历检查字符串是否相等
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
