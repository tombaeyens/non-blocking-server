/* Copyright (c) 2014, Effektif GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package be.tombaeyens.cbe.http.router;

import io.netty.handler.codec.http.router.Router;

import java.util.ArrayList;
import java.util.List;

import be.tombaeyens.cbe.http.framework.Delete;
import be.tombaeyens.cbe.http.framework.Deletes;
import be.tombaeyens.cbe.http.framework.Get;
import be.tombaeyens.cbe.http.framework.Gets;
import be.tombaeyens.cbe.http.framework.Post;
import be.tombaeyens.cbe.http.framework.Posts;
import be.tombaeyens.cbe.http.framework.Put;
import be.tombaeyens.cbe.http.framework.Puts;
import be.tombaeyens.cbe.http.framework.RequestHandler;
import be.tombaeyens.cbe.http.requests.CollectionDelete;
import be.tombaeyens.cbe.http.requests.CollectionGet;
import be.tombaeyens.cbe.http.requests.CollectionsGet;
import be.tombaeyens.cbe.http.requests.CollectionsPost;
import be.tombaeyens.cbe.http.requests.Oops;


/**
 * @author Tom Baeyens
 */
public class CbeRouter extends Router<Class< ? extends RequestHandler>> {

  public CbeRouter() {
    scan(CollectionsPost.class);
    scan(CollectionGet.class);
    scan(CollectionsGet.class);
    scan(CollectionDelete.class);
    
    notFound(Oops.class);
  }

  protected void scan(Class< ? extends RequestHandler> clazz) {
    Gets repeatableAnnotation = clazz.getDeclaredAnnotation(Gets.class);
    Get[] annotations = repeatableAnnotation!=null ? repeatableAnnotation.value() : null;  
    for (Get annotation: list(clazz.getDeclaredAnnotation(Get.class), annotations)) { 
      GET(annotation.value(), clazz);
    }

    Puts repeatablePuts = clazz.getDeclaredAnnotation(Puts.class);
    Put[] puts = repeatablePuts!=null ? repeatablePuts.value() : null;  
    for (Put annotation: list(clazz.getDeclaredAnnotation(Put.class), puts)) { 
      PUT(annotation.value(), clazz);
    }

    Posts repeatablePosts = clazz.getDeclaredAnnotation(Posts.class);
    Post[] posts = repeatablePosts!=null ? repeatablePosts.value() : null;  
    for (Post annotation: list(clazz.getDeclaredAnnotation(Post.class), posts)) { 
      POST(annotation.value(), clazz);
    }

    Deletes repeatableDeletes = clazz.getDeclaredAnnotation(Deletes.class);
    Delete[] deletes = repeatableDeletes!=null ? repeatableDeletes.value() : null;  
    for (Delete annotation: list(clazz.getDeclaredAnnotation(Delete.class), deletes)) { 
      DELETE(annotation.value(), clazz);
    }
  }

  protected <T> List<T> list(T annotation, T[] annotations) {
    List<T> list = new ArrayList<>();
    if (annotation!=null) {
      list.add(annotation);
    }
    if (annotations!=null) {
      for (T e: annotations) {
        list.add(e);
      }
    }
    return list;
  }
}
