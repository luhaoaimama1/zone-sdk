package com.zone.lib.utils.data.info;

import android.os.Build;

import java.io.*;
import java.util.regex.Pattern;

import com.zone.lib.LogZSDK;
import com.zone.lib.utils.data.check.EmptyCheck;

/**
 * Get CPU info.
 *
 * @author MaTianyu
 * @date 2015-04-18
 */
public class CpuUtil {
    private static final String CPU_INFO_PATH = "/proc/cpuinfo";
    private static final String CMD_CAT = "/system/bin/cat";
    private static final String CPU_FREQ_CUR_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";
    private static final String CPU_FREQ_MAX_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
    private static final String CPU_FREQ_MIN_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq";

    private static String CPU_NAME;
    private static int CPU_CORES = 0;
    private static long CPU_MAX_FREQENCY = 0;
    private static long CPU_MIN_FREQENCY = 0;

    public static String getCpuString() {
        if (Build.CPU_ABI.equalsIgnoreCase("x86"))
            return "Intel";
        String strInfo = "";
        try {
            byte[] bs = new byte[1024];
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1)
                strInfo = ret.substring(0, index);
            else
                strInfo = ret;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return strInfo;
    }

    public static String getCpuType() {
        String strInfo = getCpuString();
        String strType = null;
        if (strInfo.contains("ARMv5")) {
            strType = "armv5";
        } else if (strInfo.contains("ARMv6")) {
            strType = "armv6";
        } else if (strInfo.contains("ARMv7")) {
            strType = "armv7";
        } else if (strInfo.contains("Intel")) {
            strType = "x86";
        } else {
            strType = "unknown";
            return strType;
        }
        if (strInfo.contains("neon")) {
            strType += "_neon";
        } else if (strInfo.contains("vfpv3")) {
            strType += "_vfpv3";
        } else if (strInfo.contains(" vfp")) {
            strType += "_vfp";
        } else {
            strType += "_none";
        }

        return strType;
    }

    /**
     * 获取android CPU特性
     *
     * @return String CPU特性
     */
    public static String getCpuFeature() {
        String cpu_feature = "";
        CPUInfo in = getCPUInfo();
        if ((in.mCPUFeature & CPUInfo.CPU_FEATURE_NEON) == CPUInfo.CPU_FEATURE_NEON)
            cpu_feature = "neon";
        else if ((in.mCPUFeature & CPUInfo.CPU_FEATURE_VFP) == CPUInfo.CPU_FEATURE_VFP)
            cpu_feature = "vfp";
        else if ((in.mCPUFeature & CPUInfo.CPU_FEATURE_VFPV3) == CPUInfo.CPU_FEATURE_VFPV3)
            cpu_feature = "vfpv3";
        else
            cpu_feature = "unknown";
        return cpu_feature;
    }


    /**
     * @return
     * @hide
     */
    public static CPUInfo getCPUInfo() {
        String strInfo = null;
        try {
            byte[] bs = new byte[1024];
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
        } catch (IOException ex) {
            strInfo = "";
            ex.printStackTrace();
        } finally {
            CPUInfo info = parseCPUInfo(strInfo);
            info.mCPUMaxFreq = getMaxFreqency();
            return info;
        }
    }

    /**
     * 获取设备内存大小值
     *
     * @return 内存大小, 单位MB
     */
    public static long getTotalMemory() {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            if (str2 != null) {
                arrayOfString = str2.split("\\s+");
                initial_memory = Integer.valueOf(arrayOfString[1]).intValue() / 1024;
            }
            localBufferedReader.close();
            return initial_memory;
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * Print cpu info.
     */
    public static String printCpuInfo() {
        String info = getCpuString();
        LogZSDK.INSTANCE.i("_______  CPU :   \n" + info);
        return info;
    }

    /**
     * Get available processors.
     */
    public static int getProcessorsCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     *
     * @return The number of cores, or available processors if failed to get result
     */
    public static int getCoresNumbers() {
        if (CPU_CORES != 0) {
            return CPU_CORES;
        }
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            CPU_CORES = files.length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CPU_CORES < 1) {
            CPU_CORES = Runtime.getRuntime().availableProcessors();
        }
        if (CPU_CORES < 1) {
            CPU_CORES = 1;
        }
        return CPU_CORES;
    }

    /**
     * Get CPU name.
     */
    public static String getCpuName() {
        if (!EmptyCheck.isEmpty(CPU_NAME)) {
            return CPU_NAME;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(CPU_INFO_PATH), 8192);
            String line = bufferedReader.readLine();
            bufferedReader.close();
            String[] array = line.split(":\\s+", 2);
            if (array.length > 1) {
                LogZSDK.INSTANCE.i(array[1]);
                CPU_NAME = array[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CPU_NAME;
    }

    /**
     * Get current CPU freqency.
     */
    public static long getCurrentFreqency() {
        try {
            return Long.parseLong(getCpuString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get maximum CPU freqency
     */
    public static long getMaxFreqency() {
        if (CPU_MAX_FREQENCY > 0) {
            return CPU_MAX_FREQENCY;
        }
        try {
            CPU_MAX_FREQENCY = Long.parseLong(getCMDOutputString(new String[]{CMD_CAT, CPU_FREQ_MAX_PATH}).trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CPU_MAX_FREQENCY;
    }

    /**
     * Get minimum frenqency.
     */
    public static long getMinFreqency() {
        if (CPU_MIN_FREQENCY > 0) {
            return CPU_MIN_FREQENCY;
        }
        try {
            CPU_MIN_FREQENCY = Long.parseLong(getCMDOutputString(new String[]{CMD_CAT, CPU_FREQ_MIN_PATH}).trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CPU_MIN_FREQENCY;
    }

    /**
     * Get command output string.
     */
    private static String getCMDOutputString(String[] args) {
        try {
            ProcessBuilder cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] re = new byte[64];
            int len;
            while ((len = in.read(re)) != -1) {
                sb.append(new String(re, 0, len));
            }
            in.close();
            process.destroy();
            LogZSDK.INSTANCE.i("CMD: " + sb.toString());
            return sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static class CPUInfo {
        public CPUInfo() {

        }

        public static final int CPU_TYPE_UNKNOWN = 0x00000000;
        public static final int CPU_TYPE_ARMV5TE = 0x00000001;
        public static final int CPU_TYPE_ARMV6 = 0x00000010;
        public static final int CPU_TYPE_ARMV7 = 0x00000100;

        public static final int CPU_FEATURE_UNKNOWS = 0x00000000;
        public static final int CPU_FEATURE_VFP = 0x00000001;
        public static final int CPU_FEATURE_VFPV3 = 0x00000010;
        public static final int CPU_FEATURE_NEON = 0x00000100;

        public int mCPUType;
        public int mCPUCount;
        public int mCPUFeature;
        public double mBogoMips;
        public long mCPUMaxFreq;
    }

    /**
     * @param cpuInfo
     * @return
     * @hide
     */
    private static CPUInfo parseCPUInfo(String cpuInfo) {
        if (cpuInfo == null || "".equals(cpuInfo)) {
            return null;
        }

        CPUInfo ci = new CPUInfo();
        ci.mCPUType = CPUInfo.CPU_TYPE_UNKNOWN;
        ci.mCPUFeature = CPUInfo.CPU_FEATURE_UNKNOWS;
        ci.mCPUCount = 1;
        ci.mBogoMips = 0;

        if (cpuInfo.contains("ARMv5")) {
            ci.mCPUType = CPUInfo.CPU_TYPE_ARMV5TE;
        } else if (cpuInfo.contains("ARMv6")) {
            ci.mCPUType = CPUInfo.CPU_TYPE_ARMV6;
        } else if (cpuInfo.contains("ARMv7")) {
            ci.mCPUType = CPUInfo.CPU_TYPE_ARMV7;
        }

        if (cpuInfo.contains("neon")) {
            ci.mCPUFeature |= CPUInfo.CPU_FEATURE_NEON;
        }

        if (cpuInfo.contains("vfpv3")) {
            ci.mCPUFeature |= CPUInfo.CPU_FEATURE_VFPV3;
        }

        if (cpuInfo.contains(" vfp")) {
            ci.mCPUFeature |= CPUInfo.CPU_FEATURE_VFP;
        }

        String[] items = cpuInfo.split("\n");

        for (String item : items) {
            if (item.contains("CPU variant")) {
                int index = item.indexOf(": ");
                if (index >= 0) {
                    String value = item.substring(index + 2);
                    try {
                        ci.mCPUCount = Integer.decode(value);
                        ci.mCPUCount = ci.mCPUCount == 0 ? 1 : ci.mCPUCount;
                    } catch (NumberFormatException e) {
                        ci.mCPUCount = 1;
                    }
                }
            } else if (item.contains("BogoMIPS")) {
                int index = item.indexOf(": ");
                if (index >= 0) {
                    String value = item.substring(index + 2);
                }
            }
        }

        return ci;
    }

}
