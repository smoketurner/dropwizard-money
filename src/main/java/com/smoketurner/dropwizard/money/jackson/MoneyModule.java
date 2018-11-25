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
package com.smoketurner.dropwizard.money.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.ser.Serializers;
import java.io.IOException;
import java.util.Locale;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import org.javamoney.moneta.format.CurrencyStyle;

public class MoneyModule extends Module {

  private static final CurrencyStyle DEFAULT_CURRENCY_STYLE = CurrencyStyle.SYMBOL;
  private final MonetaryAmountFormat formatter;

  public MoneyModule() {
    this(Locale.getDefault());
  }

  public MoneyModule(Locale locale) {
    this(locale, DEFAULT_CURRENCY_STYLE);
  }

  public MoneyModule(Locale locale, CurrencyStyle style) {
    this.formatter =
        MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(locale).set(style).build());
  }

  private class MoneyDeserializer extends JsonDeserializer<MonetaryAmount> {
    @Override
    public MonetaryAmount deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
      return formatter.parse(jp.getText());
    }
  }

  private class MoneyDeserializers extends Deserializers.Base {
    @Override
    public JsonDeserializer<?> findBeanDeserializer(
        JavaType type, DeserializationConfig config, BeanDescription beanDesc)
        throws JsonMappingException {
      if (MonetaryAmount.class.isAssignableFrom(type.getRawClass())) {
        return new MoneyDeserializer();
      }
      return super.findBeanDeserializer(type, config, beanDesc);
    }
  }

  private class MoneySerializer extends JsonSerializer<MonetaryAmount> {
    @Override
    public void serialize(MonetaryAmount value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException {
      jgen.writeString(formatter.format(value));
    }
  }

  private class MoneySerializers extends Serializers.Base {
    @Override
    public JsonSerializer<?> findSerializer(
        SerializationConfig config, JavaType type, BeanDescription beanDesc) {
      if (MonetaryAmount.class.isAssignableFrom(type.getRawClass())) {
        return new MoneySerializer();
      }
      return super.findSerializer(config, type, beanDesc);
    }
  }

  @Override
  public String getModuleName() {
    return "MoneyModule";
  }

  @Override
  public Version version() {
    return Version.unknownVersion();
  }

  @Override
  public void setupModule(SetupContext context) {
    context.addSerializers(new MoneySerializers());
    context.addDeserializers(new MoneyDeserializers());
  }
}
