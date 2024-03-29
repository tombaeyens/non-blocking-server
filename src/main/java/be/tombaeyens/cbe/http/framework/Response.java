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
package be.tombaeyens.cbe.http.framework;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Tom Baeyens
 */
public class Response {

  private static final Logger log = LoggerFactory.getLogger(Response.class); 
  
  protected ChannelHandlerContext channelHandlerContext;
  protected HttpVersion httpVersion = HttpVersion.HTTP_1_1;
  protected ByteBuf byteBuf = Unpooled.buffer();
  protected HttpResponseStatus status = HttpResponseStatus.OK;
  protected HttpHeaders headers = new DefaultHttpHeaders();
  protected ServiceLocator serviceLocator;
  protected String contentStringForLog;

  public Response(ChannelHandlerContext channelHandlerContext, ServiceLocator serviceLocator) {
    this.channelHandlerContext = channelHandlerContext;
    this.serviceLocator =serviceLocator;
  }

  public Response statusOk() {
    return status(HttpResponseStatus.OK);
  }

  public Response statusNotFound() {
    return status(HttpResponseStatus.NOT_FOUND);
  }

  public Response statusBadRequest() {
    return status(HttpResponseStatus.BAD_REQUEST);
  }

  public Response statusInternalServerError() {
    return status(HttpResponseStatus.INTERNAL_SERVER_ERROR);
  }
  
  public Response statusCreated() {
    return status(HttpResponseStatus.CREATED);
  }
  
  public Response statusNoContent() {
    return status(HttpResponseStatus.NO_CONTENT);
  }

  public Response status(HttpResponseStatus status) {
    this.status = status;
    return this;
  }

  public Response content(String content) {
    return content(content, CharsetUtil.UTF_8);
  }

  public Response content(String content, Charset charset) {
    if (content!=null) {
      contentStringForLog = content; // the log is produced in getHttpResponse() below 
      byteBuf.writeBytes(content.getBytes(charset));
    }
    return this;
  }

  public Response contentJson(Object o) {
    String json = serviceLocator.getJson().write(o);
    headerContentTypeApplicationJson();
    content(json);
    return this;
  }

  public Response header(String name, String value) {
    headers.add(name, value);
    return this;
  }

  public Response headerContentTypeApplicationJson() {
    headerContentType("application/json");
    return this;
  }

  public Response headerContentType(String contentType) {
    header(HttpHeaders.Names.CONTENT_TYPE, contentType);
    return this;
  }

  public HttpResponse getHttpResponse() {
    autoAddContentLengthHeader();
    log.debug("<<< "+status+(contentStringForLog!=null?" "+contentStringForLog:""));
    DefaultFullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(
            httpVersion, 
            status,
            byteBuf);
    fullHttpResponse.headers().add(headers);
    return fullHttpResponse;
  }

  protected void autoAddContentLengthHeader() {
    int readableBytes = byteBuf.readableBytes();
    header(HttpHeaders.Names.CONTENT_LENGTH, Integer.toString(readableBytes));
  }
}
