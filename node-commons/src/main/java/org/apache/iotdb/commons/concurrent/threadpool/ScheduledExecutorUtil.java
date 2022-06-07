/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.iotdb.commons.concurrent.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorUtil {

  private static final Logger logger = LoggerFactory.getLogger(ScheduledExecutorUtil.class);

  /**
   * A safe wrapper method to make sure the exception thrown by the previous running will not affect
   * the next one. Please reference the javadoc of {@link
   * ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long, TimeUnit)} for more details.
   *
   * @param executor the ScheduledExecutorService instance.
   * @param command same parameter in {@link ScheduledExecutorService#scheduleAtFixedRate(Runnable,
   *     long, long, TimeUnit)}.
   * @param initialDelay same parameter in {@link
   *     ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long, TimeUnit)}.
   * @param period same parameter in {@link ScheduledExecutorService#scheduleAtFixedRate(Runnable,
   *     long, long, TimeUnit)}.
   * @param unit same parameter in {@link ScheduledExecutorService#scheduleAtFixedRate(Runnable,
   *     long, long, TimeUnit)}.
   * @return the same return value of {@link ScheduledExecutorService#scheduleAtFixedRate(Runnable,
   *     long, long, TimeUnit)}.
   */
  @SuppressWarnings("unsafeThreadSchedule")
  public static ScheduledFuture<?> safelyScheduleAtFixedRate(
      ScheduledExecutorService executor,
      Runnable command,
      long initialDelay,
      long period,
      TimeUnit unit) {
    return executor.scheduleAtFixedRate(
        () -> {
          try {
            command.run();
          } catch (Throwable t) {
            logger.error("Schedule task failed", t);
          }
        },
        initialDelay,
        period,
        unit);
  }

  /**
   * A safe wrapper method to make sure the exception thrown by the previous running will not affect
   * the next one. Please reference the javadoc of {@link
   * ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)} for more
   * details.
   *
   * @param executor the ScheduledExecutorService instance.
   * @param command same parameter in {@link
   *     ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)}.
   * @param initialDelay same parameter in {@link
   *     ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)}.
   * @param delay same parameter in {@link ScheduledExecutorService#scheduleWithFixedDelay(Runnable,
   *     long, long, TimeUnit)}.
   * @param unit same parameter in {@link ScheduledExecutorService#scheduleWithFixedDelay(Runnable,
   *     long, long, TimeUnit)}.
   * @return the same return value of {@link
   *     ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)}.
   */
  @SuppressWarnings("unsafeThreadSchedule")
  public static ScheduledFuture<?> safelyScheduleWithFixedDelay(
      ScheduledExecutorService executor,
      Runnable command,
      long initialDelay,
      long delay,
      TimeUnit unit) {
    return executor.scheduleWithFixedDelay(
        () -> {
          try {
            command.run();
          } catch (Throwable t) {
            logger.error("Schedule task failed", t);
          }
        },
        initialDelay,
        delay,
        unit);
  }
}
