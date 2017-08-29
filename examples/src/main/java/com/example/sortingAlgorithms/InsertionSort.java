package com.example.sortingAlgorithms;
/*
 * Insertion Sort 
 * Time complexity:
 * Worse case: O(n^2) when in descending order, need to traverse the whole array
 * Best case: O(n) when in ascending order
 * Space complexity: O(1)
 * In place sorting
 * Insertion sort is used when number of elements is small. 
 * It can also be useful when input array is almost sorted, 
 * only few elements are misplaced in complete big array.
 */

public class InsertionSort {

	
	
	public static void main(String[] args) {
		int[] arr = {11, 4, 9, 10, 12, 3, 1};
		
		for(int i = 1; i < arr.length; i ++){
			int j = i - 1;
			
			while(j >= 0 && arr[j] > arr[j + 1] ){
				int tmp = arr[j+1];
				arr[j+1] = arr[j];
				arr[j] = tmp;
				j -- ;
			
			}
		}
		for(int k : arr){
			System.out.print(k + ", ");
		}

	}

}
