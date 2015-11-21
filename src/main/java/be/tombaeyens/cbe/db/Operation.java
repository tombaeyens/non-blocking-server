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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;


/**
 * @author Tom Baeyens
 */
public abstract class Operation {
  
  protected Tx tx;
  protected String sql;
  protected Map<String, Integer> parameterIndexes;
  protected PreparedStatement preparedStatement;
  
  public Operation(Tx tx, String sql) {
    this.tx = tx;
    this.sql = sql;
    String parsedSql = parseParameters(sql);
    this.preparedStatement = createPreparedStatement(tx, parsedSql);
  }

  protected abstract Logger getLog();

  protected String parseParameters(String sql) {
    int colonIndex = sql.indexOf(':');
    if (colonIndex==-1) {
      return sql;
    }
    this.parameterIndexes = new HashMap<>();
    StringBuilder parsedSql = new StringBuilder();
    Pattern pattern = Pattern.compile(":[a-zA-Z0-9]+");
    Matcher matcher = pattern.matcher(sql);
    int start = 0;
    int index = 1;
    while (matcher.find()) {
      String name = matcher.group().substring(1);
      parameterIndexes.put(name, index);
      index++;
      String sqlPiece = sql.substring(start, matcher.start());
      parsedSql.append(sqlPiece);
      parsedSql.append('?');
      start = matcher.end();
    }
    if (start<sql.length()) {
      parsedSql.append(sql.substring(start, sql.length()));
    }
    return parsedSql.toString();
  }

  public PreparedStatement createPreparedStatement(Tx tx, String sql) {
    try {
      // getLog().debug(tx.toString()+"Creating prepared statement: "+sql);
      return tx.getConnection().prepareStatement(sql);
    } catch (SQLException e) {
      throw new RuntimeException(getClass().getSimpleName()+" creation failed: "+e.getMessage(), e);
    }
  }
  
  public Operation setString(String parameterName, String value) {
    Integer index = null;
    try {
      index = getIndex(parameterName);
      sql = sql.replace(":"+parameterName, "'"+value+"'");
      preparedStatement.setString(index, value);
      return this;
    } catch (SQLException e) {
      throw new RuntimeException("setString("+index+",\""+value+"\") failed for parameter "+parameterName+": "+e.getMessage(), e);
    }
  }

  protected Integer getIndex(String parameterName) {
    Integer index = parameterIndexes.get(parameterName);
    if (index==null) {
      throw new RuntimeException("Parameter '"+parameterName+"' not in statement '"+sql+"'");
    }
    return index;
  }
}
