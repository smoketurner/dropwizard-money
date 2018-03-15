/**
 * Copyright 2018 Smoke Turner, LLC.
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
package com.smoketurner.dropwizard.money.jersey;

import static org.assertj.core.api.Assertions.assertThat;
import javax.money.MonetaryException;
import javax.ws.rs.core.Response;
import org.junit.Test;

public class MonetaryExceptionMapperTest {

    private final MonetaryExceptionMapper mapper = new MonetaryExceptionMapper();

    @Test
    public void testToResponse() {
        final Response response = mapper
                .toResponse(new MonetaryException("error"));
        assertThat(response.getStatus()).isEqualTo(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }
}
