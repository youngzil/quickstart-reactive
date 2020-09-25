package org.quickstart.rsocket.zerocopy;

import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;

/**
 * <p>
 * 描述: [功能描述]
 * </p >
 *
 * @author yangzl
 * @version v1.0
 * @date 2020/9/25 13:19
 */
public class ZeroCopyClient {

  public static void main(String[] args) {
    RSocket clientRSocket = RSocketConnector.create()
        // Enable Zero Copy
        .payloadDecoder(PayloadDecoder.ZERO_COPY)//
        .connect(TcpClientTransport.create(7878))//
        .block();
  }

}
