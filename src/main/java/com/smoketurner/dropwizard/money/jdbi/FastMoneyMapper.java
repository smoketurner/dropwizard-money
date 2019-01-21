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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javax.annotation.Nullable;
import org.javamoney.moneta.FastMoney;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultColumnMapper;

/** A {@link ResultColumnMapper} to map {@link FastMoney} objects. */
public class FastMoneyMapper implements ResultColumnMapper<FastMoney> {

  private static final String DEFAULT_CURRENCY_CODE = "USD";
  private final Optional<String> currency;

  public FastMoneyMapper() {
    this(DEFAULT_CURRENCY_CODE);
  }

  public FastMoneyMapper(String currencyCode) {
    currency = Optional.ofNullable(currencyCode);
  }

  @Nullable
  @Override
  public FastMoney mapColumn(ResultSet r, int columnNumber, @Nullable StatementContext ctx)
      throws SQLException {
    return convertToFastMoney(r.getLong(columnNumber));
  }

  @Nullable
  @Override
  public FastMoney mapColumn(ResultSet r, String columnLabel, @Nullable StatementContext ctx)
      throws SQLException {
    return convertToFastMoney(r.getLong(columnLabel));
  }

  @Nullable
  private FastMoney convertToFastMoney(@Nullable Long amount) {
    if (amount == null) {
      return null;
    }
    return FastMoney.of(amount, currency.orElse(DEFAULT_CURRENCY_CODE));
  }
}
