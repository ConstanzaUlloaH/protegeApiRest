//package med.voll.api.domain.consulta.validaciones;
//
//import med.voll.api.domain.consulta.DatosAgendarConsulta;
//import med.voll.api.domain.medico.MedicoRepository;
//import med.voll.api.infra.errores.ValidacionDeIntegridad;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MedicoActivo implements ValidadorDeConsultas {
//
//    private final MedicoRepository repository;
//    @Autowired
//    public MedicoActivo(MedicoRepository repository) {
//        this.repository = repository;
//    }
//
//    public void validar(DatosAgendarConsulta datosAgendarConsulta){
//        if (datosAgendarConsulta.idMedico() == null){
//            return;
//        }
//        var medicoActivo = repository.findActivoById(datosAgendarConsulta.idMedico());
//
//        if (!medicoActivo){
//            throw new ValidacionDeIntegridad("No se puede agendar horas con un Medico inactivo");
//        }
//
//    }
//}

package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoActivo implements ValidadorDeConsultas{
    @Autowired
    private MedicoRepository repository;

    public void validar(DatosAgendarConsulta datos) {
        if(datos.idMedico()==null){
            return;
        }
        var medicoActivo=repository.findActivoById(datos.idMedico());
        if(!medicoActivo){
            throw new ValidacionDeIntegridad("No se puede permitir agendar citas con medicos inactivos en el sistema");
        }
    }
}