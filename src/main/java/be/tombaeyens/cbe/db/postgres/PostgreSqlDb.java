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
package be.tombaeyens.cbe.db.postgres;

import be.tombaeyens.cbe.db.Db;


/**
 * @author Tom Baeyens
 */
public class PostgreSqlDb extends Db {

  public PostgreSqlDb(PostgreSqlBuilder dbBuilder) {
    super(dbBuilder);
  }

  @Override
  protected String getDriverClassName() {
    return "org.postgresql.Driver";
  }

  @Override
  protected String getConnectionUrl(String server, Integer port, String databaseName) {
    return "jdbc:postgresql://"+server+(port!=null?":"+port:"")+"/"+databaseName;
  }

  @Override
  protected void upgradeDbVersion(int dbVersion) {
  }

  @Override
  protected String getDropSqlTemplate() {
    return "DROP TABLE IF EXISTS %s CASCADE";
  }
}
