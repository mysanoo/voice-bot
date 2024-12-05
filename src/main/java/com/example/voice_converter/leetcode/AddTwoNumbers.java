package com.example.voice_converter.leetcode;

public class AddTwoNumbers {


    public ListNode addTwoNumber(ListNode list1, ListNode list2){

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int carry = 0;
        while(list1 != null || list2 != null || carry != 0){

            int val1 = (list1 != null) ? list1.val : 0;
            int val2 = (list2 != null) ? list2.val : 0;

            int sum = val1 + val2 + carry;
            carry = sum/10;

            current.next = new ListNode(sum%10);
            current = current.next;

            if(list1 != null) list1=list1.next;
            if(list2 != null) list2=list2.next;
        }
        return dummy.next;
    }
}
