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
import be.tombaeyens.cbe.http.framework.Put;
import be.tombaeyens.cbe.http.framework.RequestHandler;
import be.tombaeyens.cbe.model.common.DataType;


/**
 * @author Tom Baeyens
 */
@Put("types/:typeId")
public class DataTypePut extends RequestHandler {
  
  public static class RequestBody {
    String name;
    String type;
    String typeId;
  }

  @Override
  public void handle() {
    DataType dataType = json.read(request.getContentStringUtf8(), DataType.class);
    BadRequestException.checkNotNull(dataType, "Content must be a dataType");
    
    String typeId = request.getPathParameter("typeId");
    BadRequestException.checkNotNull(typeId, "Path parameter typeId is null");
    dataType.setId(typeId);

    tx(tx -> {
      int rowCount = getDb().getDataTypesTable().updateType(tx, dataType);
      BadRequestException.checkTrue(rowCount>0, "typeId %s doesn't exist", dataType.getId());
    });
    
    response.contentJson(dataType);
    response.statusOk();
  }
}
