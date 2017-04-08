/**
 * Copyright 2017 Smoke Turner, LLC.
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

import org.javamoney.moneta.FastMoney;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

/**
 * An {@link ArgumentFactory} for {@link FastMoney} arguments.
 */
public class FastMoneyArgumentFactory implements ArgumentFactory<FastMoney> {

    @Override
    public boolean accepts(Class<?> expectedType, Object value,
            StatementContext ctx) {
        return value instanceof FastMoney;
    }

    @Override
    public Argument build(Class<?> expectedType, FastMoney value,
            StatementContext ctx) {
        return new FastMoneyArgument(value);
    }
}
