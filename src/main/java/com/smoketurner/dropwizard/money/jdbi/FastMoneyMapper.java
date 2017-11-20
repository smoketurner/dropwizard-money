package com.smoketurner.dropwizard.money.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javax.annotation.Nullable;
import org.javamoney.moneta.FastMoney;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultColumnMapper;

/**
 * A {@link ResultColumnMapper} to map {@link FastMoney} objects.
 */
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
    public FastMoney mapColumn(ResultSet r, int columnNumber,
            @Nullable StatementContext ctx) throws SQLException {
        return convertToFastMoney(r.getLong(columnNumber));
    }

    @Nullable
    @Override
    public FastMoney mapColumn(ResultSet r, String columnLabel,
            @Nullable StatementContext ctx) throws SQLException {
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
