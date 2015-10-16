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
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.router.Router;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class Server {

  private static InternalLogger log = InternalLoggerFactory.getInstance(ServerHandler.class);
  
  protected int port = 8000;
  NioEventLoopGroup bossGroup;
  NioEventLoopGroup workerGroup;
  Channel channel;
  
  public Server port(int port) {
    this.port = port;
    return this;
  }
  
  public Server startup() {
    bossGroup = new NioEventLoopGroup(1);
    workerGroup = new NioEventLoopGroup();

    try {
      final Router<Class<? extends RequestHandler>> router = new Router<Class< ? extends RequestHandler>>()
        .POST("/articles/:id", HelloPost.class)
        .notFound(Oops.class);

      ChannelInitializer<SocketChannel> channelInitializer = new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline()
              .addLast(new HttpServerCodec())
              .addLast(new HttpObjectAggregator(50*1024)) // max 50 KB
              .addLast(new ServerHandler(router))
              .addLast(new BadClientSilencer());
          }
        };

      ServerBootstrap serverBootstrap = new ServerBootstrap()
        .group(bossGroup, workerGroup)
        .childOption(ChannelOption.TCP_NODELAY, java.lang.Boolean.TRUE)
        .childOption(ChannelOption.SO_KEEPALIVE, java.lang.Boolean.TRUE)
        .channel(NioServerSocketChannel.class)
        .childHandler(channelInitializer);

      channel = serverBootstrap
        .bind("localhost", port)
        .sync()
        .channel();
      
      log.debug("Server started: http://127.0.0.1:" + port + "/\n" + router);

      
    } catch (Throwable t) {
      t.printStackTrace();
    } finally {
      shutdown();
    }
    
    return this;
  }
  
  public void waitForShutdown() {
    try {
      channel.closeFuture().sync();
    } catch (Throwable t) {
      t.printStackTrace();
    } finally {
      shutdown();
    }
  }

  public void shutdown() {
    bossGroup.shutdownGracefully();
    workerGroup.shutdownGracefully();
  }
}