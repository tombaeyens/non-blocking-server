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
package be.tombaeyens.cbe.test;

import io.netty.handler.codec.http.router.Router;
import be.tombaeyens.cbe.http.framework.RequestHandler;
import be.tombaeyens.cbe.http.framework.ServerChannelHandler;
import be.tombaeyens.cbe.http.framework.ServerChannelInitializer;


/**
 * @author Tom Baeyens
 */
public class TestServerChannelInitializer extends ServerChannelInitializer {

  protected TestServer testServer;
  
  public TestServerChannelInitializer(Router<Class< ? extends RequestHandler>> router, TestServer testServer) {
    super(router);
    this.testServer = testServer;
  }

  @Override
  protected ServerChannelHandler createServerChannelHandler() {
    return new TestServerChannelHandler(router, serviceLocator, this);
  }
}
