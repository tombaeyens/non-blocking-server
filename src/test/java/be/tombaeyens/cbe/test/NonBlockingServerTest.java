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

import org.apache.http.client.fluent.Request;
import org.junit.BeforeClass;
import org.junit.Test;

import be.tombaeyens.cbe.http.framework.Server;


/**
 * @author Tom Baeyens
 */
public class NonBlockingServerTest {
  
  static { TestLogConfiguration.initialize(); }
  
  public static Server server;
  
  @BeforeClass
  public static void startServer() {
    if (server==null) {
      server = new TestServer()
        .startup();
    }
  }

  @Test
  public void testOne() {
    String responseString = POST("collections")
      .bodyJson("{ \"name\" : \"invoices\" }")
      .execute()
      .assertStatusCreated()
      .returnContent()
      .asString();
  }
  
  public Request GET(final String path) {
    return configure(Request.Get("http://localhost:"+server.getPort()+"/"+path));
  }
  
  public Request POST(final String path) {
    return configure(Request.Post("http://localhost:"+server.getPort()+"/"+path));
  }

  public Request PUT(final String path) {
    return configure(Request.Put("http://localhost:"+server.getPort()+"/"+path));
  }

  /** configurations applied to all requests */
  private Request configure(Request request) {
    // request.socketTimeout(2000);  // default is -1
    // request.connectTimeout(2000); // default is -1
    request.setTestServer((TestServer)server);
    return request;
  }
}
