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


/**
 * @author Tom Baeyens
 */
public class Query extends Operation {
  
  private static final Logger log = LoggerFactory.getLogger(Query.class);

  public Query(Tx tx, String sql) {
    super(tx, sql);
  }
  
  public QueryResult execute() {
    try {
      log.debug(tx+" " +sql);
      logParameterValues();
      ResultSet executeQuery = preparedStatement.executeQuery();
      return new QueryResult(this, executeQuery);
    } catch (SQLException e) {
      throw new RuntimeException("Update failed: "+e.getMessage(), e);
    }
  }

  @Override
  protected Logger getLog() {
    return log;
  }

  @Override
  public Query setString(String value, String parameterName) {
    return (Query) super.setString(value, parameterName);
  }
  
  
}
