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

import graphql.schema.GraphQLType;
import be.tombaeyens.cbe.db.tables.Collection;
import be.tombaeyens.cbe.http.framework.RequestHandler;


/**
 * @author Tom Baeyens
 */
public class CollectionSchemaFieldPost extends RequestHandler {
  
  public static class RequestBody {
    String name;
    GraphQLType type;
  }

  @Override
  public void handle() {
    RequestBody requestBody = request.getContent(RequestBody.class);

    Collection collection = db.tx(tx -> {
      tx.result( db.getCollectionsTable()
        .insertCollection(tx, requestBody.name) );
    });
    
    response.contentJson(collection);
    response.statusCreated();
  }
}
