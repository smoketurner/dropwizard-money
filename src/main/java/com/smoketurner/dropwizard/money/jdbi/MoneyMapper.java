package com.smoketurner.dropwizard.money.jdbi;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.javamoney.moneta.Money;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultColumnMapper;

/**
 * A {@link ResultColumnMapper} to map {@link Money} objects.
 */
public class MoneyMapper implements ResultColumnMapper<Money> {

    private static final String DEFAULT_CURRENCY_CODE = "USD";
    private final Optional<String> currency;

    public MoneyMapper() {
        currency = Optional.of(DEFAULT_CURRENCY_CODE);
    }

    public MoneyMapper(String currencyCode) {
        currency = Optional.ofNullable(currencyCode);
    }

    @Override
    public Money mapColumn(ResultSet r, int columnNumber, StatementContext ctx)
            throws SQLException {
        return convertToMoney(r.getBigDecimal(columnNumber));
    }

    @Override
    public Money mapColumn(ResultSet r, String columnLabel,
            StatementContext ctx) throws SQLException {
        return convertToMoney(r.getBigDecimal(columnLabel));
    }

    private Money convertToMoney(BigDecimal decimal) {
        if (decimal == null) {
            return null;
        }
        return Money.of(decimal, currency.orElse(DEFAULT_CURRENCY_CODE));
    }
}
