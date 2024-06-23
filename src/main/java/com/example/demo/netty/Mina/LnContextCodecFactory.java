package com.example.demo.netty.Mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 字符编码工厂类
 *
 * @author yuan dian
 * @date 2019/1/22
 * @time 9:02
 */
public class LnContextCodecFactory implements ProtocolCodecFactory {
    /**
     * 报文编码器
     */
    private final LnContextEncoder encoder;

    /**
     * 报文解码器
     */
    private final LnContextDecoder decoder;

    public LnContextCodecFactory() {
        this.decoder = new LnContextDecoder();
        this.encoder = new LnContextEncoder();
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) {
        return this.encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) {
        return this.decoder;
    }
}