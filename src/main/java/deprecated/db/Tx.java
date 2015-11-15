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

import java.sql.Connection;
import java.sql.SQLException;

import be.tombaeyens.cbe.db.Db;


/**
 * @author Tom Baeyens
 */
public class Tx {

  private static InternalLogger log = InternalLoggerFactory.getInstance(Tx.class);

  Connection connection;
  
  public Tx(Connection connection) {
    try {
      this.connection = connection;
      this.connection.setAutoCommit(false);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  
  public void commit() {
    try {
      connection.commit();
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void rollback() {
    try {
      connection.rollback();
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Select select() {
    return new Select(this);
  }
  
  public Connection getConnection() {
    return connection;
  }

  public int update(String sql) {
    try {
      log.debug("update:\n"+sql);
      return connection
        .createStatement()
        .executeUpdate(sql);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
