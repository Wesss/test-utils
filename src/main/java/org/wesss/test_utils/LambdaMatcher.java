package org.wesss.test_utils;

import com.sun.istack.internal.Nullable;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class LambdaMatcher<T> extends BaseMatcher<T> {

    //TODO

    private Predicate<T> matchingPredicate;
    private Consumer<Description> expectedDescriptor;
    private BiConsumer<T, Description> mismatchDescriptor;
    private
    @Nullable
    Exception thrownMatchException;

    public LambdaMatcher(Predicate<T> matchingPredicate,
                         Consumer<Description> expectedDescriptor,
                         BiConsumer<T, Description> mismatchDescriptor) {
        this.matchingPredicate = matchingPredicate;
        this.expectedDescriptor = expectedDescriptor;
        this.mismatchDescriptor = mismatchDescriptor;
        thrownMatchException = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(Object item) {
        try {
            return matchingPredicate.test((T) item);
        } catch (Exception e) {
            thrownMatchException = e;
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        expectedDescriptor.accept(description);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void describeMismatch(Object item, Description description) {
        if (thrownMatchException != null) {
            description.appendText("matching predicate threw Exception: " + thrownMatchException);
            return;
        }

        try {
            mismatchDescriptor.accept((T) item, description);
        } catch (Exception e) {
            description.appendText("mismatch descriptor threw Exception: " + e);
        }
    }
}
