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
package be.tombaeyens.cbe.test.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class O {
  
  JsonObject object = new JsonObject();
  Gson gson;
  
  public O(Gson gson) {
    this.gson = gson;
  }

  /** append */
  public O a(String propertyName, Object value) {
    if (value == null) {
      object.add(propertyName, null);
    } else if (value instanceof JsonElement) {
      object.add(propertyName, (JsonElement)value);
    } else if (value instanceof String) {
      object.addProperty(propertyName, (String)value);
    } else if (value instanceof Number) {
      object.addProperty(propertyName, (Number)value);
    } else if (value instanceof Boolean) {
      object.addProperty(propertyName, (Boolean)value);
    } else if (value instanceof Character) {
      object.addProperty(propertyName, (Character)value);
    } else if (value instanceof O) {
      object.add(propertyName, ((O)value).object);
    } else if (value instanceof A) {
      object.add(propertyName, ((A)value).array);
    } else {
      throw new RuntimeException("Unknown value type: "+value.getClass().getName());
    }
    return this;
  }

  @Override
  public String toString() {
    return gson.toJson(object);
  }
}