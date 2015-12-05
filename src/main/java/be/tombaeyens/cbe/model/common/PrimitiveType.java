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
package be.tombaeyens.cbe.model.common;


/**
 * @author Tom Baeyens
 */
public class PrimitiveType extends AbstractDataType {
  
  public static final String TYPENAME_STRING = "string";
  public static final String TYPENAME_NUMBER = "number";
  public static final String TYPENAME_BOOLEAN = "boolean";

  public static final PrimitiveType STRING = new PrimitiveType().name(TYPENAME_STRING);
  public static final PrimitiveType NUMBER = new PrimitiveType().name(TYPENAME_NUMBER);
  public static final PrimitiveType BOOLEAN = new PrimitiveType().name(TYPENAME_BOOLEAN);

  protected String name;

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public PrimitiveType name(String name) {
    this.name = name;
    return this;
  }
  
  @Override
  public boolean isObject() {
    return false;
  }

  @Override
  public boolean isArray() {
    return false;
  }

  @Override
  public boolean isPrimitive() {
    return true;
  }
}
