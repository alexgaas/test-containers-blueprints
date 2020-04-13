package commons;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UnitTestBase {
  public Matcher<ResponseEntity> responseEntityWithStatus(HttpStatus status) {
    return new TypeSafeMatcher<ResponseEntity>() {
      @Override
      protected boolean matchesSafely(ResponseEntity item) {
        return status.equals(item.getStatusCode());
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("ResponseEntity with status ").appendValue(status);
      }
    };
  }

  public <T> Matcher<ResponseEntity<? extends T>> responseEntityThat(Matcher<T> categoryMatcher) {
    return new TypeSafeMatcher<ResponseEntity<? extends T>>() {
      @Override
      protected boolean matchesSafely(ResponseEntity<? extends T> item) {
        return categoryMatcher.matches(item.getBody());
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("ResponseEntity with ").appendValue(categoryMatcher);
      }
    };
  }
}
