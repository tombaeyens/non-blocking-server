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

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.skife.jdbi.v2.Update;
import org.skife.jdbi.v2.exceptions.CallbackFailedException;
import org.skife.jdbi.v2.util.BooleanMapper;
import org.skife.jdbi.v2.util.StringMapper;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * @author Tom Baeyens
 */
public class Db {
  
  static InternalLogger log = InternalLoggerFactory.getInstance(Db.class);
  
  DBI dbi;
  
  public Db(DbBuilder dbBuilder) {
    try {
      ComboPooledDataSource ds = new ComboPooledDataSource();
      ds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
      ds.setJdbcUrl( "jdbc:postgresql://localhost/cbe" );
      ds.setUser("test");                                  
      ds.setPassword("test");
//    // the settings below are optional -- c3p0 can work with defaults
//    ds.setMinPoolSize(5);                                     
//    ds.setAcquireIncrement(5);
//    ds.setMaxPoolSize(20);
      
      dbi = new DBI(ds);

    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }                                  
  }
  
  protected static final DbUpgrade[] DB_UPGRADES = new DbUpgrade[
    // TODO add upgrades here
    ]{}; 
  protected static int APP_SCHEMA_VERSION = DB_UPGRADES.length;
  
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
          
          Connection connection = handle.getConnection();
          
          ResultSet rs = connection
            .createStatement()
            .executeQuery(
              "SELECT * FROM information_schema.tables \n"+ 
              "WHERE table_schema = 'public'");
          
          while (rs.next()) {
            String tableName = rs.getString("table_name");
            if ("configurations".equals(tableName.toLowerCase())) {
              log.debug("Configurations found  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            } else {
              log.debug(tableName);
            }
          }
          
          Boolean configurationsExists = handle.createQuery(
              "SELECT EXISTS ( \n"+
              "  SELECT 1  \n"+
              "  FROM   pg_catalog.pg_class c \n"+
              "  JOIN   pg_catalog.pg_namespace n ON n.oid = c.relnamespace \n"+
              "  WHERE  c.relname = 'configurations' \n"+
              "  AND    c.relkind = 'r' );")
            .map(BooleanMapper.FIRST)
            .first();
          
          if (configurationsExists) {
            log.debug("Table configurations exists, checking dbversion");
            String dbVersionString = handle.createQuery(
                "SELECT value \n"+
                "FROM configurations "+
                "WHERE id = :id")
              .bind("id", "dbversion")
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
        Update update = handle.createStatement(
                "DROP TABLE IF EXISTS configurations CASCADE;");
        update.execute();
        return null;
      }
    });
  }

  protected void createTables() {
    dbi.inTransaction(new TransactionCallback<Void>() {
      @Override
      public Void inTransaction(Handle handle, TransactionStatus txStatus) throws Exception {
        Update update = handle.createStatement(
                "CREATE TABLE configurations ( \n"+
                "  id    VARCHAR(1024) CONSTRAINT cfg_pk PRIMARY KEY, \n"+
                "  value VARCHAR(4096) \n"+
                ");");
        update.execute();
        return null;
      }
    });
  }
  
  protected void upgradeDbSchema(int dbSchemaVersion) {
  }
}
