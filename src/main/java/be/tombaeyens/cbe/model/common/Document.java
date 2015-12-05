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
public class Document {

  protected String id;
  protected String collectionId;
  protected String json;

  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public Document id(String id) {
    this.id = id;
    return this;
  }

  public String getCollectionId() {
    return this.collectionId;
  }
  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }
  public Document collectionId(String collectionId) {
    this.collectionId = collectionId;
    return this;
  }

  public String getJson() {
    return this.json;
  }
  public void setJson(String json) {
    this.json = json;
  }
  public Document json(String json) {
    this.json = json;
    return this;
  }
}
