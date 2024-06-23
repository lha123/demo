package com.example.demo.netty.Mina;

import lombok.Getter;
import lombok.ToString;
import org.apache.mina.core.buffer.IoBuffer;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 充电协议报文实体类
 *
 * @Author: douziyu
 * @Date: 2024/2/1 17:48
 */
@Getter
@ToString
public class ContextProtocol {
    /**
     * 消息体不加密
     */
    public static final int ENCRYPT_NO = 0;

    /**
     * 消息体加密
     */
    public static final int ENCRYPT_YES = 1;

    /**
     * 一帧数据的起始标志
     */
    public static final byte HEADER = 0x68;

    /**
     * 数据长度，占用 1个字节
     */
    private final int length;

    /**
     * 序列号域，占用 2个字节
     */
    private final int seq;

    /**
     * 加密标志，占用 1个字节
     */
    private final int encryptFlag;

    /**
     * 帧类型标志，占用 1个字节
     */
    private final int protocolType;

    /**
     * 消息体
     */
    private final byte[] content;

    /**
     * 帧校验域，占用 2个字节
     */
    private byte[] sign;

    /**
     * 初始化一个下行报文实体对象
     */
    private ContextProtocol(int seq, int encryptFlag, int protocolType, byte[] content) {
        this.seq = seq;
        this.encryptFlag = encryptFlag;
        this.protocolType = protocolType;
        this.content = content;
        this.length = content.length + 4;
    }

    /**
     * 将接收到的上行报文转换成实体对象
     *
     * @param array 报文数组，数据从 序列号域开始到最后
     */
    private ContextProtocol(byte[] array) {
        this.length = array.length - 2;
        this.seq = Utils.byteArray2Short(array, 0, false);
        this.encryptFlag = Byte.toUnsignedInt(array[2]);
        this.protocolType = Byte.toUnsignedInt(array[3]);
        this.content = Arrays.copyOfRange(array, 4, this.length);
        this.sign = Arrays.copyOfRange(array, this.length, array.length);
    }

    /**
     * 生成一个下行的报文实体对象
     *
     * @param seq          序列号
     * @param protocolType 报文帧类型
     * @return 报文实体
     */
    public static ContextProtocol convertDownContextProtocol(int seq, int protocolType, byte[] content) {
        return new ContextProtocol(seq, ENCRYPT_NO, protocolType, content);
    }

    /**
     * 生成一个上行的报文实体对象
     *
     * @param array 接收到的报文字节数组
     * @return 报文实体
     */
    public static ContextProtocol convertUpContextProtocol(byte[] array) {
        return new ContextProtocol(array);
    }

    /**
     * 将报文转换成 IoBuffer
     *
     * @return IoBuffer
     */
    public IoBuffer convertIoBuffer() {
        ByteBuffer byteBuffer = convertByteBuffer();
        // 补充帧校验域，占2个字节
        this.sign = Utils.calcCrc16(byteBuffer);
        byteBuffer.put(this.sign);

        return IoBuffer.wrap(byteBuffer);
    }

    /**
     * 返回报文的十六进制标识形式的字符串
     *
     * @return
     */
    public String getHexString() {
        ByteBuffer byteBuffer = convertByteBuffer();
        byteBuffer.put(this.sign);

        byte[] array = byteBuffer.array();
        return Utils.bytes2HexString(array, 0, array.length, false);
    }

    private ByteBuffer convertByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.length + 4);
        byteBuffer.put(ContextProtocol.HEADER);
        byteBuffer.put((byte) this.length);
        byteBuffer.put(Utils.short2ByteArray(this.seq, false));
        byteBuffer.put((byte) this.encryptFlag);
        byteBuffer.put((byte) this.protocolType);
        byteBuffer.put(this.content);

        return byteBuffer;
    }
}
