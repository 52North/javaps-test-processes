/*
 * Copyright 2016 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.n52.javaps.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.n52.javaps.algorithm.annotation.Algorithm;
import org.n52.javaps.algorithm.annotation.ComplexInput;
import org.n52.javaps.algorithm.annotation.ComplexOutput;
import org.n52.javaps.algorithm.annotation.Execute;
import org.n52.javaps.io.GenericFileData;
import org.n52.javaps.io.data.binding.complex.GenericFileDataBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Algorithm(version = "1.1.0", title="for testing multiple inputs by reference")
public class MultiReferenceInputAlgorithm {

    private static Logger LOGGER = LoggerFactory.getLogger(MultiReferenceInputAlgorithm.class);

    public MultiReferenceInputAlgorithm() {
        super();
    }

    private GenericFileData result;
    private List<GenericFileData> data;

    @ComplexOutput(identifier = "result", binding = GenericFileDataBinding.class)
    public GenericFileData getResult() {
        return result;
    }

    @ComplexInput(identifier = "data", binding = GenericFileDataBinding.class, minOccurs=1, maxOccurs=2)
    public void setData(List<GenericFileData> data) {
        this.data = data;
    }

    @Execute
    public void runProcess() {

        GenericFileData gfd = data.get(0);

        File f = gfd.getBaseFile(false);

        try {
            result = new GenericFileData(f, gfd.getMimeType());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
