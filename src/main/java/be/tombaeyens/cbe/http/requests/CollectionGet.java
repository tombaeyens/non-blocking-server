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
package be.tombaeyens.cbe.http.requests;

import be.tombaeyens.cbe.http.framework.Get;
import be.tombaeyens.cbe.http.framework.RequestHandler;
import be.tombaeyens.cbe.model.common.Collection;


/**
 * @author Tom Baeyens
 */
@Get("/collections/:id")
public class CollectionGet extends RequestHandler {

  @Override
  public void handle() {
    String id = request.getPathParameter("id");
    Collection collection = tx(tx -> {
      tx.result(getDb().getCollectionsTable()
        .getCollectionById(tx, id));
    });
    
    response.contentJson(collection);
    response.statusOk();
  }
}
