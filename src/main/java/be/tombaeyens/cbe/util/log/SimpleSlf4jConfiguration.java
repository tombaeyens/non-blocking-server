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
package be.tombaeyens.cbe.util.log;



/** enables runtime configuration of loggers and allows for 
 * easy programatic configuration instead of resource files.
 * 
 * @author Tom Baeyens
 */
public class SimpleSlf4jConfiguration {

  public static SimpleSlf4jILoggerFactory initialize() {
    // SLF4J is initialized because org.slf4j.impl.StaticLoggerBinder is on the classpath 
    
    // Bridging netty logging to SLF4J
    // InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
    
    return SimpleSlf4jILoggerFactory.INSTANCE;
  }
}
