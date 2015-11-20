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

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Tom Baeyens
 */
public class Tx {

  private static final Logger log = LoggerFactory.getLogger(Tx.class);
  
  private static long nextTxId = 1;
  
  protected long id = nextTxId++;
  protected Db db;
  protected Connection connection;
  protected boolean isRollbackOnly = false;
  protected Object returnValue;
  protected Exception rollbackReason;

  public Tx(Db db, Connection connection) {
    this.db = db;
    this.connection = connection;
    log.debug("Starting "+this);
  }
  
  public String toString() {
    return "Tx["+id+"]";
  }

  public Db getDb() {
    return this.db;
  }

  public Connection getConnection() {
    return this.connection;
  }

  public Object getReturnValue() {
    return this.returnValue;
  }
  public void setReturnValue(Object returnValue) {
    this.returnValue = returnValue;
  }

  public void setRollbackOnly() {
    setRollbackOnly(null);
  }
  
  public void setRollbackOnly(Exception rollbackReason) {
    this.isRollbackOnly = true;
    this.rollbackReason = rollbackReason;
  }

  public boolean isRollbackOnly() {
    return isRollbackOnly;
  }

  public Update createUpdate(String sql) {
    return new Update(this, sql);
  }

  public Query createQuery(String sql) {
    return new Query(this, sql);
  }

  protected void end() {
    if (isRollbackOnly) {
      try {
        log.error("Rolling back "+this, rollbackReason);
        connection.rollback();
      } catch (SQLException e) {
        log.error("Tx rollback failed: "+e.getMessage(), e);
      }
    } else {
      try {
        log.error("Committing "+this);
        connection.commit();
      } catch (SQLException e) {
        log.error("Tx commit failed: "+e.getMessage(), e);
      }
    }
  }
}
