package com.example.demo.netty.Mina;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


/**
 * 聚合工具类
 *
 * @author yuan dian
 * @date 2019/1/22
 * @time 10:08
 */
@Slf4j
public class Utils {

    /**
     * 从报文中取出类型为 BCD码的字段
     */
    public static String getBCDString(byte[] content, int start, int length) {
        // BCD码高低位不需要翻转
        return bytes2HexString(content, start, length, false);
    }

    /**
     * 从报文中取出类型为 ASCII码的字段
     */
    public static String getAsciiString(byte[] content, int start, int length) {
        // ASCII码高低位不需要翻转
        return bytes2String(content, start, length, false);
    }

    /**
     * 从报文中取出车辆VIN码
     *
     * @param content 待处理的字节数组
     * @param start   开始的字节位置
     * @param reverse 字符串是否翻转
     */
    public static String getVinCode(byte[] content, int start, boolean reverse) {
        return bytes2String(content, start, 17, reverse);
    }

    /**
     * 将BCD码字符串转换为字节数组
     */
    public static byte[] bcdString2ByteArray(String input) {
        // BCD码高低位不需要翻转
        return hexStr2Bytes(input, false);
    }

    /**
     * 将字节数组的元素转换为“十六进制的字符串形式”
     *
     * @param b            待处理的字节数组
     * @param start        开始的字节位置
     * @param length       要处理的字节数量
     * @param littleEndian 是否小端传输
     * @return 十六进制的字符串形式
     */
    public static String bytes2HexString(byte[] b, int start, int length, boolean littleEndian) {
        StringBuilder ret = new StringBuilder();
        // 小端传输时，数据低位在左，高位在右
        if (littleEndian) {
            for (int i = start + length - 1; i >= start; i--) {
                String hex = Integer.toHexString(b[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                ret.append(hex.toUpperCase());
            }
        } else {
            for (int i = start; i < b.length && i < start + length; i++) {
                String hex = Integer.toHexString(b[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                ret.append(hex.toUpperCase());
            }
        }

        return ret.toString();
    }

    /**
     * 从字节数组中取出字符串
     *
     * @param b       待处理的字节数组
     * @param offset  开始的字节位置
     * @param length  连续使用的字节个数
     * @param reverse 转换的字符串是否需要翻转
     * @return ASCII码字符串
     */
    public static String bytes2String(byte[] b, int offset, int length, boolean reverse) {
        StringBuilder ret = new StringBuilder();
        if (reverse) {
            for (int i = offset + length - 1; i >= offset; i--) {
                String hex = String.valueOf((char) (b[i] & 0xFF));
                ret.append(hex.toUpperCase());
            }
            return ret.toString();
        }
        for (int i = offset; i < b.length && i < offset + length; i++) {
            String hex = String.valueOf((char) (b[i] & 0xFF));
            ret.append(hex.toUpperCase());
        }
        return ret.toString();
    }

    /**
     * 将“十六进制”形式的字符串转换成对应的字节数组
     */
    public static byte[] hexStr2Bytes(String src, boolean littleEndian) {
        // 对输入值进行规范化整理
        src = src.trim().replace(" ", "").toUpperCase(Locale.US);

        // 处理值初始化
        int m;
        int n;
        // 计算长度
        int iLen = src.length() / 2;
        // 分配存储空间
        byte[] ret = new byte[iLen];
        for (int i = 0; i < iLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[littleEndian ? (iLen - i - 1) : i] =
                    (byte) (Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 0xFF);
        }
        return ret;
    }

    /**
     * 将时间字符串转换成 CP56Time2a 格式
     *
     * @param time 日期时间，格式：yyyy-MM-dd HH:mm:ss
     */
    public static byte[] cp56Time2aToByteArray(String time) {
        // 日期转换
        Date date;
        try {
            if (Objects.nonNull(time)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = sdf.parse(time);
            } else {
                date = new Date();
            }
        } catch (ParseException e) {
            log.warn("cp56Time2aToByteArray，日期格式转换错误，取当前时间。time：{}", time);
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        byte[] cp56Time2a = new byte[7];
        // 计算出毫秒，占两个字节，且低位在前，高位在后
        byte[] milliseconds = short2ByteArray(calendar.get(Calendar.SECOND) * 1000, true);
        cp56Time2a[0] = milliseconds[0];
        cp56Time2a[1] = milliseconds[1];
        // 计算出分钟，占一个字节
        cp56Time2a[2] = (byte) calendar.get(Calendar.MINUTE);
        // 计算出小时，占一个字节
        cp56Time2a[3] = (byte) calendar.get(Calendar.HOUR_OF_DAY);
        // 计算出日期（不关注周几），占一个字节
        cp56Time2a[4] = (byte) calendar.get(Calendar.DAY_OF_MONTH);
        // 计算出月份，占一个字节
        cp56Time2a[5] = (byte) (calendar.get(Calendar.MONTH) + 1);
        // 计算出年份，但要减去2000（2000年 为基准年）
        cp56Time2a[6] = (byte) (calendar.get(Calendar.YEAR) - 2000);
        return cp56Time2a;
    }

    /**
     * 从 CP56Time2a 格式的数据中解析出时间
     *
     * @return time 日期时间，格式：yyyy-MM-dd HH:mm:ss
     */
    public static Date byteArray2CP56Time2a(byte[] content, int offset) {
        // 计算出年份，要加上 2000（2000年 为基准年）
        int year = Byte.toUnsignedInt(content[offset + 6]) + 2000;
        // 计算出月份，低4位为有效数据
        int month = Byte.toUnsignedInt(content[offset + 5]) & 0x0F;
        // 计算出日，低5位为有效数据（高3位代表“周几”，不需要）
        int day = Byte.toUnsignedInt(content[offset + 4]) & 0x1F;
        // 计算出小时，低5位为有效数据
        int hour = Byte.toUnsignedInt(content[offset + 3]) & 0x1F;
        // 计算出分钟，低6位为有效数据
        int minute = Byte.toUnsignedInt(content[offset + 2]) & 0x3F;
        // 计算出毫秒，占两个字节，除以 1000 得到 秒
        int second = byteArray2Short(content, offset, true) / 1000;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        return calendar.getTime();
    }

    /**
     * 取int型数字的2个低位字节并放入字节数组
     *
     * @param littleEndian 是否小端传输
     * @return 长度为2的字节数组
     */
    public static byte[] short2ByteArray(int s, boolean littleEndian) {
        byte[] src = new byte[2];
        // 小端传输时，数据低位在左，高位在右
        if (littleEndian) {
            src[0] = (byte) s;
            src[1] = (byte) (s >> 8);
        } else {
            src[0] = (byte) (s >> 8);
            src[1] = (byte) s;
        }
        return src;
    }

    /**
     * 将int型数字转换为长度为4的字节数组
     *
     * @param littleEndian 是否小端传输
     * @return 长度为4的字节数组
     */
    public static byte[] int2byteArray(int s, boolean littleEndian) {
        byte[] src = new byte[4];
        if (littleEndian) {
            src[3] = (byte) (s >> 24);
            src[2] = (byte) (s >> 16);
            src[1] = (byte) (s >> 8);
            src[0] = (byte) s;
        } else {
            src[0] = (byte) (s >> 24);
            src[1] = (byte) (s >> 16);
            src[2] = (byte) (s >> 8);
            src[3] = (byte) s;
        }
        return src;
    }

    /**
     * 从字节数组中取出一个“用连续两个字节”表示的整数
     *
     * @param b            待处理的字节数组
     * @param offset       开始的字节位置
     * @param littleEndian 是否小端传输
     * @return int型整数
     */
    public static int byteArray2Short(byte[] b, int offset, boolean littleEndian) {
        int left;
        int right;
        // 小端传输时，低位在前，高位在后
        if (littleEndian) {
            left = b[offset + 1] & 0xFF;
            right = b[offset] & 0xFF;
        } else {
            left = b[offset] & 0xFF;
            right = b[offset + 1] & 0xFF;
        }
        return (left << 8) | right;
    }

    /**
     * 从字节数组中取出一个“用连续四个字节”表示的整数，并构造成浮点数
     *
     * @param b            待处理的字节数组
     * @param offset       开始的字节位置
     * @param scale        小数位数
     * @param littleEndian 是否小端传输
     */
    public static BigDecimal getBigDecimalFromByte4(byte[] b, int offset, int scale, boolean littleEndian) {
        // 取出 四个字节表示的整数
        long value = byteArray2Long(b, offset, 4, littleEndian);
        // 除以倍率（10的scale次方）得到一个浮点数
        BigDecimal pow = BigDecimal.valueOf(Math.pow(10, scale));
        return new BigDecimal(value).divide(pow, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 从字节数组中取出一个“用连续四个字节”表示的整数，并构造成浮点数
     *
     * @param b            待处理的字节数组
     * @param offset       开始的字节位置
     * @param scale        小数位数
     * @param littleEndian 是否小端传输
     * @return double型数字
     */
    public static double intByteArray2Double(byte[] b, int offset, int scale, boolean littleEndian) {
        // 取出 四个字节表示的整数
        return longByteArray2double(b, offset, 4, scale, littleEndian);
    }

    /**
     * 从字节数组中取出一个“用连续n个字节”表示的整数，并构造成浮点数
     *
     * @param b
     * @param offset
     * @param scale
     * @param littleEndian
     * @return
     */
    public static double longByteArray2double(byte[] b, int offset, int length, int scale, boolean littleEndian) {
        long value = byteArray2Long(b, offset, length, littleEndian);
        // 除以倍率（10的scale次方）得到一个浮点数
        BigDecimal pow = BigDecimal.valueOf(Math.pow(10, scale));
        return new BigDecimal(value).divide(pow, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 从字节数组中取出一个“用连续n个字节”表示的整数
     *
     * @param b            待处理的字节数组
     * @param offset       开始的字节位置
     * @param littleEndian 是否小端传输
     * @return long型数字
     */
    public static long byteArray2Long(byte[] b, int offset, int length, boolean littleEndian) {
        long value = 0;
        for (int i = 0; i < length; i++) {
            int shift = littleEndian ? i * 8 : (length - 1 - i) * 8;
            value += (long) (b[i + offset] & 0xFF) << shift;
        }
        return value;
    }

    /**
     * 对字符串做补齐操作
     *
     * @param code     原字符串
     * @param len      补齐到的长度
     * @param fillWord 用于补齐的字符
     * @return 补齐后的字符串
     */
    public static String getFillCode(String code, int len, String fillWord) {
        code = code == null ? "" : code;
        if (len > code.length()) {
            StringBuilder sb = new StringBuilder();
            for (int i = code.length(); i < len; i++) {
                sb.append(fillWord);
            }
            sb.append(code);
            code = sb.toString();
        }
        return code;
    }

    /**
     * 计算出两个时间之间的分钟差
     *
     * @param stateTime 开始时间
     * @param endTime   结束时间
     * @return 分钟差
     */
    public static long diffMinute(Date stateTime, Date endTime) {
        long stateTimeLong = stateTime.getTime();
        long endTimeLong = endTime.getTime();
        return (endTimeLong - stateTimeLong) / (60 * 1000);
    }

    /**
     * 对字符串转成的 ASCII码 前面补0
     */
    public static byte[] getFillByteArray(byte[] array, int len) {
        if (len > array.length) {
            byte[] fillArray = new byte[len];
            System.arraycopy(array, 0, fillArray, 0, array.length);
            return fillArray;
        }
        return array;
    }

    /**
     * 业务CRC16
     */
    public static byte[] calcCrc16(ByteBuffer out) {
        out.flip();
        byte[] octets = new byte[out.remaining()];
        out.get(octets);
        int cal = CRC16.calcCrc16(octets, 2, octets.length - 2);
        // 校验域占两个字节，BIN码，需要小端传输
        byte[] array = short2ByteArray(cal, true);
        out.limit(octets.length + 2);
        return array;
    }

}