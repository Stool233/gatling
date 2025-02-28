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

package io.gatling.http.action.ws.fsm

import io.gatling.core.action.Action
import io.gatling.core.session.Session

final class WsInitState(fsm: WsFsm) extends WsState(fsm) {
  override protected def remainingReconnects: Int = fsm.httpProtocol.wsPart.maxReconnects

  override def onPerformInitialConnect(session: Session, initialConnectNext: Action): NextWsState =
    WsConnectingState.gotoConnecting(fsm, session, Left(initialConnectNext), remainingReconnects)
}
