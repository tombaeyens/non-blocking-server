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



/** Configuration properties for the database connection.
 * 
 * @author Tom Baeyens
 */
public abstract class DbBuilder {

  protected String server = "localhost";
  protected Integer port;
  protected String databaseName = "cbe";
  protected String connectionUrl;
  protected String username;
  protected String password;
  protected IdGenerator idGenerator = new IdGenerator();
  protected Json json;

  public abstract Db build();
  
  public String getServer() {
    return this.server;
  }
  /** The IP address or server domain name.
   * Simplest way to specify the server.
   * Always specify the databaseName as well 
   * when using this configuration. 
   * Alternatively specify the full JDBC connection 
   * url with {@link #connectionUrl}. */
  public void setServer(String server) {
    this.server = server;
  }
  /** Simplest way to specify the server: put in an IP address or 
   * the server domain name. */
  public DbBuilder server(String server) {
    this.server = server;
    return this;
  }
  
  public Integer getPort() {
    return this.port;
  }
  /** Optional configuration when specifying the server property. */
  public void setPort(Integer port) {
    this.port = port;
  }
  /** Optional configuration when specifying the server property. */
  public DbBuilder port(Integer port) {
    this.port = port;
    return this;
  }
  
  public String getDatabaseName() {
    return this.databaseName;
  }
  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }
  public DbBuilder databaseName(String databaseName) {
    this.databaseName = databaseName;
    return this;
  }
  
  public String getConnectionUrl() {
    return this.connectionUrl;
  }
  /** The jdbc connection url, which overrules properties 
   * server, port and databaseName. */
  public void setConnectionUrl(String connectionUrl) {
    this.connectionUrl = connectionUrl;
  }
  /** The jdbc connection url, which overrules properties 
   * server, port and databaseName. */
  public DbBuilder connectionUrl(String connectionUrl) {
    this.connectionUrl = connectionUrl;
    return this;
  }
  
  public String getUsername() {
    return this.username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public DbBuilder username(String username) {
    this.username = username;
    return this;
  }
  
  public String getPassword() {
    return this.password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public DbBuilder password(String password) {
    this.password = password;
    return this;
  }

  public IdGenerator getIdGenerator() {
    return this.idGenerator;
  }
  public void setIdGenerator(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }
  public DbBuilder idGenerator(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
    return this;
  }

  public Json getJson() {
    return this.json;
  }
  public void setJson(Json json) {
    this.json = json;
  }
  public DbBuilder json(Json json) {
    this.json = json;
    return this;
  }
}
