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
package be.tombaeyens.cbe.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author Tom Baeyens
 */
public class MapBuilder {
  
  protected Map map;
  
  public MapBuilder(Map map) {
    this.map = map;
  }

  public static MapBuilder hashMap() {
    return new MapBuilder(new HashMap());
  }
  
  public static MapBuilder linkedHashMap() {
    return new MapBuilder(new LinkedHashMap());
  }
  
  public MapBuilder entry(Object key, Object value) {
    map.put(key, value);
    return this;
  }
  
  public <K,V> Map<K,V> getMap() {
    return map;
  }

  public static <K,V> Map<K,V> inverseHashMap(Map<V,K> map) {
    if (map==null) {
      return null;
    }
    Map<K,V> inverse = new HashMap<>();
    for (Map.Entry<V, K> entry: map.entrySet()) {
      inverse.put(entry.getValue(), entry.getKey());
    }
    return inverse;
  }
}
