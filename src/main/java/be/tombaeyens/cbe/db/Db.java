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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.tombaeyens.cbe.db.tables.CollectionsTable;
import be.tombaeyens.cbe.db.tables.ConfigurationsTable;
import be.tombaeyens.cbe.db.tables.DataTypesTable;
import be.tombaeyens.cbe.db.tables.DocumentsTable;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * @author Tom Baeyens
 */
public abstract class Db {
  
  private static final Logger log = LoggerFactory.getLogger(Db.class);
  
  protected static int APP_SCHEMA_VERSION = 1;
  
  protected DataSource dataSource;
  protected Map<Class<? extends DbTable>,DbTable> dbTables = new LinkedHashMap<>();
  protected IdGenerator idGenerator;
  
  public Db(DbBuilder dbBuilder) {
    try {
      ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
      comboPooledDataSource.setDriverClass(getDriverClassName()); //loads the jdbc driver
      
      String connectionUrl = dbBuilder.getConnectionUrl();
      if (connectionUrl==null) {
        String server = dbBuilder.getServer();
        Integer port = dbBuilder.getPort();
        String databaseName = dbBuilder.getDatabaseName();
        connectionUrl = getConnectionUrl(server, port, databaseName);
      }
      comboPooledDataSource.setJdbcUrl(connectionUrl);
      
      comboPooledDataSource.setUser(dbBuilder.getUsername());                                  
      comboPooledDataSource.setPassword(dbBuilder.getPassword());

//    // the settings below are optional -- c3p0 can work with defaults
//    ds.setMinPoolSize(5);                                     
//    ds.setAcquireIncrement(5);
//    ds.setMaxPoolSize(20);
      
      this.dataSource = comboPooledDataSource;
      this.idGenerator = dbBuilder.getIdGenerator();

      initializeDbTables();

    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }                                  
  }
  
  /** to customize a collection, override, first delegate to 
   * this implementation and then overwrite the keys used 
   * in this implementation with customized collection 
   * implementations as values. */
  protected void initializeDbTables() {
    dbTables.put(ConfigurationsTable.class, new ConfigurationsTable(this));
    dbTables.put(CollectionsTable.class, new CollectionsTable(this));
    dbTables.put(DataTypesTable.class, new DataTypesTable(this));
    dbTables.put(DocumentsTable.class, new DocumentsTable(this));
  }

  public ConfigurationsTable getCongfigurationsTable() {
    return getDbTable(ConfigurationsTable.class);
  }

  public CollectionsTable getCollectionsTable() {
    return getDbTable(CollectionsTable.class);
  }

  public DataTypesTable getDataTypesTable() {
    return getDbTable(DataTypesTable.class);
  }
  
  public DocumentsTable getDocumentsTable() {
    return getDbTable(DocumentsTable.class);
  }


  public <T> T getDbTable(Class<T> dbTableClass) {
    return (T) dbTables.get(dbTableClass);
  }


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
    return tx(tx -> {
        boolean configurationsExists = false;
        try {
          ResultSet rs = tx
                  .getConnection()
                  .getMetaData()
                  .getTables(null, null, "configurations", null);
          configurationsExists = rs.next();
        } catch (Exception e) {
          throw new RuntimeException("Couldn't get table metadata: "+e.getMessage(), e);
        }
        
        if (configurationsExists) {
          log.debug("Table configurations exists, checking dbversion");
          String sqlSelectDbVersion = getDbTable(ConfigurationsTable.class).sqlSelectDbVersion();
          String dbVersionString = tx.createQuery(sqlSelectDbVersion)
            .execute()
            .getFirstAsString();
          
          if (dbVersionString!=null) {
            log.debug("Configuration dbversion is "+dbVersionString);
            try {
              int dbVersion = Integer.parseInt(dbVersionString);
              tx.setResult(dbVersion);
            } catch (Exception e) {
              log.debug("Configuration dbversion does not parsable as integer");
            }
          } else {
            log.debug("Configuration dbversion does not exist");
          }
        } else {
          log.debug("Table configurations does not exist");
        }
      }
    );
  }

  public void dropTables() {
    tx(tx -> {
        for (DbTable dbTable: dbTables.values()) {
          String dropSql = dbTable.sqlDrop();
          tx.createUpdate(dropSql).execute();
        }
      }
    );
  }

  protected void createTables() {
    tx(tx -> {
        for (DbTable dbTable: dbTables.values()) {
          String createSql = dbTable.sqlCreate();
          tx.createUpdate(createSql).execute();
        }
        String insertDbVersionSql = getDbTable(ConfigurationsTable.class)
                .sqlInsertDbVersion(Integer.toString(APP_SCHEMA_VERSION));
        tx.createUpdate(insertDbVersionSql).execute();
      }
    );
  }
  
  public <T> T tx(TxLogic txLogic) {
    Connection connection = null;
    Tx tx = null;
    Exception exception = null;
    try {
      connection = dataSource.getConnection();
      connection.setAutoCommit(false);
      tx = new Tx(this, connection);
      txLogic.execute(tx);
    } catch (Exception e) {
      exception = e;
      tx.setRollbackOnly(e);
    } 
    if (tx!=null) {
      tx.end();
    }
    if (connection!=null) {
      try {
        connection.close();
      } catch (SQLException e) {
        log.error("Tx connection close: "+e.getMessage(), e);
      }
    }
    if (exception!=null) {
      if (exception instanceof RuntimeException) {
        throw (RuntimeException) exception;
      } else {
        throw new RuntimeException("Transaction failed: "+exception.getMessage(), exception);
      }
    }
    return tx!=null ? (T) tx.getResult() : null;
  }

  protected void upgradeDbSchema(int dbSchemaVersion) {
    while (dbSchemaVersion<APP_SCHEMA_VERSION) {
      upgradeDbVersion(dbSchemaVersion+1);
      dbSchemaVersion++;
    }
  }
  
  protected abstract String getDriverClassName();
  protected abstract String getConnectionUrl(String server, Integer port, String databaseName);
  /** upgrades the database from the previous version to the given dbVersion */
  protected abstract void upgradeDbVersion(int dbVersion);

  protected String getDropSqlTemplate() {
    return "DROP TABLE %s";
  }

  public String typeVarcharId() {
    return "VARCHAR(1024)";
  }
  
  public String typeVarchar() {
    return "VARCHAR(4096)";
  }

  public String typeJson() {
    return "JSON";
  }

  public String nextId() {
    return idGenerator.nextId();
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public Map<Class< ? extends DbTable>, DbTable> getDbTables() {
    return dbTables;
  }

  public IdGenerator getIdGenerator() {
    return idGenerator;
  }
}
