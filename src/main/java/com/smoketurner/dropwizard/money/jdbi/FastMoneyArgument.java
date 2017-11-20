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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import javax.annotation.Nullable;
import org.javamoney.moneta.FastMoney;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

/**
 * An {@link Argument} for {@link FastMoney} objects.
 */
public class FastMoneyArgument implements Argument {

    @Nullable
    private final FastMoney value;

    FastMoneyArgument(@Nullable final FastMoney value) {
        this.value = value;
    }

    @Override
    public void apply(int position, PreparedStatement statement,
            StatementContext ctx) throws SQLException {
        if (value != null) {
            statement.setLong(position, value.getNumber().longValue());
        } else {
            statement.setNull(position, Types.BIGINT);
        }
    }
}
