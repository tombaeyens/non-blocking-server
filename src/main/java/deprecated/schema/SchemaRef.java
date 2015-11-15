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


/**
 * @author Tom Baeyens
 */
public class SchemaRef extends Schema {

  protected String $ref;

  public String get$ref() {
    return this.$ref;
  }
  public void set$ref(String $ref) {
    this.$ref = $ref;
  }
  public SchemaRef $ref(String $ref) {
    this.$ref = $ref;
    return this;
  }
}
