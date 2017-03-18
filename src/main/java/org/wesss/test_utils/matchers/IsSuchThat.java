package org.wesss.test_utils.matchers;

import org.hamcrest.Matcher;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.mockito.Matchers.argThat;

/**
 * IsSuchThat contains static methods for creating matchers for hamcrest and mockito using lambdas
 */
public class IsSuchThat {

    private static final String DEFAULT_EXPECTED_BEHAVIOR_DESCRIPTION = "a value matching given predicate";
    private static final String DEFAULT_FAILED_DESCRIPTION = "did not match";

    /**
     * @param matchingPredicate
     * @param <T>
     * @return a matcher that matches values such that the given predicate returns true when given said value.
     */
    public static <T> Matcher<T> isSuchThat(Predicate<T> matchingPredicate) {
        return new LambdaMatcher<>(matchingPredicate,
                desc -> desc.appendText(DEFAULT_EXPECTED_BEHAVIOR_DESCRIPTION),
                (mismatch, desc) -> desc.appendText(DEFAULT_FAILED_DESCRIPTION));
    }

    /**
     * @param matchingPredicate
     * @param expectedBehavior  the expected clause to be displayed on assertion error
     * @param <T>
     * @return a matcher that matches values such that the given predicate returns true when given said value.
     */
    public static <T> Matcher<T> isSuchThat(Predicate<T> matchingPredicate,
                                            String expectedBehavior) {
        return new LambdaMatcher<>(matchingPredicate,
                desc -> desc.appendText(expectedBehavior),
                (mismatch, desc) -> desc.appendText(DEFAULT_FAILED_DESCRIPTION));
    }

    /**
     * @param matchingPredicate
     * @param expectedBehavior   the 'expected' clause to be displayed on assertion error
     * @param failureDescription the 'but' clause to be displayed on assertion error
     * @param <T>
     * @return a matcher that matches values such that the given predicate returns true when given said value.
     */
    public static <T> Matcher<T> isSuchThat(Predicate<T> matchingPredicate,
                                            String expectedBehavior,
                                            String failureDescription) {
        return new LambdaMatcher<>(matchingPredicate,
                desc -> desc.appendText(expectedBehavior),
                (mismatch, desc) -> desc.appendText(failureDescription));
    }

    /**
     * @param matchingPredicate
     * @param expectedBehavior           the 'expected' clause to be displayed on assertion error
     * @param failureDescriptionFunction a function that will be used to produce the 'but' clause given the mismatched value
     * @param <V>
     * @return a matcher that matches values such that the given predicate returns true when given said value.
     */
    public static <V> Matcher<V> isSuchThat(Predicate<V> matchingPredicate,
                                            String expectedBehavior,
                                            Function<V, String> failureDescriptionFunction) {
        return new LambdaMatcher<>(matchingPredicate,
                desc -> desc.appendText(expectedBehavior),
                (mismatch, desc) -> desc.appendText(failureDescriptionFunction.apply(mismatch)));
    }

    /**
     * Use as a "matcher" for mockito arguments that matches that matches values such that the given predicate
     * returns true when given said value.
     *
     * @param matchingPredicate
     * @param <V>
     * @return
     * @see org.mockito.Matchers#argThat(Matcher)
     */
    public static <V> V argSuchThat(Predicate<V> matchingPredicate) {
        return argThat(isSuchThat(matchingPredicate));
    }
}
