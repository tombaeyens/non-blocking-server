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


/** Extends {@link ObjectType} with an id and a name.
 * 
 * The name is optional.  A name can provided for reusable types 
 * like money, , it can be offered in the 
 * UI for reusing in different collections.
 * 
 * @author Tom Baeyens
 */
public class DataType extends ObjectType {

  protected String id;
  protected String name;
  
  @Override
  public DataType field(String fieldName, AbstractDataType dataType) {
    return (DataType) super.field(fieldName, dataType);
  }
  
  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public DataType id(String id) {
    this.id = id;
    return this;
  }
  
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public DataType name(String name) {
    this.name = name;
    return this;
  }
}
