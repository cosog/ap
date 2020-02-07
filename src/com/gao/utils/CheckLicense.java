package com.gao.utils;

import java.util.Random;

// Referenced classes of package com.jamie.base.util:
//            MACAddress

public class CheckLicense
{

    private String keystring;
    Random random;

    public CheckLicense()
    {
        keystring = "sUnYTT8I1232 gdfs oialopuLYvIniis";
    }

    public static void main(String args[])
    {
        MACAddress address = new MACAddress();
        String serialnumber = address.getMACAddress();
        CheckLicense check = new CheckLicense();
        check.initialize();
        String licensenumber = check.encode(serialnumber);
//        System.out.println((new StringBuilder("license number:")).append(licensenumber).toString());
    }

    public String encode(String value)
    {
        String stepstr1 = DoXor(value);
        String stepstr2 = Stretch(stepstr1);
        return byte2hex(stepstr2.getBytes());
    }

    private void initialize()
    {
        random = new Random(5L);
        for(int i = 0; i < keystring.length(); i++)
        {
            random.setSeed((long)(random.nextDouble() * 1000D * (double)keystring.charAt(i)));
        }

    }

    private String DoXor(String operstr)
    {
        int length = operstr.length();
        char basechar[] = new char[length];
        for(int i = 0; i < operstr.length(); i++)
        {
            char tmp1 = operstr.charAt(i);
            double x = random.nextDouble();
            char tmp2 = (char)(int)(x * 127D);
            char tmp4 = (char)(tmp1 ^ tmp2);
            basechar[i] = tmp4;
        }

        return String.valueOf(basechar);
    }

    private String Stretch(String operstr)
    {
        char tmp3 = '\0';
        int j = 0;
        int length = operstr.length();
        char tmpchar[] = new char[length + (length + 2) / 3];
        for(int i = 1; i < length + 1; i++)
        {
            char tmp1 = operstr.charAt(i - 1);
            tmpchar[j] = (char)((tmp1 & 0x3f) + 59);
            j++;
            int k = i % 3;
            switch(k)
            {
            case 0: // '\0'
                tmp3 |= (char)(tmp1 / 64);
                tmpchar[j] = (char)(tmp3 + 59);
                j++;
                tmp3 = '\0';
                break;

            case 1: // '\001'
                tmp3 |= (char)((tmp1 / 64) * 16);
                break;

            case 2: // '\002'
                tmp3 |= (char)((tmp1 / 64) * 4);
                break;
            }
        }

        if(length % 3 > 0)
        {
            tmpchar[j] = (char)(tmp3 + 59);
            j++;
        }
        return String.valueOf(tmpchar);
    }

    private static String byte2hex(byte b[])
    {
        String hs = "";
        String stmp = "";
        for(int n = 0; n < b.length; n++)
        {
            stmp = Integer.toHexString(b[n] & 0xff);
            if(stmp.length() == 1)
            {
                hs = (new StringBuilder(String.valueOf(hs))).append("0").append(stmp).toString();
            } else
            {
                hs = (new StringBuilder(String.valueOf(hs))).append(stmp).toString();
            }
            if(n < b.length - 1)
            {
                hs = (new StringBuilder(String.valueOf(hs))).append(":").toString();
            }
        }

        return hs.toUpperCase();
    }
}
