/*
 * Copyright © 2018 Smoke Turner, LLC (contact@smoketurner.com)
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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javax.annotation.Nullable;
import org.javamoney.moneta.Money;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultColumnMapper;

/** A {@link ResultColumnMapper} to map {@link Money} objects. */
public class MoneyMapper implements ResultColumnMapper<Money> {

  private static final String DEFAULT_CURRENCY_CODE = "USD";
  private final Optional<String> currency;

  public MoneyMapper() {
    currency = Optional.of(DEFAULT_CURRENCY_CODE);
  }

  public MoneyMapper(String currencyCode) {
    currency = Optional.ofNullable(currencyCode);
  }

  @Nullable
  @Override
  public Money mapColumn(ResultSet r, int columnNumber, @Nullable StatementContext ctx)
      throws SQLException {
    return convertToMoney(r.getBigDecimal(columnNumber));
  }

  @Nullable
  @Override
  public Money mapColumn(ResultSet r, String columnLabel, @Nullable StatementContext ctx)
      throws SQLException {
    return convertToMoney(r.getBigDecimal(columnLabel));
  }

  @Nullable
  private Money convertToMoney(@Nullable BigDecimal decimal) {
    if (decimal == null) {
      return null;
    }
    return Money.of(decimal, currency.orElse(DEFAULT_CURRENCY_CODE));
  }
}
