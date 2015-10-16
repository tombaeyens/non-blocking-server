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

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import be.tombaeyens.nbs.SimpleLoggerFactory.Level;


/**
 * @author Tom Baeyens
 */
public class ServerMain {

  static {
    SimpleLogConfiguration.initialize()
      //.configure("io.netty", Level.INFO)
      ;
  }
  
  private static InternalLogger log = InternalLoggerFactory.getInstance(ServerHandler.class);

  public static void main(String[] args) throws Exception {
    SimpleLogConfiguration.initialize()
      .configure("io.netty", Level.INFO);

    Server server = new Server();
    server.startup();
    
    try {
      URL url = new URL("http://localhost:8000/articles/9s8d7fs");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setDoOutput(true);
      con.setRequestMethod("POST");
      OutputStream outputStream = con.getOutputStream();
      byte[] bytes = "bytesbytesbytesbytesbytesbytesbytes".getBytes(Charset.forName("UTF-8"));
      for (int i = 0; i < 1000; i++) {
        outputStream.write(bytes);
        outputStream.flush();
      }
      outputStream.close();
      con.connect();

      log.debug("Executing request : " + url);
      int responseCode = con.getResponseCode();
      log.debug("Response Code : " + responseCode);
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      log.debug("Response : " + response.toString());
      
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
