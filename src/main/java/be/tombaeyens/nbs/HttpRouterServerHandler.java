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

import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.router.RouteResult;
import io.netty.handler.codec.http.router.Router;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class HttpRouterServerHandler extends SimpleChannelInboundHandler<Object> {
  
  private static InternalLogger log = InternalLoggerFactory.getInstance(HttpRouterServerHandler.class);

  private final Router<Class< ? extends RequestHandler>> router;

  public HttpRouterServerHandler(Router<Class< ? extends RequestHandler>> router) {
    this.router = router;
  }

  private HttpRequest httpRequest;
  private List<HttpContent> httpContentPieces;
  private RouteResult<Class< ? extends RequestHandler>> route;

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
    if (msg instanceof HttpRequest) {
      this.httpRequest = (HttpRequest) msg;

      if (HttpHeaders.is100ContinueExpected(httpRequest)) {
        send100Continue(ctx);
      }

      // DecoderResult decoderResult = httpRequest.getDecoderResult();
      // if (decoderResult.isFailure()) {
      // decoderResult.cause();
      // }

      this.route = router.route(httpRequest.getMethod(), httpRequest.getUri());
    }

    if (msg instanceof HttpContent) {
      if (httpContentPieces == null) {
        httpContentPieces = new ArrayList<HttpContent>();
      }
      httpContentPieces.add((HttpContent) msg);
      log.debug("adding piece %s", msg);

      if (msg instanceof LastHttpContent) {
        Request request = new Request(httpRequest, route, httpContentPieces);
        Response response = new Response(ctx);
        RequestHandler requestHandler = instantiateRequestHandler();
        requestHandler.handle(request, response);

        HttpResponse httpResponse = response.getHttpResponse();
        if (!HttpHeaders.isKeepAlive(httpRequest)) {
          ctx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
        } else {
          httpRequest.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
          ctx.writeAndFlush(httpResponse);
        }
      }
    }
  }

  protected RequestHandler instantiateRequestHandler() {
    Class< ? extends RequestHandler> requestHandlerClass = route.target();
    try {
      return requestHandlerClass.newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Couldn't instantiate request handler " + requestHandlerClass + " : " + e.getMessage(), e);
    }
  }

  private static void send100Continue(ChannelHandlerContext ctx) {
    FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
    ctx.write(response);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
