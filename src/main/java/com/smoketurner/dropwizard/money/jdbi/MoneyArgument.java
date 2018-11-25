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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import javax.annotation.Nullable;
import org.javamoney.moneta.Money;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

/** An {@link Argument} for {@link Money} objects. */
public class MoneyArgument implements Argument {

  @Nullable private final Money value;

  MoneyArgument(@Nullable final Money value) {
    this.value = value;
  }

  @Override
  public void apply(int position, PreparedStatement statement, StatementContext ctx)
      throws SQLException {
    if (value != null) {
      statement.setBigDecimal(position, value.getNumberStripped());
    } else {
      statement.setNull(position, Types.NUMERIC);
    }
  }
}
