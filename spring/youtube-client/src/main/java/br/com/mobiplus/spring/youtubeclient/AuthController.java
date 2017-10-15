package br.com.mobiplus.spring.youtubeclient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;

/**
 * Created by luisfernandez on 14/10/17.
 */
@RestController
public class AuthController {

    @PostMapping("/auth")
    @ResponseBody()
    DeferredResult<ResponseEntity<String>> doLogin(@Valid @RequestBody UserDTO userDTO, Errors errors) {
        final DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();

        String idToken = userDTO.getIdToken();
        System.out.println(idToken);

        ResponseEntity<String> responseEntity = new ResponseEntity<>(idToken, null, HttpStatus.OK);
        deferredResult.setResult(responseEntity);
        return deferredResult;
    }
}
