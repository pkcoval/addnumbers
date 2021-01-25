package app.addnumbers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class Controller {

  private final NumberService numberService;

  @GetMapping("/{min}/{max}/add-numbers")
  public Object addTwoNumbers(@PathVariable final int max, @PathVariable final int min) {
    return numberService.sumOfTwoNumbers(min, max);
  }

}
