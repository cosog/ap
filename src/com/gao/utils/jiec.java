package com.gao.utils;


public class jiec {

	 public static void main(String[] args) {
	  jiec jc = new jiec();

	   System.out.println("!=" + jc.jc(10));
	
	 }

	 public int jc(int i) {
	  if (i <= 1)
	   return 1;
	  else
	   return i * jc(i - 1);
	 }

	}