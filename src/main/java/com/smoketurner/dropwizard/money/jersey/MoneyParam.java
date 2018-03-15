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
package com.smoketurner.dropwizard.money.jersey;

import org.javamoney.moneta.Money;
import io.dropwizard.jersey.params.AbstractParam;

public class MoneyParam extends AbstractParam<Money> {

    public MoneyParam(String input) {
        super(input);
    }

    @Override
    protected Money parse(String input) throws Exception {
        return Money.parse(input);
    }
}
