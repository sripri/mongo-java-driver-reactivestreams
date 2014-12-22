/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.reactivestreams.client;

import com.mongodb.Function;
import org.reactivestreams.Publisher;

/**
 * A wrapper over a {@link Publisher} that also provides a map method for transformations.
 *
 * @param <T> the type of item emitted by this publisher
 */
public interface MongoPublisher<T> extends Publisher<T> {

    /**
     * See {@link Publishers#map(Publisher, Function)}.
     *
     * @param function the transformation
     * @param <O> the type of transformed item
     * @return the transformed publisher
     */
    <O> MongoPublisher<O> map(Function<? super T, ? extends O> function);

}