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
public class DbBuilder {

  protected String connectionString;
  protected String username;
  protected String password;

  public String getConnectionString() {
    return this.connectionString;
  }
  public void setConnectionString(String connectionString) {
    this.connectionString = connectionString;
  }
  public DbBuilder connectionString(String connectionString) {
    this.connectionString = connectionString;
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
  
  public Db build() {
    return new Db(this);
  }
}
