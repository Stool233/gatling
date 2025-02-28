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

package io.gatling.core.check

import io.gatling.commons.validation.Validation
import io.gatling.core.session.{ Expression, Session }

final class ChecksumCheck[R](wrapped: Check[R], val algorithm: String) extends Check[R] {
  override def check(response: R, session: Session, preparedCache: Check.PreparedCache): Validation[CheckResult] =
    wrapped.check(response, session, preparedCache)

  override def checkIf(condition: (R, Session) => Validation[Boolean]): Check[R] =
    new ChecksumCheck(wrapped.checkIf(condition), algorithm)

  override def checkIf(condition: Expression[Boolean]): Check[R] =
    new ChecksumCheck(wrapped.checkIf(condition), algorithm)
}
