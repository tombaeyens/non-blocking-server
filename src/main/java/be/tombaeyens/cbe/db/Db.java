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
import java.util.HashMap;
import java.util.Map;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.skife.jdbi.v2.exceptions.CallbackFailedException;
import org.skife.jdbi.v2.logging.SLF4JLog;
import org.skife.jdbi.v2.logging.SLF4JLog.Level;
import org.skife.jdbi.v2.util.StringMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * @author Tom Baeyens
 */
public abstract class Db {
  
  private static final Logger log = LoggerFactory.getLogger(Db.class);
  
  DBI dbi;
  Map<String,DbTable> tables = new HashMap<>();;
  
  public Db(DbBuilder dbBuilder) {
    try {
      ComboPooledDataSource ds = new ComboPooledDataSource();
      ds.setDriverClass(getDriverClassName()); //loads the jdbc driver
      
      String connectionUrl = dbBuilder.getConnectionUrl();
      if (connectionUrl==null) {
        String server = dbBuilder.getServer();
        Integer port = dbBuilder.getPort();
        String databaseName = dbBuilder.getDatabaseName();
        connectionUrl = getConnectionUrl(server, port, databaseName);
      }
      ds.setJdbcUrl(connectionUrl);
      
      ds.setUser(dbBuilder.getUsername());                                  
      ds.setPassword(dbBuilder.getPassword());

//    // the settings below are optional -- c3p0 can work with defaults
//    ds.setMinPoolSize(5);                                     
//    ds.setAcquireIncrement(5);
//    ds.setMaxPoolSize(20);
      
      dbi = new DBI(ds);
      // sql statements will be logged to Db.class as debug
      dbi.setSQLLog(new SLF4JLog(LoggerFactory.getLogger(Db.class), Level.DEBUG));

    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }                                  
  }
  
  protected static int APP_SCHEMA_VERSION = 1;
  
  /** creates or upgrades the db tables */
  public Db initializeTables() {
    Integer dbSchemaVersion = getDbSchemaVersion();
    if (dbSchemaVersion==null) {
      createTables();      
    } else {
      if (dbSchemaVersion>APP_SCHEMA_VERSION) {
        throw new RuntimeException("DB schema version ("+dbSchemaVersion+") is higher than the app schema version ("+APP_SCHEMA_VERSION+")");
      } if (dbSchemaVersion<APP_SCHEMA_VERSION) {
        upgradeDbSchema(dbSchemaVersion);
      }
    }
    return this;
  }

  /** returns null if the db schema table does not exist */
  protected Integer getDbSchemaVersion() {
    try {
      return dbi.inTransaction(new TransactionCallback<Integer>() {
        @Override
        public Integer inTransaction(Handle handle, TransactionStatus txStatus) throws Exception {
          boolean configurationsExists = false;
          ResultSet rs = handle
                  .getConnection()
                  .getMetaData()
                  .getTables(null, null, "configurations", null);
          configurationsExists = rs.next();
          
          if (configurationsExists) {
            log.debug("Table configurations exists, checking dbversion");
            String dbVersionString = handle.createQuery(configurationsQueryGetDbVersion())
              .map(StringMapper.FIRST)
              .first();
            
            if (dbVersionString!=null) {
              log.debug("Configuration dbversion is "+dbVersionString);
              try {
                return Integer.parseInt(dbVersionString);
              } catch (Exception e) {
                log.debug("Configuration dbversion does not parsable as integer");
              }
            } else {
              log.debug("Configuration dbversion does not exist");
            }
          } else {
            log.debug("Table configurations does not exist");
          }
          return null;
        }
      });
    } catch (CallbackFailedException e) {
      return null;
    }
  }

  public void dropTables() {
    dbi.inTransaction(new TransactionCallback<Void>() {
      @Override
      public Void inTransaction(Handle handle, TransactionStatus txStatus) throws Exception {
        handle.createStatement(configurationsDropTable()).execute();
        handle.createStatement(collectionsDropTable()).execute();
        return null;
      }
    });
  }

  protected void createTables() {
    dbi.inTransaction(new TransactionCallback<Void>() {
      @Override
      public Void inTransaction(Handle handle, TransactionStatus txStatus) throws Exception {
        handle.createStatement(configurationsCreateTable()).execute();
        handle.createStatement(collectionsCreateTable()).execute();
        handle.createStatement(configurationsInsertDbVersion(Integer.toString(APP_SCHEMA_VERSION))).execute();
        return null;
      }
    });
  }
  
  protected void upgradeDbSchema(int dbSchemaVersion) {
    while (dbSchemaVersion<APP_SCHEMA_VERSION) {
      upgradeDbVersion(dbSchemaVersion+1);
      dbSchemaVersion++;
    }
  }
  
  protected abstract String getDriverClassName();
  protected abstract String getConnectionUrl(String server, Integer port, String databaseName);

  protected abstract String configurationsDropTable();
  protected abstract String configurationsCreateTable();
  protected abstract String configurationsQueryGetDbVersion();
  protected abstract String configurationsInsertDbVersion(String appSchemaVersion);
  /** upgrades the database from the previous version to the given dbVersion */
  protected abstract void upgradeDbVersion(int dbVersion);

  protected abstract String collectionsDropTable();
  protected abstract String collectionsCreateTable();
  
  
}
