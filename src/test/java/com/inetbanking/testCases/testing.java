package com.inetbanking.testCases;

import java.util.Arrays;
import java.util.HashMap;

public class testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = {2,7,11,15};
		int target = 13;
		//int diff = 0;
		int[] index = new int[2];
		HashMap<Integer,Integer> mapping= new HashMap<>(); {
		};
		for(int i=0;i<nums.length;i++) {
			if(!mapping.containsKey(nums[i])) {
				mapping.put(target-nums[i], i);
				
			}
			else {
				index[0]=i;
				index[1]=mapping.get(nums[i]);
				break;
			}
		}
		System.out.println(Arrays.toString(index));
		/*for (int i = 0; i < len; i++) {
			diff = target - nums[i];
			for (int j = i + 1; j < len; j++) {
				if (nums[j] == diff) {
					index[0] = i;
					index[1] = j;
				}
			}

*/		}
	}


