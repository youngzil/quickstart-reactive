package org.quickstart.rsocket;

import io.rsocket.AbstractRSocket;
import io.rsocket.ConnectionSetupPayload;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.SocketAcceptor;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Mono;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/11/17 11:54
 */
public class ZeroCopyExample {

  public static void main(String[] args) {

    // Example Java Server:

    RSocketFactory.receive()
        // Enable Zero Copy
        .frameDecoder(PayloadDecoder.ZERO_COPY).acceptor(new PingHandler()).transport(TcpServerTransport.create(7878)).start().subscribe();
        // .block().onClose().block();

    // Example Java Client:
    Mono<RSocket> client = RSocketFactory.connect()
        // Enable Zero Copy
        .frameDecoder(PayloadDecoder.ZERO_COPY).transport(TcpClientTransport.create(7878)).start();

    client.block().requestResponse(DefaultPayload.create("Hello1")).map(Payload::getDataUtf8).onErrorReturn("error").doOnNext(System.out::println)
        .block();

  }

  public static class PingHandler implements SocketAcceptor {
    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {

      return Mono.just(new RSocketImpl());
    }
  }

  private static class RSocketImpl extends AbstractRSocket {
    @Override
    public Mono<Payload> requestResponse(Payload p) {
      boolean fail = false;
      if (fail) {
        return Mono.error(new Throwable());
      } else {
        return Mono.just(p);
      }
    }

  }

}
