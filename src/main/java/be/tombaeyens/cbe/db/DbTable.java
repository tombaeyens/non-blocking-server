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

import be.tombaeyens.json.Json;


/**
 * @author Tom Baeyens
 */
public abstract class DbTable<T> {
  
  protected Db db;
  protected String name;
  protected Json json;
  protected Class<T> beanClass;
  
  /** constructor for tables without json mapping */
  public DbTable(Db db, String name) {
    this(db, name, null, null);
  }

  /** constructor for tables with json mapping */
  public DbTable(Db db, String name, Json json, Class<T> beanClass) {
    this.db = db;
    this.name = name;
    this.json = json;
    this.beanClass = beanClass;
  }

  public String convertToJson(T bean) {
    return json.write(bean);
  }

  public T convertToBean(String jsonString) {
    return (T) json.read(jsonString, beanClass);
  }

  public String getName() {
    return this.name;
  }
  
  public Db getDb() {
    return db;
  }

  public String sqlDrop() {
    return String.format(db.getDropSqlTemplate(), name);
  }

  public abstract String sqlCreate();
}
