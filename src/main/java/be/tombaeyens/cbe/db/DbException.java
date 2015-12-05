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



/**
 * @author Tom Baeyens
 */
public class DbException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DbException() {
    super();
  }

  public DbException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public DbException(String message, Throwable cause) {
    super(message, cause);
  }

  public DbException(String message) {
    super(message);
  }

  public DbException(Throwable cause) {
    super(cause);
  }

  public static void checkNotNull(Object o, String message, Object... args) {
    checkTrue(o==null, message, args);
  }

  public static void checkTrue(boolean condition, String message, Object... args) {
    if (condition) {
      throw new DbException(String.format(message, args));
    }
  }

}
