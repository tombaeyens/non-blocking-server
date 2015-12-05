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

import java.util.ArrayList;
import java.util.List;

import be.tombaeyens.cbe.db.Db;
import be.tombaeyens.cbe.db.DbTable;
import be.tombaeyens.cbe.db.QueryResult;
import be.tombaeyens.cbe.db.Tx;
import be.tombaeyens.cbe.db.Update;
import be.tombaeyens.cbe.model.common.Collection;


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
           " name "+db.typeVarchar()+", "+
           " urlName "+db.typeVarchar()+" "+
           ")";
  }

  public Collection insertCollection(Tx tx, Collection collection) {
    if (collection.getId()==null) {
      collection.setId(db.nextId());
    }
    if (collection.getUrlName()==null && collection.getName()!=null) {
      collection.setUrlName(collection.getName()+"s");
    }
    Update update = tx.createUpdate(
      "INSERT INTO collections ( " +
      " id, " +
      " name, " +
      " urlName " +
      ") " +
      "VALUES (" +
      " ?, " +
      " ?, " +
      " ? " +
      ")")
      .setString(collection.getId(), "id")
      .setString(collection.getName(), "name")
      .setString(collection.getUrlName(), "urlName")
      .execute();
    
    if (update.getRowCount()==0) {
      return null;
    }
    return collection;
  }

  protected Collection createCollection(QueryResult result) {
    return new Collection()
      .id(result.getString("id"))
      .name(result.getString("name"))
      .urlName(result.getString("urlName"));
  }

  public Collection getCollectionById(Tx tx, String id) {
    QueryResult result = tx.createQuery(
      "SELECT id, name, urlName " +
      "FROM collections " +
      "WHERE id = ? ")
      .setString(id, "id")
      .execute();
    
    try {
      if (!result.next()) {
        return null;
      }
      return createCollection(result);
    } finally {
      result.close();
    }
  }
  
  public Collection getCollectionByUrlName(Tx tx, String urlName) {
    QueryResult result = tx.createQuery(
      "SELECT id, name, urlName " +
      "FROM collections " +
      "WHERE urlName = ?")
      .setString(urlName, "urlName")
      .execute();
    
    try {
      if (!result.next()) {
        return null;
      }
      return createCollection(result);
    } finally {
      result.close();
    }
  }
  

  public List<Collection> getCollections(Tx tx) {
    List<Collection> collections = new ArrayList<>();
    
    QueryResult result = tx.createQuery(
      "SELECT id, name, urlName " +
      "FROM collections")
      .execute();

    try {
      while (result.next()) {
        collections.add(createCollection(result));
      }
      return collections;
    } finally {
      result.close();
    }
  }

  public int deleteCollectionById(Tx tx, String id) {
    return tx.createUpdate(
      "DELETE FROM collections " +
      "WHERE id = ?")
      .setString(id, "id")
      .execute()
      .getRowCount();
  }
}
