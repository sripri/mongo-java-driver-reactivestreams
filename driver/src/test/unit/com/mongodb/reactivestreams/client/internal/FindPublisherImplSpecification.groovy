/*
 * Copyright 2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.reactivestreams.client.internal

import com.mongodb.CursorType
import com.mongodb.async.client.FindIterable
import com.mongodb.client.model.Collation
import org.bson.Document
import org.reactivestreams.Subscriber
import spock.lang.Specification

import java.util.concurrent.TimeUnit

class FindPublisherImplSpecification extends Specification {

    def 'should call the underlying wrapped methods'() {
        given:
        def sort = new Document('sort', 1)
        def modifiers = new Document('modifier', 1)
        def projection = new Document('projection', 1)
        def collation = Collation.builder().locale('en').build()
        def batchSize = 100

        def subscriber = Stub(Subscriber) {
            onSubscribe(_) >> { args -> args[0].request(batchSize) }
        }

        def wrapped = Mock(FindIterable)
        def publisher = new FindPublisherImpl<Document>(wrapped)

        when:
        publisher.subscribe(subscriber)

        then:
        1 * wrapped.batchCursor(_)

        when: 'setting options'
        publisher = publisher
                .sort(sort)
                .modifiers(modifiers)
                .projection(projection)
                .maxTime(1, TimeUnit.SECONDS)
                .maxAwaitTime(2, TimeUnit.SECONDS)
                .limit(100)
                .skip(10)
                .cursorType(CursorType.NonTailable)
                .oplogReplay(false)
                .noCursorTimeout(false)
                .partial(false)
                .collation(collation)

        then:
        1 * wrapped.sort(sort) >> wrapped
        1 * wrapped.modifiers(modifiers) >> wrapped
        1 * wrapped.projection(projection) >> wrapped
        1 * wrapped.maxTime(1, TimeUnit.SECONDS) >> wrapped
        1 * wrapped.maxAwaitTime(2, TimeUnit.SECONDS) >> wrapped
        1 * wrapped.limit(100) >> wrapped
        1 * wrapped.skip(10) >> wrapped
        1 * wrapped.cursorType(CursorType.NonTailable) >> wrapped
        1 * wrapped.oplogReplay(false) >> wrapped
        1 * wrapped.noCursorTimeout(false) >> wrapped
        1 * wrapped.partial(false) >> wrapped
        1 * wrapped.collation(collation) >> wrapped

        when:
        publisher.subscribe(subscriber)

        then:
        1 * wrapped.batchSize(batchSize) >> wrapped
        1 * wrapped.batchCursor(_)

        when:
        publisher.first().subscribe(subscriber)

        then:
        1 * wrapped.first(_)
    }

}
