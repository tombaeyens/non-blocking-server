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
public class Collection {

  protected String id;
  
  /** The name used for display to people in UI */
  protected String name;

  /** The name used in the URL for the web API, eg when urlName is 'invoices', 
   * the collection CRUD requests will be exposed as:
   *   GET /collections/invoices
   *   POST /collections/invoices
   *   GET /collections/invoices/:invoiceId
   *   PUT /collections/invoices/:invoiceId
   *   DELETE /collections/invoices/:invoiceId */
  protected String urlName;
  
  /** fk to {@link AbstractDataType} */
  protected String typeId;

  public String getUrlName() {
    return this.urlName;
  }
  public void setUrlName(String urlName) {
    this.urlName = urlName;
  }
  public Collection urlName(String urlName) {
    this.urlName = urlName;
    return this;
  }

  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public Collection id(String id) {
    this.id = id;
    return this;
  }
  
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Collection name(String name) {
    this.name = name;
    return this;
  }

  public String getTypeId() {
    return this.typeId;
  }
  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }
  public Collection typeId(String typeId) {
    this.typeId = typeId;
    return this;
  }
}
