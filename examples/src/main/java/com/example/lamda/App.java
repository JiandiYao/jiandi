package com.example.lamda;

import java.util.Stack;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Solution s = new Solution();
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        ListNode r = Solution.addTwoNumbers(l1, l2);
        //System.out.println(r.val);
        while(r != null){
        	System.out.println(r.val);
        	r = r.next;
        }
        
    }
}

 //Definition for singly-linked list.
  class ListNode {
      int val;
      ListNode next;
     ListNode(int x) { val = x; }
  }

 class Solution {
    
    @SuppressWarnings("unused")
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        
        Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();
        ListNode temp = l1;
        while(temp != null){
            stack1.push(temp.val);
            temp = temp.next;
        }
        temp = l2;
        while (temp != null){
            stack2.push(temp.val);
            temp = temp.next;
        }
        int count  = 0;
        
        int remainder = 0;
        int a, b;
        int value;
        
        ListNode result = null;
        ListNode head = null;
        
        while (!stack1.isEmpty() || !stack2.isEmpty()){
            if(stack1.isEmpty()){
                a = 0;
                b = stack2.pop();
            }
            else if(stack2.isEmpty()){
                b = 0;
                a = stack1.pop();
            }else{
                a = stack1.pop();
                b =  stack2.pop();
            }
            
            value = (a + b + remainder) % 10;
            remainder = (a + b + remainder) / 10;
            
            if (count == 0){
                
                result = new ListNode(value);
                head = result;
                count ++;
            }else{
            	head.next = new ListNode(value);
                head = head.next;
            }
            
        }
        
        if (remainder != 0){
        	head.next = new ListNode(remainder);
        }
        return result;
        
    }
    
}