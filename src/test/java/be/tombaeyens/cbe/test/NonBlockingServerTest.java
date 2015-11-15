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
import org.apache.http.entity.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import be.tombaeyens.cbe.http.framework.Server;
import be.tombaeyens.cbe.util.log.SimpleLogConfiguration;
import be.tombaeyens.cbe.util.log.SimpleLoggerFactory.Level;


/**
 * @author Tom Baeyens
 */
public class NonBlockingServerTest {
  
  static { TestLogConfiguration.initialize(); }
  
  public static Server server;
  
  @BeforeClass
  public static void startServer() {
    if (server==null) {
      server = new Server()
        .startup();
    }
  }

  @Test
  public void testOne() {
    POST("articles/9sd89sd8f")
      .bodyString("klsjdflksjdklsdjf", ContentType.TEXT_PLAIN)
      .execute()
      .returnContent()
      .asString();
  }
  
  public static Request GET(final String path) {
    return Request.Get("http://localhost:8000/"+path)
      .connectTimeout(1000)
      .socketTimeout(1000);
  }
  
  public static Request POST(final String path) {
    return Request.Post("http://localhost:8000/"+path)
        .connectTimeout(1000)
        .socketTimeout(1000);
  }
}
