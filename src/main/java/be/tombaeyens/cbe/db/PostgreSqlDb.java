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


/**
 * @author Tom Baeyens
 */
public class PostgreSqlDb extends Db {

  public PostgreSqlDb(PostgreSqlBuilder dbBuilder) {
    super(dbBuilder);
  }

  @Override
  public String getDriverClassName() {
    return "org.postgresql.Driver";
  }

  @Override
  public String getConnectionUrl(String server, Integer port, String databaseName) {
    return "jdbc:postgresql://"+server+(port!=null?":"+port:"")+"/"+databaseName;
  }

  @Override
  public String configurationsDropTable() {
    return "DROP TABLE IF EXISTS configurations CASCADE";
  }

  @Override
  public String configurationsCreateTable() {
    return "CREATE TABLE configurations ( "+
           "  id    VARCHAR(1024) CONSTRAINT configurations_pk PRIMARY KEY, "+
           "  value VARCHAR(4096) "+
           ");";
  }

  @Override
  public String configurationsQueryGetDbVersion() {
    return "SELECT value "+
           "FROM configurations "+
           "WHERE id = 'dbversion'";
  }

  @Override
  public String configurationsInsertDbVersion(String appSchemaVersion) {
    return "INSERT INTO configurations ( id, value ) "+
           "VALUES ('dbversion', '"+appSchemaVersion+"')";
  }

  @Override
  public String collectionsDropTable() {
    return "DROP TABLE IF EXISTS collections CASCADE";
  }

  @Override
  public String collectionsCreateTable() {
    return "CREATE TABLE collections ( "+
           "  id   VARCHAR(1024) CONSTRAINT collections_pk PRIMARY KEY, "+
           "  name VARCHAR(4096) "+
           ");";
  }

  @Override
  protected void upgradeDbVersion(int dbVersion) {
  }
}
