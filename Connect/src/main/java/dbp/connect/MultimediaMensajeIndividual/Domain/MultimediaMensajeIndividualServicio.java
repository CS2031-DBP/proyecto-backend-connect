package dbp.connect.MultimediaMensajeIndividual.Domain;

import dbp.connect.MultimediaMensajeIndividual.Infrastructure.MultimediaMensajeIndividualRepositorio;
import dbp.connect.Tipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MultimediaMensajeIndividualServicio {
    @Autowired
    private MultimediaMensajeIndividualRepositorio multimediaRepositorio;

    public MultimediaMensajeIndividual saveMultimedia( MultipartFile file) {
        try {
            MultimediaMensajeIndividual multimedia = new MultimediaMensajeIndividual();
            multimedia.setContenido(file.getBytes());
            multimedia.setTipoContenido(file.getContentType());
            if(multimedia.getTipoContenido().equalsIgnoreCase("image")){
                multimedia.setTipo(Tipo.FOTO);
            }
            else if(multimedia.getTipoContenido().equalsIgnoreCase("video")){
                multimedia.setTipo(Tipo.VIDEO);
            }
            else{
                throw new IllegalArgumentException("Tipo de multimedia no valido");
            }
            multimediaRepositorio.save(multimedia);
            return multimedia;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo multimedia", e);
        }
    }
}
