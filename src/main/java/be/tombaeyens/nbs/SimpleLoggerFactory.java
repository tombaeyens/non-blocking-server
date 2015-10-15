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
package be.tombaeyens.nbs;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Tom Baeyens
 */
public class SimpleLoggerFactory extends InternalLoggerFactory {

  public static SimpleLoggerFactory INSTANCE = new SimpleLoggerFactory();

  public enum Level {
    ERROR(0),
    WARNING(1),
    INFO(2),
    DEBUG(3),
    TRACE(4),
    ;
    private int number;
    private Level(int number) {
      this.number = number;
    }
    public int getNumber() {
      return number;
    }
  }

  /** maps logger names to simple loggers */
  Map<String,SimpleLogger> loggers = new ConcurrentHashMap<>();
  /** maps logger name parts to configured log levels */
  Map<String,Level> configuredLevels = Collections.synchronizedMap(new LinkedHashMap<>());
  
  @Override
  protected InternalLogger newInstance(String name) {
    SimpleLogger simpleLogger = new SimpleLogger(this, name);
    for (String configuredLoggerNamePart: configuredLevels.keySet()) {
      if (name.toLowerCase().contains(configuredLoggerNamePart)) {
        simpleLogger.setLevel(configuredLevels.get(configuredLoggerNamePart));
      }
    }
    return simpleLogger;
  }
  
  // TODO more of these
  /** ensures that for all matching loggers, only messages are 
   * logged which have level equal or more important (= lower number). 
   * @param levelText is trace, debug, info, warning, error (case is ignored)
   *   Invalid levels will have no effect. */
  public void configure(String loggerNamePart, Level level) {
    if (level!=null && loggerNamePart!=null) {
      for (SimpleLogger logger: loggers.values()) {
        if (logger.getName().toLowerCase().contains(loggerNamePart)) {
          logger.setLevel(level);
        }
      }
      configuredLevels.put(loggerNamePart, level);
    }
  }

  protected void log(SimpleLogger logger, Level level, String message, Object... arguments) {
    if (level.getNumber()>logger.level.getNumber()) {
      return;
    }
    
    StringBuilder log = new StringBuilder();
    appendTime(log);
    log.append(" ");
    appendLevel(log, level);
    log.append(" ");
    appendLogger(log, logger);
    log.append(" ");
    appendMessage(log, message, arguments);
    
    log(log.toString());
  }

  protected SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss,SSS");
  protected void appendTime(StringBuilder log) {
    log.append(dateFormat.format(new Date()));
  }

  protected final Map<Level,String> levelTexts = new HashMap<>();
  {
    levelTexts.put(Level.ERROR,   "ERR");
    levelTexts.put(Level.WARNING, "WAR");
    levelTexts.put(Level.INFO,    "INF");
    levelTexts.put(Level.DEBUG,   "DEB");
    levelTexts.put(Level.TRACE,   "TRA");
  }
  protected void appendLevel(StringBuilder log, Level level) {
    String levelText = levelTexts.get(level);
    if (levelText!=null) {
      log.append(levelText);
    } else {
      log.append(Integer.toString(level.getNumber()));
    }
  }

  private void appendMessage(StringBuilder log, String message, Object[] arguments) {
    if (message!=null) {
      if (arguments==null) {
        log.append(message);
      } else if (isExceptionOnlyArgument(arguments)) {
        log.append(message);
        log.append("\nException: ");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        Throwable throwable = (Throwable) arguments[0];
        throwable.printStackTrace(printWriter);
        printWriter.flush();
        log.append(stringWriter.toString());
      } else {
        log.append(String.format(message, arguments));
      }
    }
  }

  protected boolean isExceptionOnlyArgument(Object[] arguments) {
    return arguments.length==1 
            && arguments[0]!=null 
            && Throwable.class.isAssignableFrom(arguments[0].getClass());
  }

  private final Map<String,String> shortLoggerNames = new ConcurrentHashMap<>();
  private void appendLogger(StringBuilder log, SimpleLogger logger) {
    String loggerName = logger.getName();
    String shortLoggerName = shortLoggerNames.get(loggerName);
    if (shortLoggerName==null) {
      shortLoggerName = loggerName.substring(loggerName.lastIndexOf('.')+1);
      shortLoggerNames.put(loggerName, shortLoggerName);
    }
    log.append(shortLoggerName);
  }

  protected void log(String log) {
    System.err.println(log);
  }
}
