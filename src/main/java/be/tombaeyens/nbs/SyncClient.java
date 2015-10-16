/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. */
package be.tombaeyens.nbs;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.ClientCookieEncoder;
import io.netty.handler.codec.http.DefaultCookie;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.concurrent.GenericFutureListener;


/**
 * @author Tom Baeyens
 */
public class SyncClient {

  EventLoopGroup group = new NioEventLoopGroup();
  Channel ch;

  public void main() {
    // Configure the client.
    try {
        Bootstrap b = new Bootstrap();
        b.group(group)
         .channel(NioSocketChannel.class)
         .handler(new ChannelInitializer<SocketChannel>(){
           @Override
           public void initChannel(SocketChannel ch) {
               ChannelPipeline p = ch.pipeline();
               p.addLast(new HttpClientCodec());
               p.addLast(new HttpObjectAggregator(1048576));
               p.addLast(new SyncClientHandler());
           }
         });

        // Make the connection attempt.
        Channel ch = b.connect(host, port).sync().channel();

        // Prepare the HTTP request.
        HttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
        request.headers().set(HttpHeaders.Names.HOST, host);
        request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
        request.headers().set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);

        // Set some example cookies.
        request.headers().set(
                HttpHeaders.Names.COOKIE,
                ClientCookieEncoder.encode(
                        new DefaultCookie("my-cookie", "foo"),
                        new DefaultCookie("another-cookie", "bar")));

        // Send the HTTP request.
        ch.writeAndFlush(request).sync().addListener(new GenericFutureListener<Future<? super Void>>() {
        });

        // Wait for the server to close the connection.
        ch.closeFuture().sync();
    } finally {
        // Shut down executor threads to exit.
        group.shutdownGracefully();
    }

  }
}
