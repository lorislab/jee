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
package com.ajkaandrej.lib.base.resources;

import com.ajkaandrej.lib.base.resources.model.AnnotationEnum;
import com.ajkaandrej.lib.base.resources.model.AnnotationExEnum;
import com.ajkaandrej.lib.base.resources.model.MissingEnum;
import com.ajkaandrej.lib.base.resources.model.SimpleEnum;
import java.util.Locale;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The resource manager test.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public class ResourceManagerTest {

    /**
     * The result text.
     */
    private static final String RESULT = "Succeed";
    /**
     * The result parameter text.
     */
    private static final String RESULT_PARAM = "Succeed 1 test.";

    /**
     * The simple enumeration test.
     */
    @Test
    public void simpleEnumTest() {
        String value = ResourceManager.getString(SimpleEnum.TEST, Locale.ENGLISH);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, RESULT);
        value = ResourceManager.getString(SimpleEnum.TEST_NAME, Locale.ENGLISH);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, RESULT);
    }

    /**
     * The resource key annotation test.
     */
    @Test
    public void annotationExEnumTest() {
        String value = ResourceManager.getString(AnnotationExEnum.TEST, Locale.ENGLISH);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, RESULT);
        value = ResourceManager.getString(AnnotationExEnum.TEST_NAME, Locale.ENGLISH);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, RESULT);
    }

    /**
     * The resource key annotation with parameter test.
     */
    @Test
    public void annotationExEnumParamTest() {
        String value = ResourceManager.getMessage(AnnotationExEnum.TEST_PARAM, Locale.ENGLISH, 1, "test");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, RESULT_PARAM);
    }

    /**
     * The resource key annotation test.
     */
    @Test
    public void annotationEnumTest() {
        String value = ResourceManager.getString(AnnotationEnum.TEST, Locale.ENGLISH);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, RESULT);
        value = ResourceManager.getString(AnnotationEnum.TEST_NAME, Locale.ENGLISH);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, RESULT);
    }

    /**
     * The missing resource test.
     */
    @Test
    public void missingEnumTest() {
        String value = ResourceManager.getString(MissingEnum.TEST, Locale.ENGLISH);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, MissingEnum.TEST.name());
        value = ResourceManager.getString(MissingEnum.TEST_NAME, Locale.ENGLISH);
        Assert.assertNotNull(value);
        Assert.assertEquals(value, MissingEnum.TEST_NAME.name());
    }
}
