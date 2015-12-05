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

import java.util.List;
import java.util.Map;

import org.junit.Test;

import be.tombaeyens.cbe.model.common.Collection;
import be.tombaeyens.cbe.test.framework.AbstractTest;


/**
 * @author Tom Baeyens
 */
public class CollectionsTest extends AbstractTest {
  
  @Test
  public void testBasicLifecycle() {
    List<Collection> collections = GET("collections")
            .execute()
            .assertStatusOk()
            .bodyList(Collection.class);
    assertEquals(0, collections.size());

    Collection collection = POST("collections")
      .bodyJson(o("name", "invoice"))
      .execute()
      .assertStatusCreated()
      .body(Collection.class);
    assertNotNull(collection.getId());
    assertEquals("invoice", collection.getName());
    
    collections = GET("collections")
            .execute()
            .assertStatusOk()
            .bodyList(Collection.class);
    assertEquals(1, collections.size());
    collection = collections.get(0);
    assertNotNull(collection.getId());
    assertEquals("invoice", collection.getName());

    collection = GET("collections/"+collection.getId())
            .execute()
            .assertStatusOk()
            .body(Collection.class);
    assertNotNull(collection.getId());
    assertEquals("invoice", collection.getName());

    DELETE("collections/"+collection.getId())
      .execute()
      .assertStatusNoContent();
    
    collections = GET("collections")
            .execute()
            .assertStatusOk()
            .bodyList(Collection.class);
    assertEquals(0, collections.size());    
  }

  @Test
  public void testDefaultUrlName() {
    Map collection = POST("collections")
      .bodyJson(o("name", "invoice"))
      .execute()
      .assertStatusCreated()
      .body(Map.class);
    assertEquals("invoices", collection.get("urlName"));
    
    collection = GET("collections/"+collection.get("id"))
            .execute()
            .assertStatusOk()
            .body(Map.class);
    assertEquals("invoices", collection.get("urlName"));
  }
}
