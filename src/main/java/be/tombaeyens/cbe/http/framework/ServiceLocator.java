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
import be.tombaeyens.cbe.db.CbeDb;
import be.tombaeyens.json.Json;


/**
 * @author Tom Baeyens
 */
public class ServiceLocator {

  protected CbeDb db;
  protected Json json;
  protected Router<Class<? extends RequestHandler>> router;

  public CbeDb getDb() {
    return this.db;
  }
  public void setDb(CbeDb db) {
    this.db = db;
  }
  public ServiceLocator db(CbeDb db) {
    this.db = db;
    return this;
  }

  public Json getJson() {
    return this.json;
  }
  public void setJson(Json json) {
    this.json = json;
  }
  public ServiceLocator json(Json json) {
    this.json = json;
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
