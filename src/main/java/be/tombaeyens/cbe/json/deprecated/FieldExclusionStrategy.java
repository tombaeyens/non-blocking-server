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
package be.tombaeyens.cbe.json.deprecated;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;


/**
 * @author Tom Baeyens
 */
public class FieldExclusionStrategy implements ExclusionStrategy {
  
  Map<Class<?>, Set<String>> excludedFields = new HashMap<>();
  
  public FieldExclusionStrategy exclude(Class<?> clazz, String fieldName) {
    Set<String> fields = excludedFields.get(clazz);
    if (fields==null) {
      fields = new HashSet<>();
      excludedFields.put(clazz, fields);
    }
    fields.add(fieldName);
    return this;
  }

  @Override
  public boolean shouldSkipField(FieldAttributes f) {
    Set<String> fields = excludedFields.get(f.getDeclaredClass());
    return fields!=null && fields.contains(f.getName());
  }

  @Override
  public boolean shouldSkipClass(Class< ? > clazz) {
    return false;
  }
}
