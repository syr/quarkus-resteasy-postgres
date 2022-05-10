package org.acme;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.entity.Download;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class DownloadTest {

    /*
    e.g. from https://quarkus.io/guides/hibernate-orm-panache#mocking
    We can even mock your custom methods
    Mockito.when(Person.findOrdered()).thenReturn(Collections.emptyList());
     */
    @Test
    public void testDownloadFinder(){
        PanacheMock.mock(Download.class);
        String mockedSearchKey = "mock search key";

        Mockito.when(Download.getSearchKeyFromRemote()).thenReturn(mockedSearchKey);
        Mockito.when(Download.myFinder()).thenCallRealMethod();

       Assertions.assertEquals(mockedSearchKey, Download.myFinder());
    }
}
