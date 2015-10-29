package com.test;


import java.util.EmptyStackException;


public class MyTest<T> {
	
	private int size;
	private Node stack;
	
private class Node{
		
		protected T data;
		protected Node next;
		
		public Node(T data){
			this.data= data;
			this.next = null;
			
		}

}

public MyTest(){
	this.stack = null;
	this.size = 0;
}
	
	public void push(T data){  //push an element onto the stack
		
		Node newnode = new Node(data);
		newnode.next = stack; //assign previous 
		this.stack = newnode; // assign current
		size ++;
		
	}
	
	public Object pop(){ // return and remove the  top element of the stack
		if (isEmpty())
			throw new EmptyStackException();
		T data = stack.data;
		stack = stack.next;
		size --;
		return data;
		
	}
	
	public Object top(){
		
		if (isEmpty())
			throw new EmptyStackException();
		return stack.data;
	}
	
	public int size(){
		return 0;
	}

	public boolean isEmpty(){
		return (size ==0);
	}
}