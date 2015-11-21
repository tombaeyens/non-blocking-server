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

import be.tombaeyens.cbe.db.tables.Collection;
import be.tombaeyens.cbe.db.tables.Type;
import be.tombaeyens.cbe.db.tables.Type.Base;
import be.tombaeyens.cbe.http.framework.BadRequestException;
import be.tombaeyens.cbe.http.framework.Post;
import be.tombaeyens.cbe.http.framework.RequestHandler;


/**
 * @author Tom Baeyens
 */
@Post("types/:typeId/")
public class TypePropertyPost extends RequestHandler {
  
  public static class RequestBody {
    String name;
    String typeId;
    Base base;
  }

  @Override
  public void handle() {
    RequestBody requestBody = request.getContent(RequestBody.class);
    
    request.getPathParameter("");

    Collection collection = tx(tx -> {
      String typeId = requestBody.typeId;
      if (typeId!=null && !db.getTypesTable().hasType(tx, typeId)) {
        throw new BadRequestException("typeId "+typeId+" doesn't exist");
      }
    });
    
    response.contentJson(collection);
    response.statusCreated();
  }
}
