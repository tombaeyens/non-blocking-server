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
package be.tombaeyens.cbe.http.framework;

import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.router.RouteResult;
import io.netty.handler.codec.http.router.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ServerChannelHandler extends SimpleChannelInboundHandler<Object> {
  
  private static final Logger log = LoggerFactory.getLogger(ServerChannelHandler.class);
  
  private final ServiceLocator serviceLocator;
  private final Router<Class< ? extends RequestHandler>> router;
  private FullHttpRequest fullHttpRequest;
  private RouteResult<Class< ? extends RequestHandler>> route;

  public ServerChannelHandler(Server server) {
    this.serviceLocator = server.getServiceLocator();
    this.router = serviceLocator.getRouter();
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
    if (msg instanceof FullHttpRequest) {
      this.fullHttpRequest = (FullHttpRequest) msg;

      if (HttpHeaders.is100ContinueExpected(fullHttpRequest)) {
        send100Continue(ctx);
      }

      // DecoderResult decoderResult = httpRequest.getDecoderResult();
      // if (decoderResult.isFailure()) {
      // decoderResult.cause();
      // }

      this.route = router.route(fullHttpRequest.getMethod(), fullHttpRequest.getUri());
      
      Request request = new Request(fullHttpRequest, route, serviceLocator);
      Response response = new Response(ctx, serviceLocator);
      RequestHandler requestHandler = instantiateRequestHandler();
      requestHandler.request = request;
      requestHandler.response = response;
      requestHandler.serviceLocator = serviceLocator;
      
      HttpHeaders headers = fullHttpRequest.headers();
      try {
        System.err.println();
        Request.log.debug(">>> "+fullHttpRequest.getMethod()+" "+fullHttpRequest.getUri());
//        for (String headerName: headers.names()) {
//          for (String value: headers.getAll(headerName)) {
//            Request.log.debug(">>>   "+headerName+": "+value);
//          }
//        }

        requestHandler.handle();
      } catch (RuntimeException e) {
        if (e instanceof BadRequestException) {
          response.statusBadRequest();
        } else {
          response.statusInternalServerError();
        }
        response.content("{ \"message\": \"oops\" }");
        response.headerContentTypeApplicationJson();
        requestHandlerException(e);
      } finally {
        HttpResponse httpResponse = response.getHttpResponse();
        if (!HttpHeaders.isKeepAlive(fullHttpRequest)) {
          ctx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
        } else {
          headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
          ctx.writeAndFlush(httpResponse);
        }
      }
    }
  }

  protected void requestHandlerException(RuntimeException e) {
    log.error("Request handler exception", e);
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
