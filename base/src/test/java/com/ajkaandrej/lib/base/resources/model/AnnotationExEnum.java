/*
 * Copyright 2012 Andrej Petras <andrej@ajka-andrej.com>.
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
package com.ajkaandrej.lib.base.resources.model;

import com.ajkaandrej.lib.base.resources.annotations.ResourceKey;

/**
 * The resource key annotation test enumeration.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
@ResourceKey(keyPrefix = "test", bundleName = "com.ajkaandrej.lib.base.resources.model.TestBundle")
public enum AnnotationExEnum {

    /**
     * The test key.
     */
    TEST,
    /**
     * The test name key.
     */
    TEST_NAME,
    /**
     * The test parameter key.
     */
    TEST_PARAM;
}
