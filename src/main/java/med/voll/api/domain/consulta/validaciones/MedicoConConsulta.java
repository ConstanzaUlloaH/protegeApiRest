//package med.voll.api.domain.consulta.validaciones;
//
//import jakarta.validation.ValidationException;
//import med.voll.api.domain.consulta.ConsultaRepository;
//import med.voll.api.domain.consulta.DatosAgendarConsulta;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MedicoConConsulta implements ValidadorDeConsultas {
//
//    private ConsultaRepository repository;
//
//    public void validar(DatosAgendarConsulta datosAgendarConsulta){
//        if (datosAgendarConsulta.idMedico()==null){
//            return;
//        }
//        var medicoConConsulta = repository.existsByMedicoIdAndFecha(datosAgendarConsulta.idMedico(),datosAgendarConsulta.fecha());
//
//
//        if (medicoConConsulta){
//            throw new ValidationException("este medico ya tiene una consulta en ese horario");
//        }
//    }
//}


package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta) {
        if (datosAgendarConsulta.idMedico() == null) {
            return;
        }

        var medicoConConsulta = repository.existsByMedicoIdAndFecha(datosAgendarConsulta.idMedico(), datosAgendarConsulta.fecha());

        if (medicoConConsulta) {
            throw new ValidacionDeIntegridad("Este medico ya tiene una consulta en ese horario");
        }
    }
}

