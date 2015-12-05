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
package be.tombaeyens.cbe.test;

import org.junit.Test;

import be.tombaeyens.cbe.model.common.Collection;
import be.tombaeyens.cbe.model.common.Document;
import be.tombaeyens.cbe.test.framework.AbstractTest;


/**
 * @author Tom Baeyens
 */
public class DocumentTest extends AbstractTest {

  @Test
  public void testCollections() {
    POST("collections")
      .bodyJson(o("name", "invoice"))
      .execute()
      .assertStatusCreated()
      .body(Collection.class);
    
    Document document = POST("documents/invoices")
      .bodyJson(o("customer", "bigco"))
      .execute()
      .assertStatusCreated()
      .body(Document.class);
    
    document = GET("documents/invoices/"+document.getId())
      .execute()
      .assertStatusOk()
      .body(Document.class);
  }
}
