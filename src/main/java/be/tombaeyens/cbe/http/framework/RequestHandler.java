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

import be.tombaeyens.cbe.db.CbeDb;
import be.tombaeyens.cbe.db.TxLogic;
import be.tombaeyens.json.Json;


/**
 * @author Tom Baeyens
 */
public abstract class RequestHandler {

  protected Request request;
  protected Response response;
  protected ServiceLocator serviceLocator;
  protected Json json;

  public abstract void handle();

  public <T> T tx(TxLogic txLogic) {
    return getDb().tx(txLogic);
  }
  
  public CbeDb getDb() {
    return serviceLocator.getDb();
  }
}
