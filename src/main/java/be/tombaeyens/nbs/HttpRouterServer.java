/*
 * Copyright 2015 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package be.tombaeyens.nbs;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.BadClientSilencer;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.router.Router;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import be.tombaeyens.nbs.SimpleLoggerFactory.Level;

public class HttpRouterServer {

  public static final int PORT = 8000;

  public static void main(String[] args) throws Exception {

    InternalLoggerFactory.setDefaultFactory(SimpleLoggerFactory.INSTANCE);
    SimpleLoggerFactory.INSTANCE.configure("io.netty", Level.INFO);

    InternalLogger log = InternalLoggerFactory.getInstance(HttpRouterServer.class);

    final Router<Class< ? extends RequestHandler>> router = new Router<Class< ? extends RequestHandler>>().POST("/articles/:id", HelloPost.class).notFound(
            new Oops());

    log.debug("Router:\n\n" + router);

    NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup).childOption(ChannelOption.TCP_NODELAY, java.lang.Boolean.TRUE)
              .childOption(ChannelOption.SO_KEEPALIVE, java.lang.Boolean.TRUE).channel(NioServerSocketChannel.class)
              .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ch.pipeline().addLast(new HttpServerCodec()).addLast(new HttpRouterServerHandler(router)).addLast(new BadClientSilencer());
                }
              });

      Channel ch = b.bind(PORT).sync().channel();
      log.debug("Server started: http://127.0.0.1:" + PORT + '/');

      URL url = new URL("http://localhost:" + PORT + "/articles/9s8d7fs");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setDoOutput(true);
      con.setRequestMethod("POST");
      OutputStream outputStream = con.getOutputStream();
      byte[] bytes = "bytesbytesbytesbytesbytesbytesbytes".getBytes(Charset.forName("UTF-8"));
      for (int i = 0; i < 1000; i++) {
        outputStream.write(bytes);
        outputStream.flush();
      }
      outputStream.close();
      con.connect();

      log.debug("Executing request : " + url);
      int responseCode = con.getResponseCode();
      log.debug("Response Code : " + responseCode);
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      log.debug("Response : " + response.toString());

      // ch.closeFuture().sync();
    } catch (Throwable t) {
      t.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}