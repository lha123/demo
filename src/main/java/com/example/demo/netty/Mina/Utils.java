package com.example.demo.netty.Mina;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(UtilsService.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Utils() {
    }

    public static byte int2byte(int s) {
        return (byte)(s & 255);
    }

    public static String stringToAscii(String hex) {
        StringBuilder sb = new StringBuilder();
        byte[] b = HexUtil.decodeHex(hex);

        for(int i = 0; i < b.length; ++i) {
            sb.append((char)b[i]);
        }

        return sb.toString();
    }

    public static String Bytes2String(byte[] b, int start, int length, boolean reverse) {
        return stringToAscii(BytesHexString(b, start, length, reverse));
    }

    public static long byteArray2int(byte[] b, int offset, boolean reverse) {
        long value = 0L;

        for(int i = 0; i < 4; ++i) {
            int shift = reverse ? i * 8 : (3 - i) * 8;
            value += (long)((b[i + offset] & 255) << shift);
        }

        return value & 4294967295L;
    }

    public static long byteArray2long(byte[] b, int offset, boolean reverse) {
        long value = 0L;

        for(int i = 0; i < 8; ++i) {
            int shift = reverse ? i * 8 : (3 - i) * 8;
            value += (long)((b[i + offset] & 255) << shift);
        }

        return value;
    }

    public static byte[] int2byteArray(int s, boolean reverse) {
        byte[] src = new byte[4];
        if (reverse) {
            src[3] = (byte)(s >> 24 & 255);
            src[2] = (byte)(s >> 16 & 255);
            src[1] = (byte)(s >> 8 & 255);
            src[0] = (byte)(s & 255);
        } else {
            src[0] = (byte)(s >> 24 & 255);
            src[1] = (byte)(s >> 16 & 255);
            src[2] = (byte)(s >> 8 & 255);
            src[3] = (byte)(s & 255);
        }

        return src;
    }

    public static byte[] long2byteArray(long s, boolean reverse) {
        byte[] src = new byte[8];

        for(int i = 0; i < 8; ++i) {
            src[i] = (byte)((int)(s >> (reverse ? 8 * i : 8 * (8 - i - 1)) & 255L));
        }

        return src;
    }

    public static String getHexStr(byte[] msg) {
        return HexUtil.encodeHexStr(msg, false);
    }

    public static String reverseString(String hex) {
        String str = StrUtil.emptyToDefault(hex, "");
        String[] cut = StrUtil.cut(str, 2);
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = cut.length - 1; i >= 0; --i) {
            stringBuilder.append(cut[i]);
        }

        return stringBuilder.toString().toUpperCase();
    }

    public static String BytesHexString(String hexStr, int start, int length, boolean reverse) {
        String sub = StrUtil.sub(hexStr, start * 2, (start + length) * 2);
        return reverse ? reverseString(sub) : sub;
    }

    public static String BytesHexString(byte[] b, int start, int length, boolean reverse) {
        byte[] bytes = subByte(b, start, length);
        String hexStr = getHexStr(bytes);
        return reverse ? reverseString(hexStr) : hexStr;
    }

    public static int byte2int(byte b) {
        return b & 255;
    }

    public static byte[] subByte(byte[] b, int off, int length) {
        if (off >= b.length) {
            return new byte[0];
        } else {
            if (b.length - off < length) {
                length = b.length - off;
            }

            byte[] b1 = new byte[length];
            System.arraycopy(b, off, b1, 0, length);
            return b1;
        }
    }

    public static String getGunCode(byte[] msg, int start) {
        return BytesHexString((byte[])msg, 6 + start, 8, false);
    }

    public static String getPileNumber(String gunCode) {
        return StrUtil.sub(gunCode, 0, 14);
    }

    public static int getGunNum(String gunCode) {
        return Convert.toInt(gunCode.substring(gunCode.length() - 2));
    }

    public static int byteArray2short(byte[] b, int offset, boolean reverse) {
        int value = 0;

        for(int i = 0; i < 2; ++i) {
            int shift = reverse ? i * 8 : (1 - i) * 8;
            value += (b[i + offset] & 255) << shift;
        }

        return value;
    }

    public static int byteArray2short2(byte[] b, int offset, boolean reverse) {
        int value = 0;

        for(int i = 0; i < 3; ++i) {
            int shift = reverse ? i * 8 : (1 - i) * 8;
            value += (b[i + offset] & 255) << shift;
        }

        return value;
    }

    public static byte[] short2byteArray(int s, boolean reverse) {
        byte[] src = new byte[2];
        if (reverse) {
            src[1] = (byte)(s >> 8 & 255);
            src[0] = (byte)(s & 255);
        } else {
            src[0] = (byte)(s >> 8 & 255);
            src[1] = (byte)(s & 255);
        }

        return src;
    }

    public static double byteArray2double(byte[] b, int offset, int scale, boolean reverse) {
        int value = byteArray2short(b, offset, reverse);
        return (new BigDecimal(value)).divide(new BigDecimal(Math.pow(10.0, (double)scale))).setScale(scale, 4).doubleValue();
    }

    public static double longbyteArray2double(byte[] b, int offset, int scale, boolean reverse) {
        long value = byteArray2int(b, offset, reverse);
        return (new BigDecimal(value)).divide(new BigDecimal(Math.pow(10.0, (double)scale))).setScale(scale, 4).doubleValue();
    }

    public static byte[] double2byteArray(double d, int scale, boolean reverse) {
        short value = (short)((int)(d * Math.pow(10.0, (double)scale)));
        return short2byteArray(value, reverse);
    }

    public static byte[] double2longbyteArray(double d, int scale, boolean reverse) {
        int value = (int)(d * Math.pow(10.0, (double)scale));
        log.info("value {}", value);
        return int2byteArray(value, reverse);
    }

    public static void main(String[] args) {
        String hexStr1 = getHexStr("SALMN1D43CA371750".getBytes());
        byte[] bytes1 = hexStr2Bytes(hexStr1, false);
        System.out.println(hexStr1);
        System.out.println(Bytes2String(bytes1, 0, bytes1.length, false));
        double pow = Math.pow(10.0, 4.0);
        System.out.println(pow);
        int value = (int)(15.400000000000002 * Math.pow(10.0, 4.0));
        System.out.println(value);
        System.out.println(getHexStr(new byte[]{int2byte(-2)}));
        String hexStr = getHexStr(new byte[]{int2byte(-1)});
        System.out.println(hexStr);
        byte[] bytes = hexStr2Bytes(hexStr, false);
        System.out.println(int2byte(bytes[0]));
    }

    public static String bytes2String(byte[] b, int start, int length, boolean reverse) {
        StringBuilder ret = new StringBuilder();
        int i;
        String hex;
        if (reverse) {
            for(i = start + length - 1; i >= start; --i) {
                hex = String.valueOf((char)(b[i] & 255));
                ret.append(hex.toUpperCase());
            }

            return ret.toString();
        } else {
            for(i = start; i < b.length && i < start + length; ++i) {
                hex = String.valueOf((char)(b[i] & 255));
                ret.append(hex.toUpperCase());
            }

            return ret.toString();
        }
    }

    public static byte[] string2Bytes(String str, boolean reverse) {
        byte[] bytes = str.getBytes();
        if (!reverse) {
            return bytes;
        } else {
            byte[] reverseBytes = new byte[bytes.length];

            for(int i = 0; i < bytes.length; ++i) {
                reverseBytes[i] = bytes[bytes.length - 1 - i];
            }

            return reverseBytes;
        }
    }

    public static byte[] hexStr2Bytes(String src, boolean reverse) {
        src = src.trim().replace(" ", "").toUpperCase(Locale.US);
        int m = 0;
        int n = 0;
        int iLen = src.length() / 2;
        byte[] ret = new byte[iLen];

        for(int i = 0; i < iLen; ++i) {
             m = i * 2 + 1;
             n = m + 1;
            ret[reverse ? iLen - i - 1 : i] = (byte)(Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 255);
        }

        return ret;
    }

    public static String bytesHexString(byte[] b, int start, int length, boolean reverse) {
        StringBuilder ret = new StringBuilder();
        int i;
        String hex;
        if (reverse) {
            for(i = start + length - 1; i >= start; --i) {
                hex = Integer.toHexString(b[i] & 255);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }

                ret.append(hex.toUpperCase());
            }

            return ret.toString();
        } else {
            for(i = start; i < b.length && i < start + length; ++i) {
                hex = Integer.toHexString(b[i] & 255);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }

                ret.append(hex.toUpperCase());
            }

            return ret.toString();
        }
    }

    public static String tailbytesHexString(byte[] b, int length) {
        StringBuilder ret = new StringBuilder();

        for(int i = b.length - 1; i >= b.length - length; --i) {
            String hex = Integer.toHexString(b[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            ret.append(hex.toUpperCase());
        }

        return ret.toString();
    }

    public static String tailbytesHexString(byte[] b, int index, int length) {
        StringBuilder ret = new StringBuilder();
        int startIndex = b.length - index;

        for(int i = startIndex - 1; i >= startIndex - length; --i) {
            String hex = Integer.toHexString(b[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            ret.append(hex.toUpperCase());
        }

        return ret.toString();
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static Date byteArray2CP56Time2a(byte[] content, int p) {
        String year = "20" + formatDateStr(content[p + 6] & 255);
        String month = formatDateStr(content[p + 5] & 255);
        String weekAndDay = getFillCode(Integer.toBinaryString(content[p + 4] & 255), 8, "0");
        String day = weekAndDay.substring(weekAndDay.length() - 5, weekAndDay.length());
        String hour = formatDateStr(content[p + 3] & 255);
        String minute = formatDateStr(content[p + 2] & 255);
        int sec = byteArray2short(content, p, true) / 1000;
        String second = formatDateStr(sec);
        Date date = null;

        try {
            date = sdf.parse(year + "-" + month + "-" + Integer.parseInt(day, 2) + " " + hour + ":" + minute + ":" + second);
        } catch (ParseException var12) {
            ParseException pe = var12;
            pe.printStackTrace();
        }

        return date;
    }

    public static byte[] String2Bytes(String str, boolean reverse) {
        byte[] bytes = str.getBytes();
        if (!reverse) {
            return bytes;
        } else {
            byte[] reverseBytes = new byte[bytes.length];

            for(int i = 0; i < bytes.length; ++i) {
                reverseBytes[i] = bytes[bytes.length - 1 - i];
            }

            return reverseBytes;
        }
    }

    public static int[] halfByte2int(byte b) {
        String bstr = Integer.toBinaryString(b & 255);
        String str = getFillCode(bstr, 8, "0");
        String len = bstr.substring(str.length() - 7, bstr.length());
        String continuedSign = bstr.substring(0, 1);
        return new int[]{Integer.parseInt(continuedSign), Integer.parseInt(len)};
    }

    public static byte[] cp56Time2a2byteArray(String time) {
        Date date = new Date();

        try {
            if (isNotEmpty(time)) {
                date = sdf.parse(time);
            }
        } catch (ParseException var5) {
            ParseException e = var5;
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        byte[] CP56Time2a = new byte[7];
        byte[] seconds = short2byteArray(calendar.get(13) * 1000, true);
        CP56Time2a[0] = seconds[0];
        CP56Time2a[1] = seconds[1];
        CP56Time2a[2] = (byte)calendar.get(12);
        CP56Time2a[3] = (byte)calendar.get(11);
        CP56Time2a[4] = (byte)calendar.get(5);
        CP56Time2a[5] = (byte)(calendar.get(2) + 1);
        CP56Time2a[6] = (byte)(calendar.get(1) % 100);
        return CP56Time2a;
    }

    public static String formatDateStr(int i) {
        String hexStr = String.valueOf(i);
        if (hexStr.length() == 1) {
            hexStr = "0" + hexStr;
        }

        return hexStr;
    }

    public static Date byteArray2Time(byte[] content, int i, boolean b) {
        String year = bytesHexString(content, i + 5, 2, true);
        String month = bytesHexString(content, i + 4, 1, true);
        String day = bytesHexString(content, i + 3, 1, true);
        String hour = bytesHexString(content, i + 2, 1, true);
        String minute = bytesHexString(content, i + 1, 1, true);
        String second = bytesHexString(content, i, 1, true);
        Date date = null;

        try {
            date = sdf.parse(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
        } catch (ParseException var11) {
            ParseException pe = var11;
            pe.printStackTrace();
        }

        return date;
    }

    public static byte[] getCurrentTimeBytes() {
        Calendar current = Calendar.getInstance();
        int millsecond = current.get(13) * 1000;
        int min = current.get(12);
        int hour = current.get(11);
        String dayOfWeek = getFillCode(Integer.toBinaryString(current.get(7) - 1), 8, "0");
        String dayOfMonth = getFillCode(Integer.toBinaryString(current.get(5)), 8, "0");
        int day = Integer.parseInt(dayOfWeek.substring(5, 8) + dayOfMonth.substring(3, 8), 2);
        int month = current.get(2) + 1;
        int year = Integer.parseInt(String.valueOf(current.get(1)).substring(2));
        ByteBuffer out = ByteBuffer.allocate(1024);
        out.put(short2byteArray(millsecond, true));
        out.put((byte)(min & 255));
        out.put((byte)(hour & 255));
        out.put((byte)(day & 255));
        out.put((byte)(month & 255));
        out.put((byte)(year & 255));
        out.flip();
        byte[] octets = new byte[out.remaining()];
        out.get(octets);
        return octets;
    }

    public static String getFillCode(String code, int len, String fillWord) {
        if (len > code.length()) {
            StringBuilder sb = new StringBuilder();

            for(int i = code.length(); i < len; ++i) {
                sb.append(fillWord);
            }

            sb.append(code);
            code = sb.toString();
        }

        return code;
    }

    public static byte[] getFillByteArray(byte[] array, int len) {
        if (len > array.length) {
            byte[] fillArray = new byte[len];
            System.arraycopy(array, 0, fillArray, 0, array.length);
            return fillArray;
        } else {
            return array;
        }
    }

    public static int[] getBinaryArray(int b) {
        int[] barray = new int[8];
        String bstr = getFillCode(Integer.toBinaryString(b), 8, "0");
        barray[7] = Integer.parseInt(bstr.substring(0, 1));
        barray[6] = Integer.parseInt(bstr.substring(1, 2));
        barray[5] = Integer.parseInt(bstr.substring(2, 3));
        barray[4] = Integer.parseInt(bstr.substring(3, 4));
        barray[3] = Integer.parseInt(bstr.substring(4, 5));
        barray[2] = Integer.parseInt(bstr.substring(5, 6));
        barray[1] = Integer.parseInt(bstr.substring(6, 7));
        barray[0] = Integer.parseInt(bstr.substring(7, 8));
        return barray;
    }

    public static synchronized String getFlowId(String uid) {
        String time = (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date());
        return time + uid;
    }

    public static long getMinuteDiff(Date createTime) {
        if (createTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(createTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(new Date());
            long timeTwo = calendar2.getTimeInMillis();
            long timeOne = calendar.getTimeInMillis();
            return (timeTwo - timeOne) / 60000L;
        } else {
            return 0L;
        }
    }

    public static long getMinuteDiff(Time start, Time end) {
        if (start != null && end != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(end);
            long timeTwo = calendar2.getTimeInMillis();
            long timeOne = calendar.getTimeInMillis();
            long diff = (timeTwo - timeOne) / 60000L;
            return diff < 0L ? diff + 1440L : diff;
        } else {
            return 0L;
        }
    }

    public static long getHourDiff(Date time) {
        if (time != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(new Date());
            long timeTwo = calendar2.getTimeInMillis();
            long timeOne = calendar.getTimeInMillis();
            return (timeTwo - timeOne) / 3600000L;
        } else {
            return 0L;
        }
    }

    public static byte[] calcCrc16(ByteBuffer out) {
        out.flip();
        byte[] octets = new byte[out.remaining()];
        out.get(octets);
        int cal = CRC16.calcCrc16(octets, 2, octets.length - 2);
        byte[] array = short2byteArray(cal, true);
        out.limit(octets.length + 2);
        return array;
    }

    public static String getCurrentTime() {
        return sdf.format(new Date());
    }

    public static void downMessage(IoSession session, byte[] countBytes) {
        ByteBuffer out = ByteBuffer.allocate(6);
        out.put(new byte[]{104, 4});
        out.put(new byte[]{1, 0});
        out.put(countBytes);
        out.flip();
        byte[] octets = new byte[out.remaining()];
        out.get(octets);
        IoBuffer o = IoBuffer.wrap(octets);
        session.write(o);
    }

    public static synchronized String getFlowNum(String terminalId) {
        String time = (new SimpleDateFormat("yyyyMMddHHmmssSS")).format(new Date());
        String flowId;
        if (time.length() == 17) {
            flowId = terminalId + time.substring(0, time.length() - 1);
        } else {
            flowId = terminalId + time;
        }

        return flowId;
    }

    public static String date2String(Date date) {
        return sdf.format(date);
    }

    public static byte[] getCurrentCP56TimeBytes() {
        Date date = new Date();
        return cp56Time2a2byteArray(sdf.format(date));
    }

    public static long sumTime(Date stateTime, Date endTime) {
        long stateTimeLong = stateTime.getTime();
        long endTimeLong = endTime.getTime();
        long minute = (endTimeLong - stateTimeLong) / 60000L;
        return minute;
    }

}
