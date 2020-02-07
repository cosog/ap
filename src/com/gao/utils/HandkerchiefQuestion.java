package com.gao.utils;

//（2）17个人围成一个圆圈，编号为1~17，从第一号开始报数，报到3的倍数的人离开，一直数下去，直到最后只有一个人，求此人编号  
  
public class HandkerchiefQuestion {  
    public static void main(String[] args) {  
        int n = 5;// 人数  
        int counter = n;// 计数器  
        int num = 1;// 数数  
        int[] array = new int[n];//声明数组  
  
        for (int i = 0; i < array.length; i++) {//数组初始化  
            array[i] = 1;  
        }  
  
        loop: while (true) {  
            for (int j = 0; j < array.length; j++) {  
                if (array[j] != 0) {//如果数组元素不为0，则从1开始赋值  
                    array[j] = num;  
                    if (num % 3 == 0) {//如果赋给数组元素的数能够整除3，则把此数组元素置0，计数器减1（即踢掉一个人）  
                        array[j] = 0;  
                        counter = counter - 1;  
                        if (counter == 1) {//如果最后只剩下一个人，跳出循环  
                            break loop;  
                        }  
                    }  
                    num = num + 1;  
                }  
            }  
        }  
          
        for (int k = 0; k < n; k++) {  
            if (array[k] != 0) {  
                System.out.println("最后赢家的编号为："+(k + 1));  
            }  
        }  
    }  
}  