package dbp.connect.PublicacionInicioMultimedia.Domain;

import dbp.connect.ComentariosMultimedia.Domain.ComentarioMultimedia;
import dbp.connect.ComentariosMultimedia.Domain.Multimedia;
import dbp.connect.PublicacionInicioMultimedia.Infrastructure.PublicacionInicioMultimediaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublicacionInicioMultimediaServicio {
    @Autowired
    private PublicacionInicioMultimediaRepositorio publicacionInicioMultimediaRepositorio;

    public PublicacionInicioMultimedia guardarMultimedia(MultipartFile file){
        try {
            PublicacionInicioMultimedia multimedia = new PublicacionInicioMultimedia();
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
            publicacionInicioMultimediaRepositorio.save(multimedia);
            return multimedia;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo multimedia", e);
        }

    }
    public List<PublicacionInicioMultimedia> guardarMultimedia(List<MultipartFile> archivos) {
        List<PublicacionInicioMultimedia> multimediaList = new ArrayList<>();
        for (MultipartFile file : archivos) {
            try {
                PublicacionInicioMultimedia multimedia = new PublicacionInicioMultimedia();

                multimedia.setContenido(file.getBytes());
                multimedia.setTipoConte(file.getContentType());

                if (file.getContentType().startsWith("image/")) {
                    multimedia.setTipo(Multimedia.FOTO);
                } else if (file.getContentType().startsWith("video/")) {
                    multimedia.setTipo(Multimedia.VIDEO);
                } else {
                    throw new IllegalArgumentException("Tipo de multimedia no valido: " + file.getContentType());
                }

                multimediaList.add(multimedia);
            } catch (IOException e) {
                throw new IllegalArgumentException("No se puedo guardar la archivo multimedia");
            }
        }
        return multimediaList;
    }
}
