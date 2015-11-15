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
package be.tombaeyens.cbe.util.log;

import io.netty.util.internal.logging.AbstractInternalLogger;
import be.tombaeyens.cbe.util.log.SimpleLoggerFactory.Level;


/**
 * @author Tom Baeyens
 */
public class SimpleLogger extends AbstractInternalLogger {
  
  private static final long serialVersionUID = 1L;
  
  /** all logs above this level will be filtered */
  Level level = Level.TRACE;
  SimpleLoggerFactory simpleLoggerFactory;

  public SimpleLogger(SimpleLoggerFactory simpleLoggerFactory, String name) {
    super(name);
    this.simpleLoggerFactory = simpleLoggerFactory;
  }

  public void setLevel(Level level) {
    this.level = level;
  }

  public String getName() {
    return name();
  }

  public boolean isTraceEnabled() {
    return level.getNumber()<=Level.TRACE.getNumber();
  }

  public void trace(String msg) {
    simpleLoggerFactory.log(this, Level.TRACE, msg);
  }

  public void trace(String format, Object arg) {
    simpleLoggerFactory.log(this, Level.TRACE, format, arg);
  }

  public void trace(String format, Object argA, Object argB) {
    simpleLoggerFactory.log(this, Level.TRACE, format, argA, argB);
  }

  public void trace(String format, Object... arguments) {
    simpleLoggerFactory.log(this, Level.TRACE, format, arguments);
  }

  public void trace(String msg, Throwable t) {
    simpleLoggerFactory.log(this, Level.TRACE, msg, t);
  }

  public boolean isDebugEnabled() {
    return level.getNumber()<=Level.DEBUG.getNumber();
  }

  public void debug(String msg) {
    simpleLoggerFactory.log(this, Level.DEBUG, msg);
  }

  public void debug(String format, Object arg) {
    simpleLoggerFactory.log(this, Level.DEBUG, format, arg);
  }

  public void debug(String format, Object argA, Object argB) {
    simpleLoggerFactory.log(this, Level.DEBUG, format, argA, argB);
  }

  public void debug(String format, Object... arguments) {
    simpleLoggerFactory.log(this, Level.DEBUG, format, arguments);
  }

  public void debug(String msg, Throwable t) {
    simpleLoggerFactory.log(this, Level.DEBUG, msg, t);
  }

  public boolean isInfoEnabled() {
    return level.getNumber()<=Level.INFO.getNumber();
  }

  public void info(String msg) {
    simpleLoggerFactory.log(this, Level.INFO, msg);
  }

  public void info(String format, Object arg) {
    simpleLoggerFactory.log(this, Level.INFO, format, arg);
  }

  public void info(String format, Object argA, Object argB) {
    simpleLoggerFactory.log(this, Level.INFO, format, argA, argB);
  }

  public void info(String format, Object... arguments) {
    simpleLoggerFactory.log(this, Level.INFO, format, arguments);
  }

  public void info(String msg, Throwable t) {
    simpleLoggerFactory.log(this, Level.INFO, msg, t);
  }

  public boolean isWarnEnabled() {
    return level.getNumber()<=Level.WARNING.getNumber();
  }

  public void warn(String msg) {
    simpleLoggerFactory.log(this, Level.WARNING, msg);
  }

  public void warn(String format, Object arg) {
    simpleLoggerFactory.log(this, Level.WARNING, format, arg);
  }

  public void warn(String format, Object... arguments) {
    simpleLoggerFactory.log(this, Level.WARNING, format, arguments);
  }

  public void warn(String format, Object argA, Object argB) {
    simpleLoggerFactory.log(this, Level.WARNING, format, argA, argB);
  }

  public void warn(String msg, Throwable t) {
    simpleLoggerFactory.log(this, Level.WARNING, msg, t);
  }

  public boolean isErrorEnabled() {
    return level.getNumber()<=Level.ERROR.getNumber();
  }

  public void error(String msg) {
    simpleLoggerFactory.log(this, Level.ERROR, msg);
  }

  public void error(String format, Object arg) {
    simpleLoggerFactory.log(this, Level.ERROR, format, arg);
  }

  public void error(String format, Object argA, Object argB) {
    simpleLoggerFactory.log(this, Level.ERROR, format, argA, argB);
  }

  public void error(String format, Object... arguments) {
    simpleLoggerFactory.log(this, Level.ERROR, format, arguments);
  }

  public void error(String msg, Throwable t) {
    simpleLoggerFactory.log(this, Level.ERROR, msg, t);
  }
}
