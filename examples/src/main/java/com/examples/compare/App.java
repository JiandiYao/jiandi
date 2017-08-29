package com.examples.compare;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Set<Integer> set = new HashSet<Integer>();
    	Stack<Integer> stack = new Stack<Integer>();
    	StringBuilder sb = new StringBuilder();
    	sb.toString();
    	String[] a;
    	String b = "()";
    	String[] arr = {"aca", "cba", };
    	String s = " hello ws8sss ";
    	int[] nums = {1,-1,2,3};
        System.out.println( climbStairs(3));
    }
    public static int climbStairs(int n) {
        
        int[] memo = new int[n+1];
        if(n == 0)
            memo[0] = 0;
        if(n == 1)
            memo[1] = 1;
        if (n == 2)
            memo[2] = 2;
        
        for(int i = 3; i <=  n ; i ++){
            memo[i] = memo[i-1] + memo[i-2];
        }
        return memo[n];
    }
    public static int sq(int x){
    	return x/sq(x);
    }
    public static int lengthOfLastWord(String s) {
        int j = s.length() -1;
       
        int count = 0;
        while(j >= 0){
            if(isCharacter(s.charAt(j)) ){
                count ++;
                j -- ;
            }
            else if( s.charAt(j) == ' ' && count != 0)
                return count;
            else{
                count = 0;
                while(j >=0 && s.charAt(j--) != ' ');

            }
        }
        return count;
    }
    
    static boolean isCharacter(char a){
        return (a >= 'a' && a < 'z' ) || ( a >= 'A' && a < 'Z');
    }
    
    public static int maxSubArray(int[] nums) {
        int[] memo = new int[nums.length +1];
        memo[0] = nums[0];
        
        
        for( int i = 1; i < nums.length; i ++){
            int j = i;
            int max = memo[i-1];
            int tmp = 0;
            while(j >= 0){
                tmp = tmp + nums[j];
                max = Math.max(tmp, max);
                j--;
            }
            memo[i] = max;
        }
        return memo[nums.length -1]; 
        
    }
    
    public static int maxSubArray1(int[] nums) {
        
        return max(nums.length-1, nums);
        
    }
    
    static int max(int n, int[] nums){
        if(n == 0){
            return nums[0];
        }
        int max = max(n-1, nums);
        
        int i = n;
        int tmp = 0;
        while(i >= 0){
            tmp = tmp + nums[i];
            max = max > tmp ? max : tmp;
            i -- ;
        }
        return max;
    }
    
 public static int removeDuplicates(int[] nums) {
        
        if (nums == null || nums.length == 0)
            return 0;
        
        int current = 0;
        int swapped = 0;
        
        for(int i = swapped; i < nums.length-2; i ++){
            while(swapped < nums.length-2 && nums[swapped] == nums[swapped+1])
                swapped ++;
            swapped ++;
            current ++;
            if(swapped < nums.length)
                nums[current] = nums[swapped];
           
        }
        return ++current;
    }
    
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
       for(int i = 0; i < s.length(); i ++){
    	   char ch = s.charAt(i);
           if( ch == '(' || ch ==  '[' || ch == '{'){
                stack.push(ch);
                
            }
           else if(!stack.isEmpty() && ch == change(stack.peek())){
               stack.pop();
             
           }else
               return false;
       }
        return stack.isEmpty()? true: false;
        
    }
    private static char change(char c){
            switch (c){
                case ')': return '(';
                case ']': return '[';
                case '}': return '{';
                default: return ' ';
            }
                    
            
                
    }
    
    public static String longestCommonPrefix(String[] strs) {
        if(strs.equals(null) || strs.length == 0)
            return null;
        String shortest = strs[0];
        for(String s: strs){
           /* if(s.length() < shortest.length()){
                shortest = s;
            }*/
            shortest = compare(shortest, s);
            if(shortest == null)
                return null;
        }
        return shortest;
    }
    
    private static String compare(String a, String b){
        int length = a.length() < b.length()? a.length(): b.length();
        String s = new String();
        for(int i = 0; i < length; i++){
            if(a.charAt(i) == b.charAt(i)){
                s = s.concat(a.substring(i, i+1));
            }
            else{
            	return s;
            }
        }
        return s;
    }
    
    public static int romanToInt(String s) {
        /*
          String[] I = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
          String M[] = {"", "M", "MM", "MMM"};
          String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
          String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
          */
          
          int result = 0;
          Map<String, Integer> map = new HashMap<String, Integer>();
          map.put("I", 1);
          map.put("IV", 4);
          map.put("V", 5);
          map.put("IX", 9);
          map.put("X", 10);
          map.put("XL", 40);
          map.put("L", 50);
          map.put("XC", 90);
          map.put("C", 100);
          map.put("CD", 400);
          map.put("D", 500);
          map.put("CM", 900);
          map.put("M", 1000);
          
          for(int i = 0; i < s.length(); i++){
              String a = s.substring(i, i+1);
              String b; 
              if(i+1 < s.length()){
                  b = s.substring(i+1, i+2);
                  if(map.containsKey(a+b)){
                     a = a.concat(b);
                     i++;
                  }
              }
              
              result += map.get(a);
          }

          return result;
      }
    public static int reverse(int x) {
        if( x == 0 || x < -Math.pow(2, 32)/2 || x > Math.pow(2, 32)/2-1)
            return 0;
        Queue<Integer> queue = new LinkedList<Integer>();
        int sign = 0;
        if(x < 0){
            sign = 1;
            x = -x;
        }
        int res;
        
        while(true){
            res = x%10;
            x = x/10;
            queue.add(res);
            if(x == 0)
                break;
        }
        int result = 0;
        while(!queue.isEmpty()){
            result = result*10 + queue.poll();
        }    
        
        return sign == 0 ? result:-result;
    }
}
