/*******************************************************************************
 * Copyright (c) 2013 GigaSpaces Technologies Ltd. All rights reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.cloudifysource.rest.validators;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.cloudifysource.dsl.internal.CloudifyMessageKeys;
import org.junit.Assert;
import org.junit.Test;

public class ValidateOverridesFileSizeTest {

    private static final long TEST_FILE_SIZE_LIMIT = 3;

    @Test
    public void testInstallServiceOverridesFileSizeLimitExeeded() {
    	File serviceOverridesFile = null;
    	try {
    		serviceOverridesFile = File.createTempFile("serviceOverrides", "");
    		FileUtils.writeStringToFile(serviceOverridesFile, "I'm longer than 3 bytes !");
    	} catch (IOException e) {
    		Assert.fail(e.getLocalizedMessage());
    	}
    	
    	ValidateOverridesFileSize validator = new ValidateOverridesFileSize();
    	validator.setServiceOverridesFileSizeLimit(TEST_FILE_SIZE_LIMIT);
    	InstallServiceValidationContext validationContext = new InstallServiceValidationContext();
    	validationContext.setServiceOverridesFile(serviceOverridesFile);
		ValidatorsTestsUtils.validate(
				validator, 
				validationContext , 
				CloudifyMessageKeys.SERVICE_OVERRIDES_SIZE_LIMIT_EXCEEDED.getName());
    	
		serviceOverridesFile.delete();
    }
    
    @Test
    public void testInstallApplicationOverridesFileSizeLimitExeeded() {
    	
    	File applicationOverridesFile = null;
    	try {
    		applicationOverridesFile = File.createTempFile("applicationOverrides", "");
    		FileUtils.writeStringToFile(applicationOverridesFile, "I'm longer than 3 bytes !");
    	} catch (IOException e) {
    		Assert.fail(e.getLocalizedMessage());
    	}
        
    	ValidateOverridesFileSize validator = new ValidateOverridesFileSize();
    	validator.setApplicationOverridesFileSizeLimit(TEST_FILE_SIZE_LIMIT);
        InstallApplicationValidationContext context = new InstallApplicationValidationContext();
        context.setApplicationOverridesFile(applicationOverridesFile);
		
        ValidatorsTestsUtils.validate(
        		validator, 
        		context , 
        		CloudifyMessageKeys.APPLICATION_OVERRIDES_SIZE_LIMIT_EXCEEDED.getName());
        
		applicationOverridesFile.delete();
    }

}
