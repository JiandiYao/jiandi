package com.example.sortingAlgorithms;

/*
 * repeatedly finding the minimum element (considering ascending order) from unsorted part 
 * and putting it at the beginning.
 * Time Complexity: O(n2)
 * Space Complexity: O(1)
 * The good thing about selection sort is it never makes more than O(n) swaps 
 * and can be useful when memory write is a costly operation.
 */
public class SelectionSort {

	public static void main(String[] args) {
		int[] arr = {11, 4, 9, 10, 12, 3, 1};
		for(int i = 0; i < arr.length; i ++){
			int j = i + 1;
			int min = arr[i];
			int index = i;
			while(j < arr.length){
				if(arr[j] < min){
					min = arr[j];
					index = j;
				}
				j++;
			}
			
			if(i != index){
				
				arr[index] = arr[i];
				arr[i] = min;
			}
			
			
		}
		for(int k : arr){
			System.out.print(k + ", ");
		}
	}

	
}
