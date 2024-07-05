package com.example.demo.netty.Mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

public class MinaSocketUtil {
    private static final Logger log = LoggerFactory.getLogger(MinaSocketUtil.class);
    private static final ConcurrentHashMap<String, IoSession> SESSION_MAP = new ConcurrentHashMap();

    public MinaSocketUtil() {
    }

    public static ConcurrentHashMap<String, IoSession> getSessionMap() {
        return SESSION_MAP;
    }

    public static void cacheSession(String terminal, IoSession ioSession) {
        SESSION_MAP.put(terminal, ioSession);
    }

    public static IoSession getSession(String terminal) {
        return (IoSession)SESSION_MAP.get(terminal);
    }

    public static void removeSession(String terminal) {
        SESSION_MAP.remove(terminal);
    }

    public static void sendChargingModel(IoSession session) {
        ByteBuffer out = ByteBuffer.allocate(15);
        out.put((byte)104);
        out.put((byte)11);
        out.put(Utils.hexStr2Bytes("0200", false));
        out.put(Utils.int2byte(0));
        out.put((byte)9);
        out.put(Utils.hexStr2Bytes(session.getAttribute("pileCode").toString(), false));
        out.put(Utils.calcCrc16(out));
        out.flip();
        byte[] octets = new byte[out.remaining()];
        out.get(octets);
        IoBuffer o = IoBuffer.wrap(octets);
        session.write(o);
        String strHex = Utils.bytesHexString(octets, 0, octets.length, false);
        log.info("客户端开始请求计费模式下发成功,十六进制{}", strHex);
    }

    public static void send0x13Data(IoSession ioSession, String gunCode, int wStatus, int cStatus) {
        ByteBuffer out = ByteBuffer.allocate(82);
        out.put((byte)104);
        out.put((byte)64);
        out.put(Utils.hexStr2Bytes("1A03", false));
        out.put(Utils.int2byte(0));
        out.put((byte)19);
        out.put(Utils.hexStr2Bytes("00000000000000000000000000000000", false));
        out.put(Utils.hexStr2Bytes(gunCode, false));
        out.put((byte)wStatus);
        out.put((byte)0);
        out.put((byte)cStatus);
        out.put(Utils.hexStr2Bytes("0002", false));
        out.put(Utils.hexStr2Bytes("0000", false));
        out.put(Utils.hexStr2Bytes("00", false));
        out.put(Utils.hexStr2Bytes("0000000000000003", false));
        out.put(Utils.hexStr2Bytes("04", false));
        out.put(Utils.hexStr2Bytes("00", false));
        out.put(Utils.hexStr2Bytes("0000", false));
        out.put(Utils.hexStr2Bytes("0000", false));
        out.put(Utils.hexStr2Bytes("00000099", false));
        out.put(Utils.hexStr2Bytes("00000018", false));
        out.put(Utils.hexStr2Bytes("00000000", false));
        out.put(Utils.hexStr2Bytes("0000", false));
        out.put(Utils.calcCrc16(out));
        out.flip();
        byte[] octets = new byte[out.remaining()];
        out.get(octets);
        IoBuffer o = IoBuffer.wrap(octets);
        ioSession.write(o);
        log.info("send0x13Data：{}, {}", gunCode, Utils.getHexStr(octets));
    }

    public static void sendHeartBeatData(IoSession ioSession, String gunCode) {
        ByteBuffer out = ByteBuffer.allocate(17);
        out.put((byte)104);
        out.put((byte)13);
        out.put(Utils.hexStr2Bytes("0010", false));
        out.put((byte)0);
        out.put((byte)3);
        out.put(Utils.hexStr2Bytes(gunCode, false));
        out.put((byte)0);
        out.put(Utils.calcCrc16(out));
        out.flip();
        byte[] octets = new byte[out.remaining()];
        out.get(octets);
        IoBuffer ioBuffer = IoBuffer.wrap(octets);
        log.info("gunCode {}, sendHeardData req {}", gunCode, Utils.getHexStr(octets));
        ioSession.write(ioBuffer);
    }

    public static void cardCharge(IoSession ioSession, String gunCode, String cardNo) {
        ByteBuffer out = ByteBuffer.allocate(59);
        out.put((byte)104);
        out.put(Utils.int2byte(55));
        out.put(Utils.hexStr2Bytes("0004", false));
        out.put(Utils.int2byte(0));
        out.put((byte)49);
        out.put(Utils.hexStr2Bytes(gunCode, false));
        out.put(Utils.int2byte(1));
        out.put(Utils.int2byte(0));
        out.put(Utils.hexStr2Bytes("00000000" + cardNo, false));
        out.put(Utils.hexStr2Bytes("00000000000000000000000000000000", false));
        out.put(Utils.hexStr2Bytes("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", false));
        out.put(Utils.calcCrc16(out));
        out.flip();
        byte[] octets = new byte[out.remaining()];
        out.get(octets);
        IoBuffer o = IoBuffer.wrap(octets);
        ioSession.write(o);
        String strHex = Utils.bytesHexString(octets, 0, octets.length, false);
        log.info("发送刷卡请求{}", strHex);
    }

    public static void vinCharge(IoSession ioSession, String gunCode, String vinCode) {
        ByteBuffer out = ByteBuffer.allocate(59);
        out.put((byte)104);
        out.put(Utils.int2byte(55));
        out.put(Utils.hexStr2Bytes("0004", false));
        out.put(Utils.int2byte(0));
        out.put((byte)49);
        out.put(Utils.hexStr2Bytes(gunCode, false));
        out.put(Utils.int2byte(3));
        out.put(Utils.int2byte(0));
        out.put(Utils.hexStr2Bytes("0000000000000000", false));
        out.put(Utils.hexStr2Bytes("00000000000000000000000000000000", false));
        out.put(Utils.String2Bytes(vinCode, true));
        out.put(Utils.calcCrc16(out));
        out.flip();
        byte[] octets = new byte[out.remaining()];
        out.get(octets);
        IoBuffer o = IoBuffer.wrap(octets);
        ioSession.write(o);
        String strHex = Utils.bytesHexString(octets, 0, octets.length, false);
        log.info("发送vin码充电请求{}", strHex);
    }
}
