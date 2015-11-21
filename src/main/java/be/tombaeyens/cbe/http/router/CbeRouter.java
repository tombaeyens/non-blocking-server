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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.netty.handler.codec.http.router.Router;
import be.tombaeyens.cbe.http.framework.Get;
import be.tombaeyens.cbe.http.framework.Gets;
import be.tombaeyens.cbe.http.framework.Post;
import be.tombaeyens.cbe.http.framework.Posts;
import be.tombaeyens.cbe.http.framework.RequestHandler;
import be.tombaeyens.cbe.http.requests.CollectionPost;
import be.tombaeyens.cbe.http.requests.Oops;


/**
 * @author Tom Baeyens
 */
public class CbeRouter extends Router<Class< ? extends RequestHandler>> {

  public CbeRouter() {
    register(CollectionPost.class);
    notFound(Oops.class);
  }

  private void register(Class< ? extends RequestHandler> clazz) {
    List<Post> posts = new ArrayList<>();
    Posts postsAnnotation = clazz.getDeclaredAnnotation(Posts.class);
    if (postsAnnotation!=null) {
      posts.addAll(Arrays.asList(postsAnnotation.value()));
    }
    Post postAnnotation = clazz.getDeclaredAnnotation(Post.class);
    if (postAnnotation!=null) {
      posts.add(postAnnotation);
    }
    for (Post post: posts) {
      POST(post.value(), clazz);
    }
    
    List<Get> gets = new ArrayList<>();
    Gets getsAnnotation = clazz.getDeclaredAnnotation(Gets.class);
    if (getsAnnotation!=null) {
      gets.addAll(Arrays.asList(getsAnnotation.value()));
    }
    Get getAnnotation = clazz.getDeclaredAnnotation(Get.class);
    if (getAnnotation!=null) {
      gets.add(getAnnotation);
    }
    for (Get get: gets) {
      GET(get.value(), clazz);
    }
  }
}
