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

import org.slf4j.Logger;
import org.slf4j.Marker;

import be.tombaeyens.cbe.util.log.SimpleSlf4jILoggerFactory.Level;


/**
 * @author Tom Baeyens
 */
public class SimpleSlf4jLogger implements Logger {

  SimpleSlf4jILoggerFactory factory;
  String name;
  Level level;

  public SimpleSlf4jLogger(SimpleSlf4jILoggerFactory factory, String name) {
    this.factory = factory;
    this.name = name;
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  public void setLevel(Level level) {
    this.level = level;
  }

  public boolean isEnabled(Level level) {
    if (level==null) {
      level = factory.defaultLevel;
    }
    return level.getNumber()>=level.getNumber();
  }

  @Override
  public boolean isTraceEnabled() {
    return isEnabled(Level.TRACE);
  }

  @Override
  public void trace(String msg) {
    factory.log(this, Level.TRACE, msg);
  }

  @Override
  public void trace(String format, Object arg) {
    factory.log(this, Level.TRACE, format, arg);
  }

  @Override
  public void trace(String format, Object arg1, Object arg2) {
    factory.log(this, Level.TRACE, format, arg1, arg2);
  }

  @Override
  public void trace(String format, Object... arguments) {
    factory.log(this, Level.TRACE, format, arguments);
  }

  @Override
  public void trace(String msg, Throwable t) {
    factory.log(this, Level.TRACE, msg, t);
  }

  @Override
  public boolean isTraceEnabled(Marker marker) {
    return isEnabled(Level.TRACE);
  }

  @Override
  public void trace(Marker marker, String msg) {
    factory.log(this, Level.TRACE, msg);
  }

  @Override
  public void trace(Marker marker, String format, Object arg) {
    factory.log(this, Level.TRACE, format, arg);
  }

  @Override
  public void trace(Marker marker, String format, Object arg1, Object arg2) {
    factory.log(this, Level.TRACE, format, arg1, arg2);
  }

  @Override
  public void trace(Marker marker, String format, Object... argArray) {
    factory.log(this, Level.TRACE, format, argArray);
  }

  @Override
  public void trace(Marker marker, String msg, Throwable t) {
    factory.log(this, Level.TRACE, msg, t);
  }

  @Override
  public boolean isDebugEnabled() {
    return isEnabled(Level.DEBUG);
  }
  
  @Override
  public void debug(String msg) {
    factory.log(this, Level.DEBUG, msg);
  }
  
  @Override
  public void debug(String format, Object arg) {
    factory.log(this, Level.DEBUG, format, arg);
  }
  
  @Override
  public void debug(String format, Object arg1, Object arg2) {
    factory.log(this, Level.DEBUG, format, arg1, arg2);
  }
  
  @Override
  public void debug(String format, Object... arguments) {
    factory.log(this, Level.DEBUG, format, arguments);
  }
  
  @Override
  public void debug(String msg, Throwable t) {
    factory.log(this, Level.DEBUG, msg, t);
  }
  
  @Override
  public boolean isDebugEnabled(Marker marker) {
    return isEnabled(Level.DEBUG);
  }
  
  @Override
  public void debug(Marker marker, String msg) {
    factory.log(this, Level.DEBUG, msg);
  }
  
  @Override
  public void debug(Marker marker, String format, Object arg) {
    factory.log(this, Level.DEBUG, format, arg);
  }
  
  @Override
  public void debug(Marker marker, String format, Object arg1, Object arg2) {
    factory.log(this, Level.DEBUG, format, arg1, arg2);
  }
  
  @Override
  public void debug(Marker marker, String format, Object... argArray) {
    factory.log(this, Level.DEBUG, format, argArray);
  }
  
  @Override
  public void debug(Marker marker, String msg, Throwable t) {
    factory.log(this, Level.DEBUG, msg, t);
  }

  @Override
  public boolean isInfoEnabled() {
    return isEnabled(Level.INFO);
  }

  @Override
  public void info(String msg) {
    factory.log(this, Level.INFO, msg);
  }

  @Override
  public void info(String format, Object arg) {
    factory.log(this, Level.INFO, format, arg);
  }

  @Override
  public void info(String format, Object arg1, Object arg2) {
    factory.log(this, Level.INFO, format, arg1, arg2);
  }

  @Override
  public void info(String format, Object... arguments) {
    factory.log(this, Level.INFO, format, arguments);
  }

  @Override
  public void info(String msg, Throwable t) {
    factory.log(this, Level.INFO, msg, t);
  }

  @Override
  public boolean isInfoEnabled(Marker marker) {
    return isEnabled(Level.INFO);
  }

  @Override
  public void info(Marker marker, String msg) {
    factory.log(this, Level.INFO, msg);
  }

  @Override
  public void info(Marker marker, String format, Object arg) {
    factory.log(this, Level.INFO, format, arg);
  }

  @Override
  public void info(Marker marker, String format, Object arg1, Object arg2) {
    factory.log(this, Level.INFO, format, arg1, arg2);
  }

  @Override
  public void info(Marker marker, String format, Object... argArray) {
    factory.log(this, Level.INFO, format, argArray);
  }

  @Override
  public void info(Marker marker, String msg, Throwable t) {
    factory.log(this, Level.INFO, msg, t);
  }


 @Override
 public boolean isWarnEnabled() {
   return isEnabled(Level.WARNING);
 }

 @Override
 public void warn(String msg) {
   factory.log(this, Level.WARNING, msg);
 }

 @Override
 public void warn(String format, Object arg) {
   factory.log(this, Level.WARNING, format, arg);
 }

 @Override
 public void warn(String format, Object arg1, Object arg2) {
   factory.log(this, Level.WARNING, format, arg1, arg2);
 }

 @Override
 public void warn(String format, Object... arguments) {
   factory.log(this, Level.WARNING, format, arguments);
 }

 @Override
 public void warn(String msg, Throwable t) {
   factory.log(this, Level.WARNING, msg, t);
 }

 @Override
 public boolean isWarnEnabled(Marker marker) {
   return isEnabled(Level.WARNING);
 }

 @Override
 public void warn(Marker marker, String msg) {
   factory.log(this, Level.WARNING, msg);
 }

 @Override
 public void warn(Marker marker, String format, Object arg) {
   factory.log(this, Level.WARNING, format, arg);
 }

 @Override
 public void warn(Marker marker, String format, Object arg1, Object arg2) {
   factory.log(this, Level.WARNING, format, arg1, arg2);
 }

 @Override
 public void warn(Marker marker, String format, Object... argArray) {
   factory.log(this, Level.WARNING, format, argArray);
 }

 @Override
 public void warn(Marker marker, String msg, Throwable t) {
   factory.log(this, Level.WARNING, msg, t);
 }

 @Override
 public boolean isErrorEnabled() {
   return isEnabled(Level.ERROR);
 }

 @Override
 public void error(String msg) {
   factory.log(this, Level.ERROR, msg);
 }

 @Override
 public void error(String format, Object arg) {
   factory.log(this, Level.ERROR, format, arg);
 }

 @Override
 public void error(String format, Object arg1, Object arg2) {
   factory.log(this, Level.ERROR, format, arg1, arg2);
 }

 @Override
 public void error(String format, Object... arguments) {
   factory.log(this, Level.ERROR, format, arguments);
 }

 @Override
 public void error(String msg, Throwable t) {
   factory.log(this, Level.ERROR, msg, t);
 }

 @Override
 public boolean isErrorEnabled(Marker marker) {
   return isEnabled(Level.ERROR);
 }

 @Override
 public void error(Marker marker, String msg) {
   factory.log(this, Level.ERROR, msg);
 }

 @Override
 public void error(Marker marker, String format, Object arg) {
   factory.log(this, Level.ERROR, format, arg);
 }

 @Override
 public void error(Marker marker, String format, Object arg1, Object arg2) {
   factory.log(this, Level.ERROR, format, arg1, arg2);
 }

 @Override
 public void error(Marker marker, String format, Object... argArray) {
   factory.log(this, Level.ERROR, format, argArray);
 }

 @Override
 public void error(Marker marker, String msg, Throwable t) {
   factory.log(this, Level.ERROR, msg, t);
 }
}
