package com.smoketurner.dropwizard.money.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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

    @Override
    public FastMoney mapColumn(ResultSet r, int columnNumber,
            StatementContext ctx) throws SQLException {
        return convertToFastMoney(r.getLong(columnNumber));
    }

    @Override
    public FastMoney mapColumn(ResultSet r, String columnLabel,
            StatementContext ctx) throws SQLException {
        return convertToFastMoney(r.getLong(columnLabel));
    }

    private FastMoney convertToFastMoney(Long amount) {
        if (amount == null) {
            return null;
        }
        return FastMoney.of(amount, currency.orElse(DEFAULT_CURRENCY_CODE));
    }
}
