package org.gh.onboarding;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.TestPropertySource;

/**
 * Abstract layer for Service JUnit tests
 */
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations="classpath:test.properties")
public abstract class BaseServiceTest {
}
