package com.smoketurner.dropwizard.money.jdbi;

import org.javamoney.moneta.Money;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

/**
 * An {@link ArgumentFactory} for {@link Money} arguments.
 */
public class MoneyArgumentFactory implements ArgumentFactory<Money> {

    @Override
    public boolean accepts(Class<?> expectedType, Object value,
            StatementContext ctx) {
        return value instanceof Money;
    }

    @Override
    public Argument build(Class<?> expectedType, Money value,
            StatementContext ctx) {
        return new MoneyArgument(value);
    }
}
