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
package be.tombaeyens.cbe.db.tables;


/**
 * @author Tom Baeyens
 */
public class Type {
  
  public enum Base {
    STRING,
    NUMBER,
    BOOLEAN,
    NULL,
    OBJECT,
    ARRAY
  }
  
  protected String id;
  protected String name;
  protected Base base;

  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public Type id(String id) {
    this.id = id;
    return this;
  }
  
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Type name(String name) {
    this.name = name;
    return this;
  }
  public Base getBase() {
    return this.base;
  }
  public void setBase(Base base) {
    this.base = base;
  }
  public Type base(Base base) {
    this.base = base;
    return this;
  }
}
