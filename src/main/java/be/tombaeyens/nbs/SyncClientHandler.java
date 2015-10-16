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
package be.tombaeyens.nbs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;


/**
 * @author Tom Baeyens
 */
public class SyncClientHandler extends SimpleChannelInboundHandler<HttpObject> {

  @Override
  public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
      if (msg instanceof FullHttpResponse) {
        FullHttpResponse response = (FullHttpResponse) msg;

          System.err.println("STATUS: " + response.getStatus());
          System.err.println("VERSION: " + response.getProtocolVersion());
          System.err.println();

          if (!response.headers().isEmpty()) {
              for (String name: response.headers().names()) {
                  for (String value: response.headers().getAll(name)) {
                      System.err.println("HEADER: " + name + " = " + value);
                  }
              }
              System.err.println();
          }

          if (HttpHeaders.isTransferEncodingChunked(response)) {
              System.err.println("CHUNKED CONTENT {");
          } else {
              System.err.println("CONTENT {");
          }
      }
      if (msg instanceof HttpContent) {
          HttpContent content = (HttpContent) msg;

          System.err.print(content.content().toString(CharsetUtil.UTF_8));
          System.err.flush();

          if (content instanceof LastHttpContent) {
              System.err.println("} END OF CONTENT");
              ctx.close();
          }
      }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      cause.printStackTrace();
      ctx.close();
  }

}
