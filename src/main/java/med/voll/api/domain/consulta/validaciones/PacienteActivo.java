//package med.voll.api.domain.consulta.validaciones;
//
//import jakarta.validation.ValidationException;
//import med.voll.api.domain.consulta.DatosAgendarConsulta;
//import med.voll.api.domain.paciente.PacienteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PacienteActivo  implements ValidadorDeConsultas{
//
//    private PacienteRepository repository;
//
//    @Autowired
//    public PacienteActivo(PacienteRepository repository) {
//        this.repository = repository;
//    }
//    public void validar(DatosAgendarConsulta datosAgendarConsulta){
//
//        if (datosAgendarConsulta.idPaciente()==null){
//            return;
//        }
//
//        var pacienteActivo= repository.findActivoById(datosAgendarConsulta.idPaciente());
//
//        if (!pacienteActivo){
//            throw new ValidationException("No se puede peermitir agendar a pacientes inactivos en el sistema");
//        }
//    }
//}
package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorDeConsultas{
    @Autowired
    private PacienteRepository repository;

    public void validar(DatosAgendarConsulta datos){
        if(datos.idPaciente()==null){
            return;
        }
        var pacienteActivo=repository.findActivoById(datos.idPaciente());

        if(!pacienteActivo){
            throw new ValidacionDeIntegridad("No se puede permitir agendar citas con pacientes inactivos en el sistema");
        }
    }
}