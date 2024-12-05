package com.example.voice_converter.leetcode;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
@NoArgsConstructor
public class ListNode{
    int val;
    ListNode next;

    ListNode(int val){
        this.val = val;
    }

    ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
    }

}
