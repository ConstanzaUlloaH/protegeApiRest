//package med.voll.api.domain.consulta.validaciones;
//
//import jakarta.validation.ValidationException;
//import med.voll.api.domain.consulta.DatosAgendarConsulta;
//import org.springframework.stereotype.Component;
//
//import java.time.DayOfWeek;
//
//@Component
//public class HorarioDeFuncionamiento implements ValidadorDeConsultas {
//
//    public void  validar(DatosAgendarConsulta datosAgendarConsulta){
//        var domingo = DayOfWeek.SUNDAY.equals(datosAgendarConsulta.fecha());
//
//        var antesDeApertura = datosAgendarConsulta.fecha().getHour()<7;
//        var despuesDeCierre =datosAgendarConsulta.fecha().getHour()>19;
//        if (domingo || antesDeApertura || despuesDeCierre){
//            throw new ValidationException("El horario de atencion es de las 07:00 a 19:00 horas, de Lunes a Sabado.");
//        }
//    }
//}

package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamiento implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos) {

        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());

        var antesdDeApertura=datos.fecha().getHour()<7;
        var despuesDeCierre=datos.fecha().getHour()>19;
        if(domingo || antesdDeApertura || despuesDeCierre){
            throw  new ValidacionDeIntegridad("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
        }
    }
}