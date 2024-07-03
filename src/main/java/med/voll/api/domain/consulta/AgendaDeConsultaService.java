package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    @Autowired
    public ConsultaRepository consultaRepository;

    @Autowired
    public PacienteRepository pacienteRepository;

    @Autowired
    public MedicoRepository medicoRepository;

    public void agendar( DatosAgendarConsulta datosAgendarConsulta){

        //Validaciones de  integridad
        //chequear id paciente
        if (pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("este Id para el paciente no fue encontrado");
        }

        if (datosAgendarConsulta.idMedico() != null && medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionDeIntegridad("este Id para el medico no fue encontrado");
        }



        //Encuentra pacientes por id
        var paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();
        //Encuentra medico por id
        var medico = seleccionarMedico(datosAgendarConsulta);
        //Crea una nueva consulta
        var consulta = new Consulta(null, medico, paciente , datosAgendarConsulta.fecha());

        consultaRepository.save(consulta);


    }

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
