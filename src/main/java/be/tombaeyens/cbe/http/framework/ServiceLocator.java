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

import io.netty.handler.codec.http.router.Router;
import be.tombaeyens.cbe.db.Db;

import com.google.gson.Gson;


/**
 * @author Tom Baeyens
 */
public class ServiceLocator {

  protected Db db;
  protected Gson gson;
  protected Router<Class<? extends RequestHandler>> router;

  public Db getDb() {
    return this.db;
  }
  public void setDb(Db db) {
    this.db = db;
  }
  public ServiceLocator db(Db db) {
    this.db = db;
    return this;
  }

  public Gson getGson() {
    return this.gson;
  }
  public void setGson(Gson gson) {
    this.gson = gson;
  }
  public ServiceLocator gson(Gson gson) {
    this.gson = gson;
    return this;
  }

  public Router<Class<? extends RequestHandler>> getRouter() {
    return this.router;
  }
  public void setRouter(Router<Class<? extends RequestHandler>> router) {
    this.router = router;
  }
  public ServiceLocator router(Router<Class<? extends RequestHandler>> router) {
    this.router = router;
    return this;
  }
}
