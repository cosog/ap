package com.cosog.utils;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdvancedMemoryMonitorUtils {

	private static final DecimalFormat DF = new DecimalFormat("#,##0.00");
    private static final long MB = 1024 * 1024;
    private static final long KB = 1024;
    
    public static long getJVMMemory() {
    	MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    	MemoryUsage heap = memoryMXBean.getHeapMemoryUsage();
    	MemoryUsage nonHeap = memoryMXBean.getNonHeapMemoryUsage();
    	
    	return heap.getUsed()+nonHeap.getUsed();
    }
    
    public static long getJVMHeapMemory() {
    	MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    	MemoryUsage heap = memoryMXBean.getHeapMemoryUsage();
    	return heap.getUsed();
    }
    
    public static long getJVMNonHeapMemory() {
    	MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    	MemoryUsage nonHeap = memoryMXBean.getNonHeapMemoryUsage();
    	return nonHeap.getUsed();
    }
    
    public static MemoryUsage getJVMMemoryInfo() {
    	MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    	MemoryUsage heap = memoryMXBean.getHeapMemoryUsage();
    	MemoryUsage nonHeap = memoryMXBean.getNonHeapMemoryUsage();
    	 
    	Runtime runtime = Runtime.getRuntime();
    	System.out.println("JVM总内存: " + runtime.totalMemory() / 1024 / 1024 + " MB");
    	System.out.println("JVM已使用内存: " + (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024 + " MB");
    	 
    	 
    	System.out.println("堆内存使用情况:");
    	System.out.println("提交大小: " + heap.getCommitted() / 1024 / 1024 + " MB");
        System.out.println("已使用: " + heap.getUsed() / 1024 / 1024 + " MB");
    	 
        System.out.println("非堆内存使用情况:");
        System.out.println("提交大小: " + nonHeap.getCommitted() / 1024 / 1024 + " MB");
        System.out.println("已使用: " + nonHeap.getUsed() / 1024 / 1024 + " MB");
         
         
        System.out.println("堆内存与非堆内存提交大小和: " + (nonHeap.getCommitted()+heap.getCommitted() )/ 1024 / 1024 + " MB");
    	 
    	return heap;
    }
    
    public static MemoryStats getJVMMemoryUsage() {
        MemoryStats stats = new MemoryStats();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        
        // 堆内存
        MemoryUsage heap = memoryMXBean.getHeapMemoryUsage();
        stats.setHeapUsed(heap.getUsed());
        stats.setHeapCommitted(heap.getCommitted());
        stats.setHeapMax(heap.getMax());
        
        // 非堆内存
        MemoryUsage nonHeap = memoryMXBean.getNonHeapMemoryUsage();
        stats.setNonHeapUsed(nonHeap.getUsed());
        stats.setNonHeapCommitted(nonHeap.getCommitted());
        stats.setNonHeapMax(nonHeap.getMax());
        
        // 内存池详情
        List<MemoryPool> pools = new ArrayList<>();
        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            MemoryUsage usage = pool.getUsage();
            MemoryPool poolInfo = new MemoryPool(
                pool.getName(),
                usage.getUsed(),
                usage.getCommitted(),
                usage.getMax(),
                pool.getType().toString()
            );
            pools.add(poolInfo);
        }
        stats.setMemoryPools(pools);
        
        // GC信息
        List<GCInfo> gcStats = new ArrayList<>();
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            gcStats.add(new GCInfo(
                gc.getName(),
                gc.getCollectionCount(),
                gc.getCollectionTime()
            ));
        }
        stats.setGcStats(gcStats);
        
        return stats;
    }
    
    public static String getFormattedMemoryUsage() {
        MemoryStats stats = getJVMMemoryUsage();
        StringBuilder sb = new StringBuilder();
        
        sb.append("=== JVM Memory Usage ===\n");
        sb.append(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"\n");
        sb.append("Heap: ").append(formatMB(stats.getHeapUsed())).append(" MB used, ")
          .append(formatMB(stats.getHeapCommitted())).append(" MB committed, ")
          .append(formatMB(stats.getHeapMax())).append(" MB max\n");
        
        sb.append("Non-Heap: ").append(formatMB(stats.getNonHeapUsed())).append(" MB used, ")
          .append(formatMB(stats.getNonHeapCommitted())).append(" MB committed, ")
          .append(formatMB(stats.getNonHeapMax())).append(" MB max\n");
        
        sb.append("\n=== Memory Pools ===\n");
        for (MemoryPool pool : stats.getMemoryPools()) {
            sb.append(String.format("%-20s: %6s MB used, %6s MB committed, %6s MB max\n",
                pool.getName(), 
                formatMB(pool.getUsed()),
                formatMB(pool.getCommitted()),
                formatMB(pool.getMax())));
        }
        
        sb.append("\n=== GC Statistics ===\n");
        for (GCInfo gc : stats.getGcStats()) {
            sb.append(String.format("%-20s: %d collections, %d ms\n",
                gc.getName(), gc.getCollectionCount(), gc.getCollectionTime()));
        }
        
        return sb.toString();
    }
    
    public static String getFormattedMemoryUsageKB() {
        MemoryStats stats = getJVMMemoryUsage();
        StringBuilder sb = new StringBuilder();
        
        sb.append("=== JVM Memory Usage ===\n");
        sb.append(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"\n");
        sb.append("Heap: ").append(formatKB(stats.getHeapUsed())).append(" KB used, ")
          .append(formatKB(stats.getHeapCommitted())).append(" KB committed, ")
          .append(formatKB(stats.getHeapMax())).append(" KB max\n");
        
        sb.append("Non-Heap: ").append(formatKB(stats.getNonHeapUsed())).append(" KB used, ")
          .append(formatKB(stats.getNonHeapCommitted())).append(" KB committed, ")
          .append(formatKB(stats.getNonHeapMax())).append(" KB max\n");
        
        sb.append("\n=== Memory Pools ===\n");
        for (MemoryPool pool : stats.getMemoryPools()) {
            sb.append(String.format("%-20s: %6s KB used, %6s KB committed, %6s KB max\n",
                pool.getName(), 
                formatKB(pool.getUsed()),
                formatKB(pool.getCommitted()),
                formatKB(pool.getMax())));
        }
        
        sb.append("\n=== GC Statistics ===\n");
        for (GCInfo gc : stats.getGcStats()) {
            sb.append(String.format("%-20s: %d collections, %d ms\n",
                gc.getName(), gc.getCollectionCount(), gc.getCollectionTime()));
        }
        
        return sb.toString();
    }
    
    private static String formatMB(long bytes) {
        if (bytes == -1) return "N/A";
        return DF.format(bytes / (double) MB);
    }
    
    private static String formatKB(long bytes) {
        if (bytes == -1) return "N/A";
        return DF.format(bytes / (double) KB);
    }
    
    // 数据类
    public static class MemoryStats {
        private long heapUsed;
        private long heapCommitted;
        private long heapMax;
        private long nonHeapUsed;
        private long nonHeapCommitted;
        private long nonHeapMax;
        private List<MemoryPool> memoryPools;
        private List<GCInfo> gcStats;
		public long getHeapUsed() {
			return heapUsed;
		}
		public void setHeapUsed(long heapUsed) {
			this.heapUsed = heapUsed;
		}
		public long getHeapCommitted() {
			return heapCommitted;
		}
		public void setHeapCommitted(long heapCommitted) {
			this.heapCommitted = heapCommitted;
		}
		public long getHeapMax() {
			return heapMax;
		}
		public void setHeapMax(long heapMax) {
			this.heapMax = heapMax;
		}
		public long getNonHeapUsed() {
			return nonHeapUsed;
		}
		public void setNonHeapUsed(long nonHeapUsed) {
			this.nonHeapUsed = nonHeapUsed;
		}
		public long getNonHeapCommitted() {
			return nonHeapCommitted;
		}
		public void setNonHeapCommitted(long nonHeapCommitted) {
			this.nonHeapCommitted = nonHeapCommitted;
		}
		public long getNonHeapMax() {
			return nonHeapMax;
		}
		public void setNonHeapMax(long nonHeapMax) {
			this.nonHeapMax = nonHeapMax;
		}
		public List<MemoryPool> getMemoryPools() {
			return memoryPools;
		}
		public void setMemoryPools(List<MemoryPool> memoryPools) {
			this.memoryPools = memoryPools;
		}
		public List<GCInfo> getGcStats() {
			return gcStats;
		}
		public void setGcStats(List<GCInfo> gcStats) {
			this.gcStats = gcStats;
		}
    }
    
    public static class MemoryPool {
        private String name="";
        private long used=0;
        private long committed=0;
        private long max=0;
        private String type="";
        
        
        
		public MemoryPool(String name, long used, long committed, long max, String type) {
			super();
			this.name = name;
			this.used = used;
			this.committed = committed;
			this.max = max;
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public long getUsed() {
			return used;
		}
		public long getCommitted() {
			return committed;
		}
		public long getMax() {
			return max;
		}
		public String getType() {
			return type;
		}
        
    }
    
    public static class GCInfo {
        private String name="";
        private long collectionCount=0;
        private long collectionTime=0;
        
		public GCInfo(String name, long collectionCount, long collectionTime) {
			super();
			this.name = name;
			this.collectionCount = collectionCount;
			this.collectionTime = collectionTime;
		}
		public String getName() {
			return name;
		}
		public long getCollectionCount() {
			return collectionCount;
		}
		public long getCollectionTime() {
			return collectionTime;
		}
    }
}
