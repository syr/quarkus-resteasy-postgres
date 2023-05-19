package org.acme;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeCustomInterceptorIT extends CustomInterceptorTest {

    // Execute the same tests but in native mode.
}