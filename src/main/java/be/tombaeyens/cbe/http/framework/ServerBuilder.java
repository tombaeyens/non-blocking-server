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

import be.tombaeyens.cbe.db.DbBuilder;
import be.tombaeyens.cbe.db.postgres.PostgreSqlBuilder;
import be.tombaeyens.cbe.http.router.CbeRouter;

import com.google.gson.GsonBuilder;


/**
 * @author Tom Baeyens
 */
public class ServerBuilder {

  protected DbBuilder dbBuilder = new PostgreSqlBuilder()
          .server("localhost")
          .databaseName("cbe")
          .username("test")
          .password("test");
  protected GsonBuilder gsonBuilder = new GsonBuilder();

  public ServiceLocator buildServiceLocator() {
    return new ServiceLocator()
      .db(dbBuilder.buildDb())
      .gson(gsonBuilder.create())
      .router(new CbeRouter());
  }
  
  public DbBuilder getDbBuilder() {
    return this.dbBuilder;
  }
  public void setDbBuilder(DbBuilder dbBuilder) {
    this.dbBuilder = dbBuilder;
  }
  public ServerBuilder dbBuilder(DbBuilder dbBuilder) {
    this.dbBuilder = dbBuilder;
    return this;
  }

  public GsonBuilder getGsonBuilder() {
    return this.gsonBuilder;
  }
  public void setGsonBuilder(GsonBuilder gsonBuilder) {
    this.gsonBuilder = gsonBuilder;
  }
  public ServerBuilder gsonBuilder(GsonBuilder gsonBuilder) {
    this.gsonBuilder = gsonBuilder;
    return this;
  }
}
