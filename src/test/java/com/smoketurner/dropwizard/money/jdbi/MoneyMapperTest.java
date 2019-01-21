/*
 * Copyright © 2019 Smoke Turner, LLC (github@smoketurner.com)
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
package com.smoketurner.dropwizard.money.jdbi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.ResultSet;
import org.javamoney.moneta.Money;
import org.junit.Test;

public class MoneyMapperTest {

  private final ResultSet resultSet = mock(ResultSet.class);

  @Test
  public void mapColumnByName() throws Exception {
    when(resultSet.getBigDecimal("amount")).thenReturn(new BigDecimal("1234.53"));

    final Money actual = new MoneyMapper().mapColumn(resultSet, "amount", null);

    assertThat(actual).isEqualTo(Money.of(new BigDecimal("1234.53"), "USD"));
  }

  @Test
  public void mapColumnByName_TimestampIsNull() throws Exception {
    when(resultSet.getBigDecimal("amount")).thenReturn(null);

    final Money actual = new MoneyMapper().mapColumn(resultSet, "amount", null);

    assertThat(actual).isNull();
  }

  @Test
  public void mapColumnByIndex() throws Exception {
    when(resultSet.getBigDecimal(1)).thenReturn(new BigDecimal("1234.53"));

    final Money actual = new MoneyMapper().mapColumn(resultSet, 1, null);

    assertThat(actual).isEqualTo(Money.of(new BigDecimal("1234.53"), "USD"));
  }

  @Test
  public void mapColumnByIndex_TimestampIsNull() throws Exception {
    when(resultSet.getBigDecimal(1)).thenReturn(null);

    final Money actual = new MoneyMapper().mapColumn(resultSet, 1, null);

    assertThat(actual).isNull();
  }
}
