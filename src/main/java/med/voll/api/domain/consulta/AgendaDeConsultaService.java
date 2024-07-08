package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.desafio.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//CADA CLASE DEBE TENER UNA UNICA RESPONSABILIDAD.

@Service
public class AgendaDeConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    @Autowired
    List<ValidadorCancelamientoDeConsulta> validadorCancelamiento;

    public DatosDetalleConsulta agendar( DatosAgendarConsulta datos){

        //Validaciones de  integridad
        //chequear id paciente
        if (!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("este Id para el paciente no fue encontrado");
        }

        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("este Id para el medico no fue encontrado");
        }

        //VALIDACIONES

        validadores.forEach(v-> v.validar(datos));

        //Encuentra pacientes por id
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        //Encuentra medico por id
        var medico = seleccionarMedico(datos);
        //Crea una nueva consulta
        if(medico==null){
            throw new ValidacionDeIntegridad("no existen medicos disponibles para este horario y especialidad");
        }
        var consulta = new Consulta( medico, paciente , datos.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);


    }

    public void cancelar(DatosCancelamientoConsulta datos){
        if(!consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionDeIntegridad("Id de la Consulta informaado no existe");
        }
         validadorCancelamiento.forEach(v -> v.validar(datos));
        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
    //cambio intellij

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if (datosAgendarConsulta.idMedico() != null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if (datosAgendarConsulta.especialidad()==null){
            throw  new ValidacionDeIntegridad(("debe seleccionar una especialidad para el medico"));
        }


        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad(), datosAgendarConsulta.fecha());
    }
}

//package med.voll.api.domain.consulta;
//
//import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
//import med.voll.api.domain.medico.Medico;
//import med.voll.api.domain.medico.MedicoRepository;
//import med.voll.api.domain.paciente.PacienteRepository;
//import med.voll.api.infra.errores.ValidacionDeIntegridad;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AgendaDeConsultaService {
//
//    @Autowired
//    private ConsultaRepository consultaRepository;
//
//    @Autowired
//    private PacienteRepository pacienteRepository;
//
//    @Autowired
//    private MedicoRepository medicoRepository;
//
//    @Autowired
//    private List<ValidadorDeConsultas> validadores;
//
//    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos) {
//        // Validaciones de integridad
//        if (!pacienteRepository.findById(datos.idPaciente()).isPresent()) {
//            throw new ValidacionDeIntegridad("Este ID de paciente no fue encontrado");
//        }
//
//        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
//            throw new ValidacionDeIntegridad("Este ID de médico no fue encontrado");
//        }
//
//        validadores.forEach(v -> v.validar(datos));
//
//        // Lógica para seleccionar médico y agendar consulta
//        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
//
//        var medico = seleccionarMedico(datos);
//
//        if (medico == null) {
//            throw new ValidacionDeIntegridad("No hay médicos disponibles para este horario y especialidad");
//        }
//
//        var consulta = new Consulta(null, medico, paciente, datos.fecha());
//
//        consultaRepository.save(consulta);
//
//        return new DatosDetalleConsulta(consulta);
//    }
//
//    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
//        if(datos.idMedico()!=null){
//            return medicoRepository.getReferenceById(datos.idMedico());
//        }
//
//        if (datos.especialidad() == null) {
//            throw new ValidacionDeIntegridad("Debe seleccionar una especialidad para el médico");
//        }
//
//        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
//    }
//}
