package org.wesss.test_utils;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.wesss.test_utils.IsSuchThat.isSuchThat;

public class LambdaMatcherTest {

    public ObjGet ObjGet = mock(ObjGet.class);

    @Before
    public void before() {
        reset(ObjGet);
    }

    @Test
    public void objMatchesObj() {
        Object obj = new Object();

        assertThat(obj, IsSuchThat.isSuchThat(o -> o.equals(obj)));
    }

    @Test
    public void nullMatchesNull() {
        assertThat(null, IsSuchThat.isSuchThat(o -> o == null));
    }

    @Test
    public void objDoesntMatchNull() {
        Object obj = new Object();

        assertThat(obj, not(IsSuchThat.isSuchThat(o -> o == null)));
    }

    @Test
    public void nullDoesntMatchObj() {
        Object obj = new Object();
        assertThat(null, not(IsSuchThat.isSuchThat(o -> o.equals(obj))));
    }

    @Test
    public void argDoesntMatch() {
        Object arg = new Object();
        Object ret = new Object();
        when(ObjGet.getObj(IsSuchThat.argSuchThat(obj -> !obj.equals(arg)))).thenReturn(ret);

        assertThat(ObjGet.getObj(arg), not(ret));
    }

    @Test
    public void argMatches() {
        Object arg = new Object();
        Object ret = new Object();
        when(ObjGet.getObj(IsSuchThat.argSuchThat(obj -> obj.equals(arg)))).thenReturn(ret);

        assertThat(ObjGet.getObj(arg), is(ret));
    }

    private static class ObjGet {
        public Object getObj(Object obj) {
            return obj;
        }
    }
}




