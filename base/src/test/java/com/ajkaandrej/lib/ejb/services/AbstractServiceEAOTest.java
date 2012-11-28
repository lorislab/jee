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
package com.ajkaandrej.lib.ejb.services;

import com.ajkaandrej.lib.ejb.services.model.TestEAO;
import com.ajkaandrej.lib.ejb.services.model.TestServiceEAO;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * The abstract EAO test.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public class AbstractServiceEAOTest {

    /**
     * The create new EAO service test.
     */
    @Test
    public void createAbstractServiceEAOTest() throws Exception {
        TestServiceEAO service = new TestServiceEAO();
        Assert.assertNotNull(service);

        service.init();
        Assert.assertNotNull(service.getBaseEAO());

        TestEAO eao = new TestEAO();
        Assert.assertNotNull(eao);
    }
}
