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
package be.tombaeyens.cbe.db.tables;

import be.tombaeyens.cbe.db.Db;
import be.tombaeyens.cbe.db.DbException;
import be.tombaeyens.cbe.db.DbTable;
import be.tombaeyens.cbe.db.Tx;
import be.tombaeyens.cbe.db.Update;
import be.tombaeyens.cbe.json.deprecated.DataTypeAdapter;
import be.tombaeyens.cbe.json.deprecated.FieldExclusionStrategy;
import be.tombaeyens.cbe.model.common.AbstractDataType;
import be.tombaeyens.cbe.model.common.DataType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @author Tom Baeyens
 */
public class DataTypesTable extends DbTable {

  Gson typesGson = new GsonBuilder()
    .setExclusionStrategies(new FieldExclusionStrategy()
      .exclude(AbstractDataType.class, "id")
      .exclude(AbstractDataType.class, "name"))
    // .registerTypeHierarchyAdapter(AbstractDataType.class, new DataTypeAdapter())
    .create();
  
  public DataTypesTable(Db db) {
    super(db, "datatypes");
  }
  
  public String convertToJson(DataType type) {
    return typesGson.toJson(type);
  }

  public DataType convertToDataType(String dataTypeJsonString) {
    return typesGson.fromJson(dataTypeJsonString, DataType.class);
  }

  @Override
  public String sqlCreate() {
    return "CREATE TABLE datatypes ( "+
           " id   "+db.typeVarcharId()+" CONSTRAINT datatypes_pk PRIMARY KEY, "+
           " name "+db.typeVarchar()+"," +
           " json "+db.typeJson()+""+
           ")";
  }

  public AbstractDataType insertType(Tx tx, DataType type) {
    if (type==null) {
      DbException.checkNotNull(type, "datatype is null");
    }
    if (type.getId()==null) {
      type.setId(db.nextId());
    }
    Update update = tx.createUpdate(
      "INSERT INTO datatypes ( " +
      " id, " +
      " name," +
      " json " +
      ") " +
      "VALUES (" +
      " ?, " +
      " ?," +
      " ?::jsonb " +
      ")")
      .setString(type.getId(), "id")
      .setString(type.getName(), "name")
      .setString(convertToJson(type), "json")
      .execute();
    
    if (update.getRowCount()==0) {
      return null;
    }
    return type;
  }

  public int updateType(Tx tx, DataType dataType) {
    tx.createUpdate("UPDATE datatypes " +
            "SET name = ?, " +
            "    json = ? " +
            "WHERE id = ?")
      .setString(dataType.getName(), "name")
      .setString(convertToJson(dataType), "json")
      .setString(dataType.getId(), "id");
    return 0;
  }

  public boolean hasType(Tx tx, String typeId) {
    return false;
  }
}
