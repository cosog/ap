package com.cosog.model.scada;

import com.sun.jna.Structure;


public class CStruct {
	//模拟C语言结构体，采集项结构体
		public static class TDataItems extends Structure {
			public static class ByReference extends TDataItems implements Structure.ByReference { }
	        public static class ByValue extends TDataItems implements Structure.ByValue{ }
			
			public String DataBlock;
			public String DataItem;
			public int RegisterType;
			public byte[] StartAddr=new byte[2];
			public byte[] length=new byte[2];
			public float RangeChangeX;
			public float RangeChangeY;
			public String TableName;
			public String ColumnName;
			public int sjlx;//1-整型,2-实型(两位小数),3-实型(三位小数),4-实型(四位小数),5-BCD码,6-曲线数据
		}
		//单元数据结构体
		public static class TUnitData extends Structure{
			public static class ByReference extends TUnitData implements Structure.ByReference { }
	        public static class ByValue extends TUnitData implements Structure.ByValue{ }
			public String RTUName;
			public byte[] UnitId=new byte[1];
			public String UnitIp;
			public float CCH;
			public int itemcount;
			public TDataItems.ByReference[] DataItems=new TDataItems.ByReference[500];
		}
}
