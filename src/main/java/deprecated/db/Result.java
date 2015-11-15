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
package deprecated.db;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Tom Baeyens
 */
public class Result {

  ResultSet resultSet;
  
  public Result(ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  public String getString(String name) {
    try {
      return resultSet.getString(name);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Integer getInteger(String name) {
    try {
      return resultSet.getInt(name);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean next() {
    try {
      return resultSet.next();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
