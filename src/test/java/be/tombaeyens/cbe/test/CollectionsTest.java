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

import static org.junit.Assert.*;
import graphql.language.ObjectField;

import org.junit.Test;

import be.tombaeyens.cbe.db.tables.Collection;
import be.tombaeyens.cbe.test.framework.AbstractTest;


/**
 * @author Tom Baeyens
 */
public class CollectionsTest extends AbstractTest {
  
  @Test
  public void testOne() {
    Collection collection = POST("collections")
      .bodyJson(o("name", "invoice"))
      .execute()
      .assertStatusCreated()
      .body(Collection.class);
    assertNotNull(collection.getId());
    assertEquals("invoice", collection.getName());
    
//    ObjectField customer = POST("collections/"+collection.getId()+"/type/fields")
//            .bodyJson(o("name", "customer"))
//            .execute()
//            .assertStatusCreated()
//            .body(ObjectField.class);
    
  }
}
