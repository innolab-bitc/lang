/*
 * Copyright 2017 The Mifos Initiative
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
package io.mifos.core.lang.validation;

import io.mifos.core.lang.validation.constraints.ValidIdentifier;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author Myrle Krantz
 */
public class ValidIdentifierTest {
  @Test
  public void validIdentifier()
  {
    final AnnotatedClass annotatedInstance = new AnnotatedClass("xxxx");
    Assert.assertTrue(isValid(annotatedInstance));
  }

  @Test
  public void nullIdentifier()
  {
    final AnnotatedClass annotatedInstance = new AnnotatedClass(null);
    Assert.assertFalse(isValid(annotatedInstance));
  }

  @Test
  public void emptyStringIdentifier()
  {
    final AnnotatedClass annotatedInstance = new AnnotatedClass("");
    Assert.assertFalse(isValid(annotatedInstance));
  }

  @Test
  public void tooShortStringIdentifier()
  {
    final AnnotatedClass annotatedInstance = new AnnotatedClass("x");
    Assert.assertFalse(isValid(annotatedInstance));
  }

  @Test
  public void tooLongStringIdentifier()
  {
    final AnnotatedClass annotatedInstance = new AnnotatedClass("012345");
    Assert.assertFalse(isValid(annotatedInstance));
  }

  @Test
  public void notEncodableStringIdentifier()
  {
    final AnnotatedClass annotatedInstance = new AnnotatedClass("x/y/z");
    Assert.assertFalse(isValid(annotatedInstance));
  }

  private boolean isValid(final AnnotatedClass annotatedInstance)
  {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    final Validator validator = factory.getValidator();
    final Set<ConstraintViolation<AnnotatedClass>> errors = validator.validate(annotatedInstance);

    return errors.size() == 0;
  }

  private static class AnnotatedClass {
    @ValidIdentifier(maxLength = 5)
    String applicationName;

    AnnotatedClass(final String applicationName) {
      this.applicationName = applicationName;
    }
  }
}
