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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.tombaeyens.cbe.db.Db;
import be.tombaeyens.cbe.db.DbTable;
import be.tombaeyens.cbe.db.QueryResult;
import be.tombaeyens.cbe.db.Tx;
import be.tombaeyens.cbe.db.Update;
import be.tombaeyens.cbe.model.common.Document;
import be.tombaeyens.json.Json;


/**
 * @author Tom Baeyens
 */
public class DocumentsTable extends DbTable<Document> {
  
  private static final String ALL_COLUMNS = "id, collectionId, json";

  public DocumentsTable(Db db, Json json) {
    super(db, "documents", json, Document.class);
  }
  
  @Override
  public String sqlCreate() {
    return "CREATE TABLE documents ( "+
           " id   "+db.typeVarcharId()+" CONSTRAINT documents_pk PRIMARY KEY, "+
           " collectionId "+db.typeVarcharId()+" REFERENCES collections (id), "+
           " json "+db.typeJson()+" "+
           ")";
  }

  public Document insertDocument(Tx tx, Document document) {
    if (document.getCollectionId()==null) {
      throw new RuntimeException("Document must have a collection");
    }
    if (document.getId()==null) {
      document.setId(db.nextId());
    }
    
    Map<String,Object> json = new HashMap<>();
    json.put("k", "v");
    
    Update update = tx.createUpdate(
      "INSERT INTO documents ( " +
      " " +ALL_COLUMNS+" "+
      ") " +
      "VALUES (" +
      " ?, " +
      " ?, " +
      " ?::jsonb" +
      ")")
      .setString(document.getId(), "id")
      .setString(document.getCollectionId(), "collectionId")
      .setString(document.getJson(), "json")
      .execute();
    
    if (update.getRowCount()==0) {
      return null;
    }
    return document;
  }

  protected Document createDocument(QueryResult result) {
    return new Document()
      .id(result.getString("id"))
      .collectionId(result.getString("collectionId"))
      .json(result.getString("json"));
  }

  public Document getDocumentById(Tx tx, String id, String collectionId) {
    QueryResult result = tx.createQuery(
      "SELECT "+ALL_COLUMNS+" " +
      "FROM documents " +
      "WHERE id = ? " +
      "  AND collectionId = ?")
      .setString(id, "id")
      .setString(collectionId, "collectionId")
      .execute();
    
    try {
      if (!result.next()) {
        return null;
      }
      return createDocument(result);
    } finally {
      result.close();
    }
  }
  
  public Document getDocumentsByUrlName(Tx tx, String urlName) {
    QueryResult result = tx.createQuery(
      "SELECT "+ALL_COLUMNS+" "+
      "FROM collections " +
      "WHERE urlName = ?")
      .setString(urlName, "urlName")
      .execute();
    
    try {
      if (!result.next()) {
        return null;
      }
      return createDocument(result);
    } finally {
      result.close();
    }
  }
  

  public List<Document> getDocuments(Tx tx, String urlName) {
    List<Document> documents = new ArrayList<>();
    
    QueryResult result = tx.createQuery(
      "SELECT "+ALL_COLUMNS+" " +
      "FROM documents " +
      "WHERE urlName = ?")
      .setString(urlName, "urlName")
      .execute();
    
    try {
      while (result.next()) {
        documents.add(createDocument(result));
      }
      return documents;
    } finally {
      result.close();
    }
  }

  public int deleteDocumentById(Tx tx, String id) {
    return tx.createUpdate(
      "DELETE FROM documents " +
      "WHERE id = ?")
      .setString(id, "id")
      .execute()
      .getRowCount();
  }
}
