/**
 * Copyright 2011-2013 eBusiness Information, Groupe Excilys (www.excilys.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.core.action

import akka.actor.Props
import io.gatling.core.result.message.{ End, ScenarioMessage }
import io.gatling.core.result.terminator.Terminator
import io.gatling.core.result.writer.DataWriter
import io.gatling.core.session.Session
import io.gatling.core.util.TimeHelper.nowMillis

object UserEnd {

	val userEnd = system.actorOf(Props(new UserEnd))
}

class UserEnd extends Action {

	def execute(session: Session) {

		DataWriter.tell(ScenarioMessage(session.scenarioName, session.userId, End, session.startDate, nowMillis))
		logger.info(s"End user #${session.userId}")

		Terminator.endUser
	}
}