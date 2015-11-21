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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;


/** json array 
 * @author Tom Baeyens
 */
public class A {
  
  JsonArray array = new JsonArray();
  Gson gson;
  
  public A(Gson gson) {
    this.gson = gson;
  }

  /** append */
  public A a(Object value) {
    if (value == null) {
      array.add(null);
    } else if (value instanceof JsonElement) {
      array.add((JsonElement)value);
    } else if (value instanceof String) {
      array.add(new JsonPrimitive((String)value));
    } else if (value instanceof Number) {
      array.add(new JsonPrimitive((Number)value));
    } else if (value instanceof Boolean) {
      array.add(new JsonPrimitive((Boolean)value));
    } else if (value instanceof Character) {
      array.add(new JsonPrimitive((Character)value));
    } else if (value instanceof O) {
      array.add(((O)value).object);
    } else if (value instanceof A) {
      array.add(((A)value).array);
    } else {
      throw new RuntimeException("Unknown value type: "+value.getClass().getName());
    }
    return this;
  }
  
  @Override
  public String toString() {
    return gson.toJson(array);
  }
}
