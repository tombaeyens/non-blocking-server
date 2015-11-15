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
import be.tombaeyens.cbe.http.framework.RequestHandler;
import be.tombaeyens.cbe.http.requests.HelloPost;
import be.tombaeyens.cbe.http.requests.Oops;


/**
 * @author Tom Baeyens
 */
public class CbeRouter extends Router<Class< ? extends RequestHandler>> {

  public CbeRouter() {
    POST("/articles/:id", HelloPost.class);
    notFound(Oops.class);
  }
}
