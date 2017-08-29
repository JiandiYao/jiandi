package com.example.csv.reader;

public class IsPowerOfNumber {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(isPowerOfThree(9));
		int n = 27;
		int remainder = n%3;
        int a = n/3;
        System.out.println(remainder);
    	System.out.println(a);
        while(a >=3){
        	
            if(remainder != 0){
            	
                //return false;
            }
            a = a/3;
            remainder = a%3;
            System.out.println(remainder);
        	System.out.println(a);
        }
       		
	}
   public static boolean isPowerOfThree(int n) {
        int remainder = n%3;
        int a = n/3;
        while(a >=3){
            if(remainder != 0){
            	System.out.println(remainder);
            	System.out.println(a);
                return false;
            }
            a = a/3;
            remainder = a%3;
        }
        if(remainder != 0)
            return false;
        return true;
        
    }
}
