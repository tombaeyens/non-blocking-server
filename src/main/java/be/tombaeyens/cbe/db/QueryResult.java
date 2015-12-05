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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;


/**
 * @author Tom Baeyens
 */
public class QueryResult {
  
  private static final Logger log = LoggerFactory.getLogger(QueryResult.class);

  Query query;
  ResultSet resultSet;
  JsonObject result;

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
  
  public void close() {
    try {
      logResult();
      resultSet.close();
    } catch (SQLException e) {
      throw new RuntimeException("Couldn't close: "+e.getMessage(), e);
    }
  }

  public boolean next() {
    try {
      logResult();
      return resultSet.next();
    } catch (SQLException e) {
      throw new RuntimeException("Couldn't get next result: "+e.getMessage(), e);
    }
  }

  public void logResult() {
    if (result!=null) {
      log.debug(result.toString());
    }
    result = new JsonObject();
  }

  public String getString(String columnLabel) {
    try {
      String value = resultSet.getString(columnLabel);
      result.addProperty(columnLabel, value);
      return value;
    } catch (SQLException e) {
      throw new RuntimeException("Couldn't get string value for column '"+columnLabel+"': "+e.getMessage(), e);
    }
  }

  public ResultSet getResultSet() {
    return resultSet;
  }
}
