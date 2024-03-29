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

import be.tombaeyens.cbe.http.framework.Post;
import be.tombaeyens.cbe.http.framework.RequestHandler;
import be.tombaeyens.cbe.model.common.Collection;
import be.tombaeyens.cbe.model.common.DataType;


/**
 * @author Tom Baeyens
 */
@Post("/collections")
public class CollectionsPost extends RequestHandler {

  public static class RequestBody {
    String name;
    String urlName;
  }

  @Override
  public void handle() {
    RequestBody requestBody = request.getContent(RequestBody.class);
    
    Collection collection = tx(tx -> {
      String typeId = getDb().nextId();
      DataType dataType = new DataType()
        .id(typeId)
        .name(requestBody.name);
      
      getDb().getDataTypesTable()
        .insertType(tx, dataType);
       
      tx.result(getDb().getCollectionsTable()
        .insertCollection(tx, new Collection()
          .name(requestBody.name)
          .typeId(typeId)
          .urlName(requestBody.urlName)));
    });
    
    response.contentJson(collection);
    response.statusCreated();
  }
}
