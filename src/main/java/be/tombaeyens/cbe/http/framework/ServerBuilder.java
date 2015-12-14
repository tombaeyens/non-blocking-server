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
package be.tombaeyens.cbe.http.framework;

import be.tombaeyens.cbe.db.CbeDbBuilder;
import be.tombaeyens.cbe.db.postgres.PostgreSqlBuilder;
import be.tombaeyens.cbe.http.router.CbeRouter;
import be.tombaeyens.cbe.json.CbeJsonBuilder;
import be.tombaeyens.json.Json;


/**
 * @author Tom Baeyens
 */
public class ServerBuilder {

  protected CbeDbBuilder dbBuilder = new PostgreSqlBuilder()
          .server("localhost")
          .databaseName("cbe")
          .username("test")
          .password("test");
  protected CbeJsonBuilder jsonBuilder = new CbeJsonBuilder();
  
  public ServiceLocator buildServiceLocator() {
    Json json = jsonBuilder.build();
    dbBuilder.json(json);
    return new ServiceLocator()
      .json(json)
      .db(dbBuilder.build())
      .router(new CbeRouter());
  }
  
  public CbeDbBuilder getDbBuilder() {
    return this.dbBuilder;
  }
  public void setDbBuilder(CbeDbBuilder dbBuilder) {
    this.dbBuilder = dbBuilder;
  }
  public ServerBuilder dbBuilder(CbeDbBuilder dbBuilder) {
    this.dbBuilder = dbBuilder;
    return this;
  }

  public CbeJsonBuilder getJsonBuilder() {
    return this.jsonBuilder;
  }
  public void setJsonBuilder(CbeJsonBuilder jsonBuilder) {
    this.jsonBuilder = jsonBuilder;
  }
  public ServerBuilder jsonBuilder(CbeJsonBuilder jsonBuilder) {
    this.jsonBuilder = jsonBuilder;
    return this;
  }
}
