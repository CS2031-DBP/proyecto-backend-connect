package dbp.connect.MultimediaInicio.Domain;

import dbp.connect.MultimediaInicio.Infrastructure.MultimediaInicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultimediaInicioServicio {
    @Autowired
    private MultimediaInicioRepository multimediaRepository;

    public MultimediaInicio guardarMultimedia(MultimediaInicio multimedia) {
        return multimediaRepository.save(multimedia);
    }

}
