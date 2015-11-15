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
public class AbstractSchema extends Schema {
  
  /** short description */
  protected String title;

  /** long description */
  protected String description;

  protected String type;

  public String getTitle() {
    return this.title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public AbstractSchema title(String title) {
    this.title = title;
    return this;
  }

  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public AbstractSchema description(String description) {
    this.description = description;
    return this;
  }

  public String getType() {
    return this.type;
  }
}
