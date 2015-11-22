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

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.router.RouteResult;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Tom Baeyens
 */
public class Request {
  
  static final Logger log = LoggerFactory.getLogger(Request.class);

  FullHttpRequest fullHttpRequest;
  RouteResult<?> route;
  ServiceLocator serviceLocator;

  public Request(FullHttpRequest fullHttpRequest, RouteResult<?> route, ServiceLocator serviceLocator) {
    this.fullHttpRequest = fullHttpRequest;
    this.route = route;
    this.serviceLocator = serviceLocator;
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
  
  public <T> T getContent(Class<T> type) {
    String content = getContentStringUtf8();
    return serviceLocator.getGson().fromJson(content, type);
  }
  
  public boolean isDecodingFailed() {
    return fullHttpRequest.getDecoderResult().isFailure();
  }

  public String getContentString(Charset charset) {
    String content = fullHttpRequest.content().toString(charset);
    log.debug(">>> "+content);
    return content;
  }
  
  public String getHeader(String name) {
    HttpHeaders headers = fullHttpRequest.headers();
    return headers.get(name);
  }
}
