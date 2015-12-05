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
public class ArrayType extends AbstractDataType {

  protected AbstractDataType elementType;

  public AbstractDataType getElementType() {
    return this.elementType;
  }
  public void setElementType(AbstractDataType elementType) {
    this.elementType = elementType;
  }
  public ArrayType elementType(AbstractDataType elementType) {
    this.elementType = elementType;
    return this;
  }
  
  @Override
  public boolean isObject() {
    return false;
  }
  @Override
  public boolean isArray() {
    return true;
  }
  @Override
  public boolean isPrimitive() {
    return false;
  }
}
