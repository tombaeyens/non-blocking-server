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
package be.tombaeyens.cbe.json.deprecated;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.LinkedHashMap;
import java.util.Map;

import be.tombaeyens.cbe.model.common.AbstractDataType;
import be.tombaeyens.cbe.model.common.ArrayType;
import be.tombaeyens.cbe.model.common.DataType;
import be.tombaeyens.cbe.model.common.ObjectType;
import be.tombaeyens.cbe.model.common.PrimitiveType;
import be.tombaeyens.cbe.util.MapBuilder;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;


/**
 * @author Tom Baeyens
 */
public class DataTypeAdapter implements TypeAdapterFactory {
  
  private static final String TYPE_FIELD_NAME = "type";
  Map<Class<? extends AbstractDataType>, String> typeNames = MapBuilder.hashMap()
    .entry(ObjectType.class, "object")
    .entry(ArrayType.class, "array")
    .entry(PrimitiveType.class, "primitive")
    .entry(DataType.class, "datatype")
    .getMap();
  Map<String, Class<? extends AbstractDataType>> typeClasses = MapBuilder.inverseHashMap(typeNames);

//  @Override
//  public AbstractDataType deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//    JsonObject jsonObject =  json.getAsJsonObject();
//    String typeName = jsonObject.remove(TYPE_FIELD_NAME).getAsString();
//    Class<?> klass = typeClasses.get(typeName);
//    return context.deserialize(jsonObject, klass);
//  }
//
//  @Override
//  public JsonElement serialize(AbstractDataType src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
//    JsonObject json = (JsonObject) context.serialize(src); 
//    String typeName = typeNames.get(src.getClass());
//    if (typeName==null) {
//      throw new RuntimeException("Unknown DataType: "+src.getClass().getName());
//    }
//    json.addProperty(TYPE_FIELD_NAME, typeName);
//    return json;
//  }
}
