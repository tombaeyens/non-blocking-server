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

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.tombaeyens.cbe.db.Db;


/**
 * @author Tom Baeyens
 */
public class Select {
  
  private static InternalLogger log = InternalLoggerFactory.getInstance(Select.class);

  Tx tx;
  StringBuilder sql = null;
  boolean hasFields = false;
  WhereClause whereClause;
  
  public Select(Tx tx) {
    this.tx = tx;
    this.sql = new StringBuilder();
    this.sql.append("SELECT ");
  }

  public Select field(String field) {
    field(field, null);
    return this;
  }

  public Select field(String field, String as) {
    if (hasFields) {
      sql.append(", \n  ");
    } else {
      hasFields = true;
    }
    sql.append(field);
    sql.append(" ");
    if (as!=null) {
      sql.append("AS ");
      sql.append(as);
      sql.append(" ");
    }
    return this;
  }
  
  public Select from(String table) {
    sql.append("\nFROM ");
    sql.append(table);
    return this;
  }
  
  public Select where(WhereClause whereClause) {
    this.whereClause = whereClause;
    return this;
  }

  public Result execute() {
    try {
      if (whereClause!=null) {
        sql.append("\nWHERE ");
        whereClause.appendSql(sql);
      }
      sql.append(";");
      String sqlString = sql.toString();
      log.debug("select:\n"+sqlString);
      PreparedStatement statement = tx
              .getConnection()
              .prepareStatement(sqlString);
      if (whereClause!=null) {
        Parameters parameters = new Parameters(statement);
        whereClause.appendParameters(parameters);
      }
      ResultSet resultSet = statement.executeQuery();
      return new Result(resultSet);
    } catch (SQLException e) {
      throw new RuntimeException("DB exception: "+e.getMessage(), e);
    }
  }
  
  protected static class Parameters {
    int parameterIndex = 1;
    PreparedStatement statement;
    public Parameters(PreparedStatement statement) {
      super();
      this.statement = statement;
    }

    public void setString(String value) {
      try {
        log.debug("  parameter %s = "+value+" (string)", parameterIndex);
        statement.setString(parameterIndex++, value);
      } catch (SQLException e) {
        throw new RuntimeException("DB exception: "+e.getMessage(), e);
      }
    }
  }
}
