package com.gabriel.hksongguesser.core.infrastructure.storage;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gabriel.hksongguesser.domain.service.MusicaArquivoStorageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.nio.file.Path;

public class S3ArquivoMusicaService implements MusicaArquivoStorageService {


    @Autowired
    private AmazonS3 amazonS3;
    @Autowired
    private StorageProperties properties;

    @Override
    public void armazenar(MusicaArquivoStorageRequest request) {
        try {
            var pathArquivo = getArquivoPath(request.getNomeArquivo());
            var objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(request.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    properties.getS3().getBucket(),
                    pathArquivo,
                    request.getInputStream(),
                    objectMetaData);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception ex) {
            throw new StorageException("Não foi possível enviar o arquivo para Amazon S3", ex);
        }
    }

    @Override
    public void remover(String nomeArquivoMusica) {
        try {
            var pathArquivo = getArquivoPath(nomeArquivoMusica);
            var deleteObjectREquest = new DeleteObjectRequest(
                    properties.getS3().getBucket(),
                    pathArquivo
            );

            amazonS3.deleteObject(deleteObjectREquest);

        } catch (Exception ex) {
            throw new StorageException("Não foi possível remover o arquivo da Amazon S3", ex);
        }
    }

    @Override
    public MusicaArquivoStorageModel recuperarArquivo(String nomeArquivoMusica) {
        var pathArquivo = getArquivoPath(nomeArquivoMusica);
        URL url = getUrl(pathArquivo);

        return MusicaArquivoStorageModel.builder()
                .url(url.toString())
                .build();
    }

    @Override
    public String gerarUrlArquivo(String nomeArquivo) {
        return getUrl(getArquivoPath(nomeArquivo)).toString();
    }

    private URL getUrl(String pathArquivo) {
        return amazonS3.getUrl(properties.getS3().getBucket(), pathArquivo);
    }

    private String getArquivoPath(String nomeArquivoMusica) {
        return String.format("%s/%s", properties.getS3().getDiretorioMusicas(), nomeArquivoMusica);
    }
}
