package com.cosog.utils;

import java.util.Random;


public class CryptUtil {

	private String  keystring="sUnYTT8I1232 gdfs oialopuLYvIniis";
	Random          random; 
	    
	private void initialize()
	{
		random=new Random(5);
		for(int i=0;i<keystring.length();i++)
			random.setSeed((long)(random.nextDouble()*1000*(int)keystring.charAt(i)));
	}

	public java.lang.String decode(String value)
	{
			initialize();
			if(!StringManagerUtils.isNotNull(value)){
				return "";
			}
			return DoXor(Shrink(new String(hex2byte(removeColon(value)))));
	}

	private String DoXor(String operstr)
	{
        
		char tmp1,tmp2;
		int length=operstr.length();
		char[] basechar=new char[length];
		for(int i=0;i<operstr.length();i++)
		{
			tmp1=operstr.charAt(i);
			double x=random.nextDouble();
			tmp2=(char)(x*127);

			char tmp4=(char)(tmp1^tmp2);
			basechar[i]=tmp4;
            
		}
		return String.valueOf(basechar);
	}
    
	private String Shrink(String operstr)
	{
		char      tmp1,tmp2=0,tmp3=0;
		int       length;
		int       j=0;
		int       tmp4=0;
        
		length=operstr.length();
		char[] tmpchar=new char[length-1-(length-1)/4];       
        
		for(int i=1;i<tmpchar.length+1;i++)
		{
			tmp1=(char)(operstr.charAt(j)-59);          
			j++;
			int k=i % 3;
			switch(k)
			{
				case 0:
				   tmp2=(char)((tmp3 & 3) * 64);
				   j++;
				   break;
				case 1:
				   tmp4=tmp4+4;
				   if(tmp4>length) tmp4=length;
				   tmp3=(char)(operstr.charAt(tmp4-1)-59);
				   tmp2=(char)(((tmp3/16) & 3)*64);
				   break;
				case 2:
				   tmp2 =(char)(((tmp3/4)  & 3)* 64);break;
			}
			tmpchar[i-1]=(char)(tmp1 | tmp2);
		}
        
		return (String.valueOf(tmpchar));
       
	}
	private static String removeColon(String value) {
		StringBuffer result  = new StringBuffer();
		for(int i=0; i< value.length();i++){
			if(value.charAt(i) != ':') result.append(value.charAt(i));
		}
		return result.toString();
	}
		

//	------------------------------------------------------------------------------
	private static byte[] hex2byte(String str){
	  int factlen = str.length()/2;
	  byte[] bytearray = new byte[factlen];

	  for(int n=0; n<factlen; n++)
			  bytearray[n] = hexByte(str.charAt(2*n),str.charAt(2*n+1));

	  return bytearray;
	}

	private static byte hexByte(char a1, char a2){
	   int  k;
	   if (a1 >= '0' && a1 <= '9')
			   k = (int)(a1 - '0');
	   else if (a1 >= 'a' && a1 <= 'f')
			   k = (int)(a1 - 'a' + 10);
	   else if (a1 >= 'A' && a1 <= 'F')
			   k = (int)(a1 - 'A' + 10);
	   else
			   k = 0;

	   k <<= 4;

	   if (a2 >= '0' && a2 <= '9')
			   k += (int)(a2 - '0');
	   else if (a2 >= 'a' && a2 <= 'f')
			   k += (int)(a2 - 'a' + 10);
	   else if (a2 >= 'A' && a2 <= 'F')
			   k += (int)(a2 - 'A' + 10);
	   else
			   k += 0;

	   return (byte)(k & 0xFF);
	}
	

    public static void main(String[] args) {
		//String drive = "C:\\";
		//CryptUtil checkobj = new CryptUtil();
        //StringManagerUtils.printLog("driver number:" + checkobj.GetMachineID(drive));
        //String encodestr = checkobj.encode(checkobj.GetMachineID(drive));
		//StringManagerUtils.printLog("License number:" + encodestr);
		//StringManagerUtils.printLog("machine id:" + checkobj.decode(encodestr));
    }
}

