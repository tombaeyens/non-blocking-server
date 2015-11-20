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
import be.tombaeyens.cbe.db.DbBuilder;


/**
 * @author Tom Baeyens
 */
public class PostgreSqlBuilder extends DbBuilder {

  @Override
  public Db build() {
    return new PostgreSqlDb(this);
  }

}
