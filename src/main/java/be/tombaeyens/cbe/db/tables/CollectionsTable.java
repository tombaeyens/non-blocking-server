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
public class CollectionsTable extends DbTable {

  public CollectionsTable(Db db) {
    super(db, "collections");
  }
  
  @Override
  public String sqlCreate() {
    return "CREATE TABLE collections ( "+
           " id   "+db.typeVarcharId()+" CONSTRAINT collections_pk PRIMARY KEY, "+
           " name "+db.typeVarcharName()+" "+
           ")";
  }

  public Collection insertCollection(Tx tx, String name) {
    String id = db.nextId();
    Update update = tx.createUpdate(
      "INSERT INTO collections ( " +
      " id, " +
      " name " +
      ") " +
      "VALUES (" +
      " :id, " +
      " :name " +
      ")")
      .setString("id", id)
      .setString("name", name)
      .execute();
    
    if (update.getRowCount()==0) {
      return null;
    }
    return new Collection()
      .id(id)
      .name(name);
  }
}
