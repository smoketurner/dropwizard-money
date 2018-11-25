/*
 * Copyright © 2018 Smoke Turner, LLC (github@smoketurner.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smoketurner.dropwizard.money.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Locale;
import org.javamoney.moneta.Money;
import org.junit.Test;

public class MoneyModuleTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void canDeserializeAMoney() throws Exception {
    mapper.registerModule(new MoneyModule());
    assertThat(mapper.readValue("\"1,234.53\"", Money.class))
        .isEqualTo(Money.of(new BigDecimal("1234.53"), "USD"));
  }

  @Test
  public void canDeserializeAMoneyEUR() throws Exception {
    mapper.registerModule(new MoneyModule(Locale.GERMAN));
    assertThat(mapper.readValue("\"1.234,53 €\"", Money.class))
        .isEqualTo(Money.of(new BigDecimal("1234.53"), "EUR"));
  }

  @Test
  public void canSerializeMoney() throws Exception {
    mapper.registerModule(new MoneyModule());
    assertThat(mapper.writeValueAsString(Money.of(new BigDecimal("1234.53"), "USD")))
        .isEqualTo("\"$1,234.53\"");
  }

  @Test
  public void canSerializeMoneyEUR() throws Exception {
    mapper.registerModule(new MoneyModule(Locale.GERMAN));
    assertThat(mapper.writeValueAsString(Money.of(new BigDecimal("9375.59"), "EUR")))
        .isEqualTo("\"9.375,59 €\"");
  }
}
