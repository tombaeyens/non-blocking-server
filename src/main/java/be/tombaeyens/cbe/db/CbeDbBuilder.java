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
public abstract class CbeDbBuilder extends DbBuilder {

  @Override
  public abstract CbeDb build();

  @Override
  public CbeDbBuilder server(String server) {
    return (CbeDbBuilder) super.server(server);
  }

  @Override
  public CbeDbBuilder port(Integer port) {
    return (CbeDbBuilder) super.port(port);
  }

  @Override
  public CbeDbBuilder databaseName(String databaseName) {
    return (CbeDbBuilder) super.databaseName(databaseName);
  }

  @Override
  public CbeDbBuilder connectionUrl(String connectionUrl) {
    return (CbeDbBuilder) super.connectionUrl(connectionUrl);
  }

  @Override
  public CbeDbBuilder username(String username) {
    return (CbeDbBuilder) super.username(username);
  }

  @Override
  public CbeDbBuilder password(String password) {
    return (CbeDbBuilder) super.password(password);
  }

  @Override
  public CbeDbBuilder idGenerator(IdGenerator idGenerator) {
    return (CbeDbBuilder) super.idGenerator(idGenerator);
  }

  @Override
  public CbeDbBuilder json(Json json) {
    return (CbeDbBuilder) super.json(json);
  }
}
