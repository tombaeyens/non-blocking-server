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

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Tom Baeyens
 */
public class QueryResult {

  Query query;
  ResultSet resultSet;

  public QueryResult(Query query, ResultSet resultSet) {
    this.query = query;
    this.resultSet = resultSet;
  }

  public String getFirstAsString() {
    try {
      if (!resultSet.next()) {
        return null;
      }
      return resultSet.getString(1);
    } catch (SQLException e) {
      throw new RuntimeException("Couldn't get first string: "+e.getMessage(), e);
    }
  }

  public boolean next() {
    try {
      return resultSet.next();
    } catch (SQLException e) {
      throw new RuntimeException("Couldn't get next result: "+e.getMessage(), e);
    }
  }

  public String getString(String columnLabel) {
    try {
      return resultSet.getString(columnLabel);
    } catch (SQLException e) {
      throw new RuntimeException("Couldn't get string value for column '"+columnLabel+"': "+e.getMessage(), e);
    }
  }

}
