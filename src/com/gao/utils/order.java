package com.gao.utils;

import java.util.*;

public class order {

	public static void main(String args[]) {
		List<Double> dlist = new ArrayList<Double>();
		dlist.add(45.1);
		dlist.add(45.2);
		dlist.add(110.22);
		max();
		//System.out.println("max的值为: " + Collections.max(dlist) + "   ====min的值为: " + Collections.min(dlist));
	}
	public  static void max(){
		{        
            List dlist=new ArrayList();
            dlist.add(45.1);
            dlist.add(45.2);
            dlist.add(110.22);
            Double max = (Double)dlist.get(0);     
            Double min = (Double)dlist.get(0); 
          for (int i = 0; i < dlist.size(); i++) {          
                    if (min > (Double)dlist.get(i)) min = (Double)dlist.get(i);   
                     if (max < (Double)dlist.get(i)) max = (Double)dlist.get(i);        
             }        
                      System.out.println("max的值为" + max + "min的值为" + min);    
    }
	}
}
