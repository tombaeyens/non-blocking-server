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
import be.tombaeyens.cbe.db.DbTable;
import be.tombaeyens.cbe.db.Tx;
import be.tombaeyens.cbe.db.Update;


/**
 * @author Tom Baeyens
 */
public class TypesTable extends DbTable {

  public TypesTable(Db db) {
    super(db, "types");
  }
  
  @Override
  public String sqlCreate() {
    return "CREATE TABLE types ( "+
           " id   "+db.typeVarcharId()+" CONSTRAINT types_pk PRIMARY KEY, "+
           " name "+db.typeVarchar()+"," +
           " base "+db.typeVarchar()+""+
           ")";
  }

  public Type insertType(Tx tx, String name, Type.Base base) {
    String id = db.nextId();
    Update update = tx.createUpdate(
      "INSERT INTO types ( " +
      " id, " +
      " name," +
      " base " +
      ") " +
      "VALUES (" +
      " :id, " +
      " :name," +
      " :base " +
      ")")
      .setString("id", id)
      .setString("name", name)
      .setString("base", base!=null ? base.toString() : null)
      .execute();
    
    if (update.getRowCount()==0) {
      return null;
    }
    return new Type()
      .id(id)
      .name(name)
      .base(base);
  }

  public boolean hasType(Tx tx, String typeId) {
    return false;
  }
}
