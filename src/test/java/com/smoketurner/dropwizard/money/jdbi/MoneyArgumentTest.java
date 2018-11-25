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
package com.smoketurner.dropwizard.money.jdbi;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Types;
import org.javamoney.moneta.Money;
import org.junit.Test;
import org.skife.jdbi.v2.StatementContext;

public class MoneyArgumentTest {

  private final PreparedStatement statement = mock(PreparedStatement.class);
  private final StatementContext context = mock(StatementContext.class);

  @Test
  public void apply() throws Exception {
    Money money = Money.of(new BigDecimal("1234.53"), "USD");

    new MoneyArgument(money).apply(1, statement, context);

    verify(statement).setBigDecimal(1, new BigDecimal("1234.53"));
  }

  @Test
  public void apply_ValueIsNull() throws Exception {
    new MoneyArgument(null).apply(1, statement, context);

    verify(statement).setNull(1, Types.NUMERIC);
  }
}
