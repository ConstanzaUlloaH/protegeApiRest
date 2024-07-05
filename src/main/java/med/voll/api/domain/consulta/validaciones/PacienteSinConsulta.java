package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PacienteSinConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository repository;
    //validar si paciente existe
    public void validar (DatosAgendarConsulta datosAgendarConsulta){
        var primerHorarioDeConsulta= datosAgendarConsulta.fecha().withHour(7);
        var ultimoHorarioDeConsulta = datosAgendarConsulta.fecha().withHour(18);

        var pacienteConConsulta = repository.existsByPacienteIdAndFechaBetween(datosAgendarConsulta.idPaciente(),primerHorarioDeConsulta,ultimoHorarioDeConsulta);

        if (pacienteConConsulta){
            throw new ValidacionDeIntegridad("el paciente ya tiene una consulta para ese dia");
        }
    }
}
