/**
 * Copyright 2018 Smoke Turner, LLC.
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
 * limitations under the License.
 */
package com.smoketurner.dropwizard.money.jdbi;

import org.javamoney.moneta.Money;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

/**
 * An {@link ArgumentFactory} for {@link Money} arguments.
 */
public class MoneyArgumentFactory implements ArgumentFactory<Money> {

    @Override
    public boolean accepts(Class<?> expectedType, Object value,
            StatementContext ctx) {
        return value instanceof Money;
    }

    @Override
    public Argument build(Class<?> expectedType, Money value,
            StatementContext ctx) {
        return new MoneyArgument(value);
    }
}
