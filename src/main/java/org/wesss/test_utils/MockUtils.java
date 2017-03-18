package org.wesss.test_utils;

import static org.mockito.Mockito.mock;

public class MockUtils {

    @SuppressWarnings("unchecked")
    static <T> T genericMock(Class<? super T> classToMock) {
        return (T)mock(classToMock);
    }
}
