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
package com.smoketurner.dropwizard.money.jackson;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MoneyModuleTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mapper.registerModule(new MoneyModule());
    }

    @Test
    public void canDeserializeAMoney() throws Exception {
        assertThat(mapper.readValue("\"USD 1234.53\"", Money.class))
                .isEqualTo(Money.of(new BigDecimal("1234.53"), "USD"));
    }

    @Test
    public void canSerializeMoney() throws Exception {
        assertThat(mapper
                .writeValueAsString(Money.of(new BigDecimal("1234.53"), "USD")))
                        .isEqualTo("\"USD 1234.53\"");
    }
}
