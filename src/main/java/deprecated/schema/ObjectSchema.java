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
package deprecated.schema;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Tom Baeyens
 */
public class ObjectSchema extends AbstractSchema {

  public static final String TYPE_OBJECT = "object";
  
  Map<String,Schema> properties;
  
  /** required properties */
  List<String> required;
  
  
  public ObjectSchema() {
    this.type = TYPE_OBJECT;
  }

  public ObjectSchema property(String name, Schema schema) {
    if (properties==null) {
      properties = new LinkedHashMap<>();
    }
    properties.put(name, schema);
    return this;
  }

  @Override
  public ObjectSchema title(String title) {
    return (ObjectSchema) super.title(title);
  }

  @Override
  public ObjectSchema description(String description) {
    return (ObjectSchema) super.description(description);
  }
}
