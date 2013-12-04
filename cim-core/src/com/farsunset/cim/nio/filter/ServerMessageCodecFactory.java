 
package com.farsunset.cim.nio.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/** 
 * @author 3979434@qq.com
 */
public class ServerMessageCodecFactory implements ProtocolCodecFactory {

    private final ServerMessageEncoder encoder;

    private final ServerMessageDecoder decoder;

    /**
     * Constructor.
     */
    public ServerMessageCodecFactory() {
        encoder = new ServerMessageEncoder();
        decoder = new ServerMessageDecoder();
    }

    /**
     * Returns a new (or reusable) instance of ProtocolEncoder.
     */
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    /**
     * Returns a new (or reusable) instance of ProtocolDecoder.
     */
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

}
