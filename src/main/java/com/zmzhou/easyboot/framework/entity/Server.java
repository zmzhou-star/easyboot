package com.zmzhou.easyboot.framework.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.zmzhou.easyboot.common.utils.Arith;
import com.zmzhou.easyboot.common.utils.IpUtils;
import com.zmzhou.easyboot.framework.entity.server.Cpu;
import com.zmzhou.easyboot.framework.entity.server.Jvm;
import com.zmzhou.easyboot.framework.entity.server.Mem;
import com.zmzhou.easyboot.framework.entity.server.Sys;
import com.zmzhou.easyboot.framework.entity.server.SysFile;

import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

/**
 * 服务器相关信息
 *
 * @author ruoyi
 */
@Slf4j
public class Server {
    /**
     * The constant OSHI_WAIT_SECOND.
     */
    private static final int OSHI_WAIT_SECOND = 1000;
    
    /**
     * CPU相关信息
     */
    private Cpu cpu = new Cpu();
    
    /**
     * 內存相关信息
     */
    private Mem mem = new Mem();
    
    /**
     * JVM相关信息
     */
    private Jvm jvm = new Jvm();
    
    /**
     * 服务器相关信息
     */
    private Sys sys = new Sys();
    
    /**
     * 磁盘相关信息
     */
    private List<SysFile> sysFiles = new LinkedList<SysFile>();
    
    /**
     * Gets cpu.
     *
     * @return the cpu
     */
    public Cpu getCpu() {
        return cpu;
    }
    
    /**
     * Sets cpu.
     *
     * @param cpu the cpu
     */
    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }
    
    /**
     * Gets mem.
     *
     * @return the mem
     */
    public Mem getMem() {
        return mem;
    }
    
    /**
     * Sets mem.
     *
     * @param mem the mem
     */
    public void setMem(Mem mem) {
        this.mem = mem;
    }
    
    /**
     * Gets jvm.
     *
     * @return the jvm
     */
    public Jvm getJvm() {
        return jvm;
    }
    
    /**
     * Sets jvm.
     *
     * @param jvm the jvm
     */
    public void setJvm(Jvm jvm) {
        this.jvm = jvm;
    }
    
    /**
     * Gets sys.
     *
     * @return the sys
     */
    public Sys getSys() {
        return sys;
    }
    
    /**
     * Sets sys.
     *
     * @param sys the sys
     */
    public void setSys(Sys sys) {
        this.sys = sys;
    }
    
    /**
     * Gets sys files.
     *
     * @return the sys files
     */
    public List<SysFile> getSysFiles() {
        return sysFiles;
    }
    
    /**
     * Sets sys files.
     *
     * @param sysFiles the sys files
     */
    public void setSysFiles(List<SysFile> sysFiles) {
        this.sysFiles = sysFiles;
    }
    
    /**
     * Copy to.
     */
    public void copyTo() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        setCpuInfo(hal.getProcessor());

        setMemInfo(hal.getMemory());

        setSysInfo();

        setJvmInfo();
        setSysFiles(si.getOperatingSystem());
    }
    
    /**
     * 设置CPU信息
     *
     * @param processor the processor
     */
    private void setCpuInfo(CentralProcessor processor) {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
    }
    
    /**
     * 设置内存信息
     *
     * @param memory the memory
     */
    private void setMemInfo(GlobalMemory memory) {
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
    }
    
    /**
     * 设置服务器信息
     */
    private void setSysInfo() {
        Properties props = System.getProperties();
        sys.setComputerName(IpUtils.getHostName());
        sys.setComputerIp(IpUtils.getHostIp());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
    }
    
    /**
     * 设置Java虚拟机
     */
    private void setJvmInfo() {
        Properties props = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
    }
    
    /**
     * 设置磁盘信息
     *
     * @param os the os
     */
    private void setSysFiles(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFile sysFile = new SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(Arith.mul(Arith.div(used, total, 4), 100));
            sysFiles.add(sysFile);
        }
    }
    
    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值 string
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        // 文件大小
        String fileSize;
        if (size >= gb) {
            fileSize = String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            fileSize = String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            fileSize = String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            fileSize = String.format("%d B", size);
        }
        return fileSize;
    }
}
