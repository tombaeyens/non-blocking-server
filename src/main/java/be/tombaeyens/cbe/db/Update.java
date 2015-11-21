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

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Tom Baeyens
 */
public class Update extends Operation {
  
  private static final Logger log = LoggerFactory.getLogger(Update.class);

  Integer rowCount;
  
  public Update(Tx tx, String sql) {
    super(tx, sql);
  }

  public Update execute() {
    try {
      log.debug(tx+" " +sql);
      rowCount = preparedStatement.executeUpdate();
      return this;
    } catch (SQLException e) {
      throw new RuntimeException("Update failed: "+e.getMessage(), e);
    }
  }

  @Override
  public Update setString(String parameterName, String value) {
    return (Update) super.setString(parameterName, value);
  }

  public Integer getRowCount() {
    return rowCount;
  }

  public String getGeneratedString(int index) {
    try {
      return preparedStatement.getGeneratedKeys().getString(index);
    } catch (SQLException e) {
      throw new RuntimeException("Getting generated key "+index+" failed: "+e.getMessage(), e);
    }
  }

  @Override
  protected Logger getLog() {
    return log;
  }
}
