package dbp.connect.ComentariosMultimedia.Domain;

import dbp.connect.Comentarios.Domain.Comentario;
import dbp.connect.ComentariosMultimedia.Infrastructure.ComentarioMultimediaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ComentarioMultimediaServicio {
    @Autowired
    ComentarioMultimediaRepositorio comentariosMultimediaRepositorio;
    public void saveMultimedia(Comentario comentario, MultipartFile file) {
        try {
            ComentarioMultimedia multimedia = new ComentarioMultimedia();
            multimedia.setContenido(file.getBytes());
            multimedia.setTipoConte(file.getContentType());
            if(multimedia.getTipoConte().equalsIgnoreCase("image")){
                multimedia.setTipo(Multimedia.FOTO);
            }
            else if(multimedia.getTipoConte().equalsIgnoreCase("video")){
                multimedia.setTipo(Multimedia.VIDEO);
            }
            else{
                throw new IllegalArgumentException("Tipo de multimedia no valido");

            }
            comentariosMultimediaRepositorio.save(multimedia);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo multimedia", e);
        }
    }
}
