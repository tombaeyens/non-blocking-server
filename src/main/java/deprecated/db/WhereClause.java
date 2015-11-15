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

import deprecated.db.Select.Parameters;


/**
 * @author Tom Baeyens
 */
public abstract class WhereClause {
  
  public abstract void appendSql(StringBuilder sql);

  public abstract void appendParameters(Parameters parameters);

  public static WhereClause and(WhereClause... andClauses) {
    if (andClauses==null || andClauses.length==0) {
      return null;
    }
    if (andClauses.length==1) {
      return andClauses[0];
    }
    return new And(andClauses);
  }

  public static WhereClause or(WhereClause... orClauses) {
    if (orClauses==null || orClauses.length==0) {
      return null;
    }
    if (orClauses.length==1) {
      return orClauses[0];
    }
    return new Or(orClauses);
  }

  protected static class And extends Operator {
    public And(WhereClause[] andClauses) {
      super("AND", andClauses);
    }
  }

  protected static class Or extends Operator {
    public Or(WhereClause[] andClauses) {
      super("OR", andClauses);
    }
  }

  protected static class Operator extends WhereClause {
    String operatorName;
    WhereClause[] andClauses;
    public Operator(String operatorName, WhereClause[] andClauses) {
      this.operatorName = operatorName;
      this.andClauses = andClauses;
    }
    @Override
    public void appendSql(StringBuilder sql) {
      andClauses[0].appendSql(sql);
      for (int i=0; i<andClauses.length; i++) {
        sql.append("\n  ");
        sql.append(operatorName);
        sql.append(" ");
        andClauses[i].appendSql(sql);
      }
    }
    @Override
    public void appendParameters(Parameters parameters) {
      for (WhereClause andClause: andClauses) {
        andClause.appendParameters(parameters);
      }
    }
  }

  public static WhereClause equalVarchar(String columnName, String value) {
    return new EqualVarchar(columnName, value);
  }
  protected static class EqualVarchar extends WhereClause {
    String columnName;
    String value;
    public EqualVarchar(String columnName, String value) {
      this.columnName = columnName;
      this.value = value;
    }
    @Override
    public void appendSql(StringBuilder sql) {
      sql.append(columnName);
      sql.append(" = ?");
    }
    @Override
    public void appendParameters(Parameters parameters) {
      parameters.setString(value);
    }
  }
}
