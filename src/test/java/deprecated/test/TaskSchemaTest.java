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
package deprecated.test;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import deprecated.schema.ObjectSchema;
import deprecated.schema.StringSchema;


/**
 * @author Tom Baeyens
 */
public class TaskSchemaTest {

  @Test 
  public void testTaskSchema() {
    ObjectSchema taskSchema = new ObjectSchema()
      .title("Task")
      .property("id", IdSchemaRef.INSTANCE)
      .property("title", StringSchema.INSTANCE)
      .property("description", StringSchema.INSTANCE);
    
    Gson gson = new GsonBuilder()
      .setPrettyPrinting()
      .create();
    
    System.out.println(gson.toJson(taskSchema));
  }
}
