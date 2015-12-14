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
package be.tombaeyens.cbe.json;

import be.tombaeyens.cbe.model.common.AbstractDataType;
import be.tombaeyens.cbe.model.common.ArrayType;
import be.tombaeyens.cbe.model.common.DataType;
import be.tombaeyens.cbe.model.common.ObjectType;
import be.tombaeyens.cbe.model.common.PrimitiveType;
import be.tombaeyens.json.ClassHierarchy;
import be.tombaeyens.json.JsonBuilder;


/**
 * @author Tom Baeyens
 */
public class CbeJsonBuilder extends JsonBuilder {

  public CbeJsonBuilder() {
    classHierarchy(new ClassHierarchy()
      .baseClass(AbstractDataType.class, "abstract")
      .subClass(ObjectType.class, "object")
      .subClass(ArrayType.class, "array")
      .subClass(PrimitiveType.class, "primitive")
      .subClass(DataType.class));
  }
}
