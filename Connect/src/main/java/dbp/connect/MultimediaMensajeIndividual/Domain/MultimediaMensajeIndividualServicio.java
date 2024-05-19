package dbp.connect.MultimediaMensajeIndividual.Domain;

import dbp.connect.MultimediaMensajeIndividual.Infrastructure.MultimediaMensajeIndividualRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultimediaMensajeIndividualServicio {
    @Autowired
    MultimediaMensajeIndividualRepositorio multimediaRepositorio;

    public void guardarMultimediaMensaje(MultimediaMensajeIndividual mensaje) {
        multimediaRepositorio.save(mensaje);
    }
}
