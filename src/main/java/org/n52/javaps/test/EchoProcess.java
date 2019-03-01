package org.n52.javaps.test;
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


import java.util.List;

import org.apache.xmlbeans.XmlObject;
import org.n52.javaps.algorithm.annotation.Algorithm;
import org.n52.javaps.algorithm.annotation.BoundingBoxInput;
import org.n52.javaps.algorithm.annotation.BoundingBoxOutput;
import org.n52.javaps.algorithm.annotation.ComplexInput;
import org.n52.javaps.algorithm.annotation.ComplexOutput;
import org.n52.javaps.algorithm.annotation.Execute;
import org.n52.javaps.algorithm.annotation.LiteralInput;
import org.n52.javaps.algorithm.annotation.LiteralOutput;
import org.n52.javaps.io.data.binding.complex.GenericXMLDataBinding;
import org.n52.shetland.ogc.ows.OwsBoundingBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Algorithm(version = "1.0.0")
public class EchoProcess {

    private static final Logger log = LoggerFactory.getLogger(EchoProcess.class);

    private List<XmlObject> complexInput;
    private List<String> literalInput;

    private XmlObject complexOutput;
    private String literalOutput;

    private OwsBoundingBox boundingboxInput;
    private OwsBoundingBox boundingboxOutput;

    private int duration;

    @Execute
    public void echo() {

        log.debug("Running echo process");

        if (literalInput != null && literalInput.size() > 0){
            literalOutput = literalInput.get(0);
        } else{
            log.debug("No literal input");
        }

        if (complexInput != null && complexInput.size() > 0){
            complexOutput = complexInput.get(0);
        } else{
            log.debug("No complex input");
        }

        if (boundingboxInput != null){
            boundingboxOutput = boundingboxInput;
        } else{
            log.debug("No boundingbox input");
        }

        if(duration != 0) {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                log.error("Could not sleep for: " + duration, e);
            }
        }

        log.debug("Finished echo process, literal output is '{}', complex output is : {}", literalOutput, complexOutput);
    }

    @LiteralOutput(identifier = "literalOutput")
    public String getLiteralOutput() {
        return literalOutput;
    }

    @LiteralInput(identifier = "literalInput", minOccurs = 0, maxOccurs= 1)
    public void setLiteralInput(List<String> literalInput) {
        this.literalInput = literalInput;
    }

    @LiteralInput(identifier = "duration", minOccurs = 0, maxOccurs= 1)
    public void setLiteralInput(int duration) {
        this.duration = duration;
    }

    @BoundingBoxInput(defaultCRSString="EPSG:4326", minOccurs = 0, maxOccurs= 1, identifier = "boundingboxInput")
    public void setBoundingBox(OwsBoundingBox data){
        this.boundingboxInput = data;
    }

    @BoundingBoxOutput(defaultCRSString="EPSG:4326", identifier = "boundingboxOutput")
    public OwsBoundingBox getBoundingBox(){
        return this.boundingboxOutput;
    }

    @ComplexOutput(identifier="complexOutput", binding=GenericXMLDataBinding.class)
    public XmlObject getComplexOutput() {
        return complexOutput;
    }

    @ComplexInput(identifier="complexInput", minOccurs = 0, maxOccurs= 2, binding=GenericXMLDataBinding.class)
    public void setComplexInput(List<XmlObject> complexInput) {
        this.complexInput = complexInput;
    }

}
