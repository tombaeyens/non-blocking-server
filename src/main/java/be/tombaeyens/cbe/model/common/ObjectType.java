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

import java.util.HashMap;
import java.util.Map;



/**
 * @author Tom Baeyens
 */
public class ObjectType extends AbstractDataType {

  protected Map<String,AbstractDataType> fields;

  public Map<String,AbstractDataType> getFields() {
    return this.fields;
  }
  public void setFields(Map<String,AbstractDataType> fields) {
    this.fields = fields;
  }
  public ObjectType field(String fieldName, AbstractDataType dataType) {
    if (fields==null) {
      fields = new HashMap<>();
    }
    fields.put(fieldName, dataType);
    return this;
  }
  
  @Override
  public boolean isObject() {
    return true;
  }
  @Override
  public boolean isArray() {
    return false;
  }
  @Override
  public boolean isPrimitive() {
    return false;
  }
}
