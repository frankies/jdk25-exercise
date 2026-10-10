package com.example.jdk25;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Demonstrates Mockito usage against the sealed-type and stream examples in {@link App}.
 */
class AppMockitoTest {

  private App realApp;

  @BeforeEach
  void setUp() {
    realApp = new App();
  }

  @Test
  void sealedExhaustiveReturnsPerType() {
      assertThat(App.testSealedExhaustive(realApp.new A())).isEqualTo(1);
      assertThat(App.testSealedExhaustive(realApp.new B())).isEqualTo(2);
      assertThat(App.testSealedExhaustive(new App.C(5))).isEqualTo(3);
  }

  @Test
  void genericSealedExhaustiveOnlyAllowsE() {
      assertThat(App.testGenericSealedExhaustive(realApp.new E<Integer>())).isEqualTo(42);
  }

  @Test
  void mockAppReturnsStubbedAreaForSquare() {
    App app = mock(App.class);
      App.Square side = new App.Square(4.0);
    when(app.areaOf(side)).thenReturn(99.0);

    assertThat(app.areaOf(side)).isEqualTo(99.0);
    verify(app).areaOf(side);
  }

  @Test
    void mockReturnsDefaultWithoutCallingRealMethod() {
    App app = mock(App.class);

      // An unstubbed double method returns 0.0 instead of invoking the real logic.
      assertThat(app.areaOf(new App.Circle(3.0))).isEqualTo(0.0);
      verify(app).areaOf(any());
    }

    @Test
    void verifyInteractionWithMockedPredicate() {
      @SuppressWarnings("unchecked")
      Predicate<Integer> predicate = mock(Predicate.class);
      // lenient: the mock is also invoked with unstubbed arguments during filtering.
      lenient().when(predicate.test(2)).thenReturn(true);

      var result = realApp.filterEven(List.of(1, 2, 3), predicate);

      assertThat(result).containsExactly(2);
      verify(predicate).test(1);
      verify(predicate).test(2);
      verify(predicate).test(3);
    }

    @Test
    void doAnswerCanComputeInsteadOfReturn() {
      @SuppressWarnings("unchecked")
      Predicate<Integer> predicate = mock(Predicate.class);
      doAnswer(invocation -> (Integer) invocation.getArgument(0) % 2 == 0)
          .when(predicate).test(any(Integer.class));

      var result = realApp.filterEven(List.of(1, 2, 3, 4), predicate);

      assertThat(result).containsExactly(2, 4);
    }
}