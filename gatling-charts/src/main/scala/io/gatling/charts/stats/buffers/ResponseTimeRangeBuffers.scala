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

package io.gatling.charts.stats.buffers

import scala.collection.mutable

import io.gatling.charts.stats.{ GroupRecord, RequestRecord }
import io.gatling.commons.shared.unstable.model.stats.Group
import io.gatling.commons.stats.{ KO, Status }

private[stats] trait ResponseTimeRangeBuffers {
  protected def lowerBound: Int
  protected def higherBound: Int

  val responseTimeRangeBuffers: mutable.Map[BufferKey, ResponseTimeRangeBuffer] = mutable.Map.empty

  def getResponseTimeRangeBuffers(requestName: Option[String], group: Option[Group]): ResponseTimeRangeBuffer =
    responseTimeRangeBuffers.getOrElseUpdate(BufferKey(requestName, group, None), new ResponseTimeRangeBuffer)

  def updateResponseTimeRangeBuffer(record: RequestRecord): Unit = {
    import record._
    getResponseTimeRangeBuffers(Some(name), group).update(responseTime, status)
    getResponseTimeRangeBuffers(None, None).update(responseTime, status)
  }

  def updateGroupResponseTimeRangeBuffer(record: GroupRecord): Unit =
    getResponseTimeRangeBuffers(None, Some(record.group)).update(record.duration, record.status)

  final class ResponseTimeRangeBuffer {
    var low: Int = 0
    var middle: Int = 0
    var high: Int = 0
    var ko: Int = 0

    def update(time: Int, status: Status): Unit =
      if (status == KO) ko += 1
      else if (time < lowerBound) low += 1
      else if (time > higherBound) high += 1
      else middle += 1
  }
}
