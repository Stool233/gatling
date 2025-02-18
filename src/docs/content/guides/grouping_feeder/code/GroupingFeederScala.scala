/*
 * Copyright 2011-2021 GatlingCorp (https://gatling.io)
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

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GroupingFeederScala {
//#grouping-feeder
val records: Seq[Map[String, Any]] =
  csv("file.csv").readRecords

val recordsGroupedByUsername =
  records.groupBy(record => record("username").toString)

val groupedRecordsFeeder =
  recordsGroupedByUsername
    .values
    .iterator
    .map(groupedRecords => Map("userRecords" -> groupedRecords))

val chain =
  feed(groupedRecordsFeeder)
    .foreach("#{userRecords}", "record") {
      exec(http("request").get("${record.url}"))
    }
//#grouping-feeder
}
