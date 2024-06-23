package com.example.demo.netty.Mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 编码
 *
 * @author yuan dian
 * @date 2019/1/22
 * @time 9:02
 */
@Slf4j
public class LnContextEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession ioSession, Object message, ProtocolEncoderOutput output) {
        IoBuffer ioBuffer;
        if (message instanceof IoBuffer) {
            ioBuffer = (IoBuffer) message;
        } else if (message instanceof ContextProtocol) {
            ContextProtocol protocol = (ContextProtocol) message;
            ioBuffer = protocol.convertIoBuffer();
        } else {
            log.warn("报文格式错误，不予下发：{}", message);
            return;
        }

        // 数据写入完毕后要调用 flip方法切换模式，不然后续读取不到数据
        ioBuffer.flip();
        // 数据写出
        output.write(ioBuffer);
        output.flush();
    }
}