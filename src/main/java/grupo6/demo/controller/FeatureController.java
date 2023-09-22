package grupo6.demo.controller;

import grupo6.demo.model.Feature;
import grupo6.demo.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/caracteristicas")
public class FeatureController {
    @Autowired
    private FeatureService service;

    @GetMapping
    public ResponseEntity<List<Feature>> listFeatures() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<List<Feature>> listarCaracteristicasPorProducto(@PathVariable Integer productId) {
//        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(service.getAllByProductId(productId));
//        System.out.println(response);
//        return response;
//
//
//
//    }
}
