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

import be.tombaeyens.cbe.http.framework.BadRequestException;
import be.tombaeyens.cbe.http.framework.Post;
import be.tombaeyens.cbe.http.framework.RequestHandler;
import be.tombaeyens.cbe.model.common.Collection;
import be.tombaeyens.cbe.model.common.Document;


/**
 * @author Tom Baeyens
 */
@Post("/documents/:collectionUrlName")
public class DocumentsPost extends RequestHandler {

  @Override
  public void handle() {
    String collectionUrlName = request.getPathParameter("collectionUrlName");
    String documentJson = request.getContentStringUtf8();
    
    Document document = tx(tx -> {
      Collection collection = getDb().getCollectionsTable()
        .getCollectionByUrlName(tx, collectionUrlName);

      BadRequestException.checkNotNull(collection, "No collection with urlName '%s'", collectionUrlName);
      
      tx.result(getDb().getDocumentsTable()
        .insertDocument(tx, new Document()
          .id(getDb().nextId())
          .collectionId(collection.getId())
          .json(documentJson)));
    });
    
    response.contentJson(document);
    response.statusCreated();
  }
}
