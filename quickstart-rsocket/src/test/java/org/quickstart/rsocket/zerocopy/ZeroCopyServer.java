package org.quickstart.rsocket.zerocopy;

import org.quickstart.rsocket.ZeroCopyExample.PingHandler;

import io.rsocket.core.RSocketServer;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.server.TcpServerTransport;

/**
 * <p>
 * 描述: [功能描述]
 * </p >
 *
 * @author yangzl
 * @version v1.0
 * @date 2020/9/25 13:18
 */
public class ZeroCopyServer {

  public static void main(String[] args) {
    RSocketServer.create(new PingHandler())
        // Enable Zero Copy
        .payloadDecoder(PayloadDecoder.ZERO_COPY)//
        .bind(TcpServerTransport.create(7878))//
        .block()//
        .onClose()//
        .block();
  }

}
