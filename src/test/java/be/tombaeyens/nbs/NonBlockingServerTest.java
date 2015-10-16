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

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.junit.BeforeClass;
import org.junit.Test;

import be.tombaeyens.nbs.SimpleLoggerFactory.Level;


/**
 * @author Tom Baeyens
 */
public class NonBlockingServerTest {
  
  static {
    SimpleLogConfiguration.initialize()
      .configure("io.netty", Level.INFO)
      ;
  }
  
  private static InternalLogger log = InternalLoggerFactory.getInstance(ServerHandler.class);
  
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
    try {
      
      String urlParameters  = "param1=a&param2=b&param3=c";
      byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
      int    postDataLength = postData.length;
      String request        = "http://localhost:7777/articles/9sd89sd8f";
      URL    url            = new URL( request );
      HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
      conn.setDoOutput( true );
      conn.setInstanceFollowRedirects( false );
      conn.setRequestMethod( "POST" );
      conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
      conn.setRequestProperty( "charset", "utf-8");
      conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
      conn.setUseCaches( false );
      try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
         wr.write( postData );
      }
      
//      String url = "http://localhost:7777/articles/9sd89sd8f";
//      URL obj = new URL(url);
//      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//      con.setRequestMethod("POST");
//      // con.setRequestProperty("User-Agent", "me");
//      con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//
//      // String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
//      
//      // Send post request
//      con.setDoOutput(true);
//      DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//      // wr.writeBytes(urlParameters);
//      wr.flush();
//      wr.close();
//
//      int responseCode = con.getResponseCode();
//      System.out.println("\nSending 'POST' request to URL : " + url);
//      // System.out.println("Post parameters : " + urlParameters);
//      System.out.println("Response Code : " + responseCode);
//
//      BufferedReader in = new BufferedReader(
//              new InputStreamReader(con.getInputStream()));
//      String inputLine;
//      StringBuffer response = new StringBuffer();
//
//      while ((inputLine = in.readLine()) != null) {
//        response.append(inputLine);
//      }
//      in.close();
//      
//      //print result
//      System.out.println(response.toString());
      
      
      
//      URL url = new URL("http://localhost:8000/articles/9s8d7fs");
//      HttpURLConnection con = (HttpURLConnection) url.openConnection();
//      con.setDoOutput(true);
//      con.setRequestMethod("POST");
//      OutputStream outputStream = con.getOutputStream();
//      byte[] bytes = "bytesbytesbytesbytesbytesbytesbytes".getBytes(Charset.forName("UTF-8"));
//      for (int i = 0; i < 1000; i++) {
//        outputStream.write(bytes);
//        outputStream.flush();
//      }
//      outputStream.close();
//      con.connect();
//
//      log.debug("Executing request : " + url);
//      int responseCode = con.getResponseCode();
//      log.debug("Response Code : " + responseCode);
//      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//      String inputLine;
//      StringBuffer response = new StringBuffer();
//      while ((inputLine = in.readLine()) != null) {
//        response.append(inputLine);
//      }
//      in.close();
//      log.debug("Response : " + response.toString());
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
