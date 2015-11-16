/* Copyright (c) 2014, Effektif GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package be.tombaeyens.cbe.http.framework;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.BadClientSilencer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.router.Router;


/** Initializes each channel.  
 * One object for the whole object, initialized in the {@link Server}.
 * 
 * @author Tom Baeyens
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
  
  Router<Class<? extends RequestHandler>> router;
  ServiceLocator serviceLocator;
  
  public ServerChannelInitializer(Router<Class< ? extends RequestHandler>> router) {
    this.router = router;
    this.serviceLocator = new ServiceLocator();
  }

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new HttpServerCodec());
    pipeline.addLast(new HttpObjectAggregator(1024*1024)); // max 1 MB
    pipeline.addLast(new ServerHandler(router, serviceLocator));
    pipeline.addLast(new BadClientSilencer());
  }
}
