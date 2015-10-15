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

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.router.RouteResult;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.nio.charset.Charset;
import java.util.List;


/**
 * @author Tom Baeyens
 */
public class Request {

  private static InternalLogger log = InternalLoggerFactory.getInstance(Request.class);

  HttpRequest httpRequest;
  List<HttpContent> contentPieces;
  RouteResult<?> route;

  public Request(HttpRequest httpRequest, RouteResult<?> route, List<HttpContent> content) {
    this.httpRequest = httpRequest;
    this.contentPieces = content;
    this.route = route;
  }

  public String getParameter(String name) {
    return route.param(name);
  }

  public List<String> getParameters(String name) {
    return route.params(name);
  }

  public String getPathParameter(String name) {
    return route.pathParams().get(name);
  }

  public String getQueryParameter(String name) {
    return route.queryParam(name);
  }

  public List<String> getQueryParameters(String name) {
    return route.queryParams().get(name);
  }

  public String getContentStringUtf8() {
    return getContentString(CharsetUtil.UTF_8);
  }
  
  public boolean hasContentDecoderResultFailure() {
    if (contentPieces==null || contentPieces.isEmpty()) {
      return false;
    }
    for (HttpContent httpContentPiece: contentPieces){
      DecoderResult decoderResult = httpContentPiece.getDecoderResult();
      if (decoderResult.isFailure()) {
        return true;
      }
    }
    return false;
  }

  public String getContentString(Charset charset) {
    if (contentPieces==null || contentPieces.isEmpty()) {
      return null;
    }
    StringBuffer content = new StringBuffer();
    int count = 0;
    for (HttpContent httpContentPiece: contentPieces){
      ByteBuf byteBufPiece = httpContentPiece.content();
      if (byteBufPiece.isReadable()) {
        log.debug("Reading content piece "+count++);
        content.append(byteBufPiece.toString(charset));
      } else {
        log.debug("Reading unreadable content piece "+count++);
      }
    }
    return content.toString();
  }

  public String getHeader(String name) {
    HttpHeaders headers = httpRequest.headers();
    return headers.get(name);
  }
}
