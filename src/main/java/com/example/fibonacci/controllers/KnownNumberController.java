package com.example.fibonacci.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fibonacci.Utils;
import com.example.fibonacci.models.KnownNumberModel;
import com.example.fibonacci.services.KnownNumberService;

@RestController
@RequestMapping("/api/fibonacci")
public class KnownNumberController {
  private final KnownNumberService knownValueService;

  @Autowired
  public KnownNumberController(KnownNumberService knownValueService) {
    this.knownValueService = knownValueService;
  }

  @GetMapping("/statistics")
  public ResponseEntity<?> getStatistics() {
    List<KnownNumberModel> result = knownValueService.getStatistics();
    return Utils.sendResponse(HttpStatus.OK, Utils.SUCCESS, result);  
  }

  @PostMapping("/calculate/{number}")
  public ResponseEntity<?> calculate(@PathVariable("number") Integer number) {
    
    // Controlo que 0 <= n <= 90
    if (number < 0)
      return Utils.sendResponse(HttpStatus.BAD_REQUEST, Utils.ERROR_NEGATIVE_NUMBER, number);
    else if (number > 90)
      return Utils.sendResponse(HttpStatus.BAD_REQUEST, Utils.ERROR_GREATHER_90, number);

    // Si coincide result con el number devulevo directo
    KnownNumberModel result = knownValueService.getKnownValue(number);
    if (result != null && result.getValue() == number) {
      KnownNumberModel updated = knownValueService.updateFrecuency(number);
      if (updated != null) 
        return Utils.sendResponse(HttpStatus.OK, Utils.SUCCESS, updated);
    }
    
    // Si result es nulo -> calculo de 0 a n
    // Si result no es nulo -> calculo desde ulitmo index hasta n
    List<KnownNumberModel> newValues = new ArrayList<>();
    Integer start = result == null ? 0 : result.getValue() + 1;
    IntStream
      .range(start, number + 1)
      .forEach(index -> {
        Long fibonacci = Utils.fibonacci(index);
        KnownNumberModel newValue = new KnownNumberModel(index, fibonacci, 1);
        newValues.add(newValue);
      });
    
    knownValueService.saveValues(newValues);
    int lastIndex = newValues.size() - 1;

    return Utils.sendResponse(HttpStatus.OK, Utils.SUCCESS, newValues.get(lastIndex));
  }

}
