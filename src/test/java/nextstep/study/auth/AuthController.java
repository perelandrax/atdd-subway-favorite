package nextstep.study.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login/session")
    public ResponseEntity login() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/session")
    public ResponseEntity session() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/basic")
    public ResponseEntity basic() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/bearer")
    public ResponseEntity bearer() {
        return ResponseEntity.ok().build();
    }
}
