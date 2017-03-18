package org.wesss.test_utils.matchers;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.wesss.test_utils.matchers.IsSuchThat.*;

public class LambdaMatcherTest {

    private ObjGet objGet = mock(ObjGet.class);

    public LambdaMatcherTest() {
        reset(objGet);
    }

    @Before
    public void before() {
        reset(objGet);
    }

    @Test
    public void objMatchesObj() {
        Object obj = new Object();

        assertThat(obj, isSuchThat(o -> o.equals(obj)));
    }

    @Test
    public void nullMatchesNull() {
        assertThat(null, isSuchThat(o -> o == null));
    }

    @Test
    public void objDoesntMatchNull() {
        Object obj = new Object();

        assertThat(obj, not(isSuchThat(o -> o == null)));
    }

    @Test
    public void nullDoesntMatchObj() {
        Object obj = new Object();
        assertThat(null, not(isSuchThat(o -> o.equals(obj))));
    }

    @Test
    public void argDoesntMatch() {
        Object arg = new Object();
        Object ret = new Object();
        when(objGet.getObj(argSuchThat(obj -> !obj.equals(arg)))).thenReturn(ret);

        assertThat(objGet.getObj(arg), not(ret));
    }

    @Test
    public void argMatches() {
        Object arg = new Object();
        Object ret = new Object();
        when(objGet.getObj(argSuchThat(obj -> obj.equals(arg)))).thenReturn(ret);

        assertThat(objGet.getObj(arg), is(ret));
    }

    private static class ObjGet {
        public Object getObj(Object obj) {
            return obj;
        }
    }
}




