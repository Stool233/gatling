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

package io.gatling.charts.template

import io.gatling.charts.stats.RunInfo
import io.gatling.commons.shared.unstable.model.stats.assertion.AssertionResult

private[charts] final class AssertionsJsonTemplate(runInfo: RunInfo, scenarioNames: List[String], assertionResults: List[AssertionResult]) {
  private[this] def print(assertionResult: AssertionResult): String = {
    import assertionResult._
    s"""{
  "path": "${assertion.path.printable}",
  "target": "${assertion.target.printable}",
  "condition": "${assertion.condition.printable}",
  "expectedValues": [${assertion.condition.values.mkString(",")}],
  "result": $result,
  "message": "$message",
  "actualValue": [${actualValue.getOrElse(-1.0)}]
}"""
  }

  def getOutput: String =
    s"""{
  "simulation": "${runInfo.simulationClassName}",
  "simulationId": "${runInfo.simulationId}",
  "start": ${runInfo.injectStart},
  "description": "${runInfo.runDescription}",
  "scenarios": [${scenarioNames.map(n => s""""$n"""").mkString(", ")}],
  "assertions": [
${assertionResults.map(print).mkString(",\n")}
  ]
}"""
}
