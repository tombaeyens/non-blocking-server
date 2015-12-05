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
import java.sql.Types;

import org.slf4j.Logger;

import com.google.gson.JsonObject;


/**
 * @author Tom Baeyens
 */
public abstract class Operation {
  
  protected Tx tx;
  protected String sql;
  protected JsonObject parameterValues = null;
  protected int parameterIndex = 1;
//  protected Map<String, Integer> parameterIndexes;
  protected PreparedStatement preparedStatement;
  
  public Operation(Tx tx, String sql) {
    this.tx = tx;
    this.sql = sql;
//    String parsedSql = parseParameters(sql);
    this.preparedStatement = createPreparedStatement(tx, sql);
  }

  protected abstract Logger getLog();

//  protected String parseParameters(String sql) {
//    int colonIndex = sql.indexOf(':');
//    if (colonIndex==-1) {
//      return sql;
//    }
//    this.parameterIndexes = new HashMap<>();
//    StringBuilder parsedSql = new StringBuilder();
//    Pattern pattern = Pattern.compile("(:[a-zA-Z0-9]+|\\[.*\\])");
//    Matcher matcher = pattern.matcher(sql);
//    int start = 0;
//    int index = 1;
//    while (matcher.find()) {
//      String group = matcher.group();
//      String sqlPiece = sql.substring(start, matcher.start());
//      parsedSql.append(sqlPiece);
//      if (isQuote(group)) {
//        parsedSql.append(group.substring(1, group.length()-1));
//        
//      } else {
//        String name = group.substring(1);
//        parameterIndexes.put(name, index);
//        parsedSql.append('?');
//        index++;
//      }
//
//      start = matcher.end();
//    }
//    if (start<sql.length()) {
//      parsedSql.append(sql.substring(start, sql.length()));
//    }
//    return parsedSql.toString();
//  }
//  
//  private boolean isQuote(String group) {
//    return group.startsWith("[");
//  }

  public PreparedStatement createPreparedStatement(Tx tx, String sql) {
    try {
      // getLog().debug(tx.toString()+"Creating prepared statement: "+sql);
      return tx.getConnection().prepareStatement(sql);
    } catch (SQLException e) {
      throw new RuntimeException(getClass().getSimpleName()+" creation failed: "+e.getMessage(), e);
    }
  }

  public Operation setString(String value) {
    return setString(value, null);
  }

  public Operation setString(String value, String parameterName) {
    int index = parameterIndex++;
    try {
      if (parameterName==null) {
        parameterName = Integer.toString(index);
      }
      addParameterValue(parameterName, "'"+value+"'");
      preparedStatement.setString(index, value);
      return this;
    } catch (SQLException e) {
      throw new RuntimeException("set parameter failed for parameter "+parameterName+": "+e.getMessage(), e);
    }
  }
  
  public Operation setOther(Object value, String parameterName) {
    int index = parameterIndex++;
    try {
      if (parameterName==null) {
        parameterName = Integer.toString(index);
      }
      addParameterValue(parameterName, value!=null ? value.toString() : null);
      preparedStatement.setObject(index, value, Types.OTHER);
      return this;
    } catch (SQLException e) {
      throw new RuntimeException("setOther("+index+",\""+value+"\") failed for parameter "+parameterName+": "+e.getMessage(), e);
    }
  }
  
  protected void addParameterValue(String parameterName, String value) {
    if (parameterValues==null) {
      parameterValues = new JsonObject();
    }
    parameterValues.addProperty(parameterName, value);
  }

  protected void logParameterValues() {
    if (parameterValues!=null) {
      getLog().debug(tx.toString()+" "+parameterValues);
    }
  }

//  protected Integer getIndex(String parameterName) {
//    Integer index = parameterIndexes.get(parameterName);
//    if (index==null) {
//      throw new RuntimeException("Parameter '"+parameterName+"' not in statement '"+sql+"'");
//    }
//    return index;
//  }
}
