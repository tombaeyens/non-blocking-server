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
package be.tombaeyens.cbe.db;

import be.tombaeyens.cbe.db.tables.CollectionsTable;
import be.tombaeyens.cbe.db.tables.ConfigurationsTable;
import be.tombaeyens.cbe.db.tables.DataTypesTable;
import be.tombaeyens.cbe.db.tables.DocumentsTable;


/**
 * @author Tom Baeyens
 */
public abstract class CbeDb extends Db {
  
  public CbeDb(DbBuilder dbBuilder) {
    super(dbBuilder);
    initializeDbTables();
  }

  /** to customize a collection, override, first delegate to 
   * this implementation and then overwrite the keys used 
   * in this implementation with customized collection 
   * implementations as values. */
  protected void initializeDbTables() {
    dbTables.put(ConfigurationsTable.class, new ConfigurationsTable(this));
    dbTables.put(CollectionsTable.class, new CollectionsTable(this, json));
    dbTables.put(DataTypesTable.class, new DataTypesTable(this, json));
    dbTables.put(DocumentsTable.class, new DocumentsTable(this, json));
  }

  public ConfigurationsTable getCongfigurationsTable() {
    return getDbTable(ConfigurationsTable.class);
  }

  public CollectionsTable getCollectionsTable() {
    return getDbTable(CollectionsTable.class);
  }

  public DataTypesTable getDataTypesTable() {
    return getDbTable(DataTypesTable.class);
  }
  
  public DocumentsTable getDocumentsTable() {
    return getDbTable(DocumentsTable.class);
  }
}
