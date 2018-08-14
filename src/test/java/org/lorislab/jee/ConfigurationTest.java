/*
 * Copyright 2018 andrej.
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
package org.lorislab.jee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrej
 */
public class ConfigurationTest {
    
    @Test
    public void msgTraceStartTest() {
        Object tmp = Configuration.msgTraceStart(1,2,3);
        Assertions.assertEquals("1->2:3()", tmp.toString());
    }
    
    @Test
    public void msgTraceEndTest() {
        Object tmp = Configuration.msgTraceEnd(1,2,3,4);
        Assertions.assertEquals("1-->2:3() 4", tmp.toString());
    }    
}
