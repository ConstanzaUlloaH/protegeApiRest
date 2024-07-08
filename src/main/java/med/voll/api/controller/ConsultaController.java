package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Controller
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta) throws ValidacionDeIntegridad {

        //CADA CLASE DEBE TENER UNA UNICA RESPONSABILIDAD.

        //LA RESPONSABILIDAD ES MOSTRAR LOS DATOS DE LAS CONSULTAS

        var response = service.agendar(datosAgendarConsulta);


        return ResponseEntity.ok( response);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar (@RequestBody @Valid DatosCancelamientoConsulta datos)throws ValidacionDeIntegridad{
        service.cancelar(datos);
        return ResponseEntity.noContent().build();
    }



}
