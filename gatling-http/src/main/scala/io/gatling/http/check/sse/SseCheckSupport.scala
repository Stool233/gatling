/*
 * Copyright 2011-2022 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gatling.http.check.sse

import io.gatling.core.check._
import io.gatling.core.check.jmespath.JmesPathCheckType
import io.gatling.core.check.jsonpath.JsonPathCheckType
import io.gatling.core.check.regex.RegexCheckType
import io.gatling.core.check.string.BodyStringCheckType
import io.gatling.core.check.substring.SubstringCheckType
import io.gatling.core.json.JsonParsers

import com.fasterxml.jackson.databind.JsonNode

trait SseCheckSupport {
  implicit def checkBuilder2SseCheck[T, P](checkBuilder: CheckBuilder[T, P])(implicit materializer: CheckMaterializer[T, SseCheck, String, P]): SseCheck =
    checkBuilder.build(materializer)

  implicit def validate2SseCheck[T, P, X](
      validate: CheckBuilder.Validate[T, P, X]
  )(implicit materializer: CheckMaterializer[T, SseCheck, String, P]): SseCheck =
    validate.exists

  implicit def find2SseCheck[T, P, X](
      find: CheckBuilder.Find[T, P, X]
  )(implicit materializer: CheckMaterializer[T, SseCheck, String, P]): SseCheck =
    find.find.exists

  implicit def sseJsonPathCheckMaterializer(implicit jsonParsers: JsonParsers): CheckMaterializer[JsonPathCheckType, SseCheck, String, JsonNode] =
    SseCheckMaterializer.jsonPath(jsonParsers)

  implicit def sseJmesPathCheckMaterializer(implicit jsonParsers: JsonParsers): CheckMaterializer[JmesPathCheckType, SseCheck, String, JsonNode] =
    SseCheckMaterializer.jmesPath(jsonParsers)

  implicit val sseRegexCheckMaterializer: CheckMaterializer[RegexCheckType, SseCheck, String, String] = SseCheckMaterializer.Regex

  implicit val sseSubstringCheckMaterializer: CheckMaterializer[SubstringCheckType, SseCheck, String, String] =
    SseCheckMaterializer.Substring

  implicit val sseBodyStringCheckMaterializer: CheckMaterializer[BodyStringCheckType, SseCheck, String, String] =
    SseCheckMaterializer.BodyString

  implicit val sseUntypedCheckIfMaker: UntypedCheckIfMaker[SseCheck] = _.checkIf(_)

  implicit val sseTypedCheckIfMaker: TypedCheckIfMaker[String, SseCheck] = _.checkIf(_)
}
