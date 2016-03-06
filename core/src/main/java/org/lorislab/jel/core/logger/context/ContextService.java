/*
 * Copyright 2016 Andrej Petras.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lorislab.jel.core.logger.context;

/**
 * The context logger.
 *
 * @author Andrej Petras <andrej@lorislab.org>
 */
public interface ContextService {

    /**
     * Gets the start log string.
     *
     * @param context the logger context.
     * @return the logger string.
     */
    String getLogStart(Context context);

    /**
     * Gets the succeed log string.
     *
     * @param context the logger context.
     * @return the logger string.
     */
    String getLogSucceed(Context context);

    /**
     * Gets the failed log string.
     *
     * @param context the logger context.
     * @return the logger string.
     */
    String getLogFailed(Context context);
}
