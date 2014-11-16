package com.bpf.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.bpf.BPF;
import org.apache.commons.fileupload.FileItem;
import org.bson.types.ObjectId;
import java.io.ByteArrayInputStream;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonClientException;

public class S3 implements FileStore {

    private String accessKey = null;
    private String secretKey = null;
    private String bucketName = null;
    private String type = null;
    private AmazonS3 client = null;

    public String getType(){
        return this.type;
    }

    public void setInitVars(){

        type = BPF.FileStorageTypes.S3.name();

        accessKey = BPF.getConfigParam(BPF.ConfigKeys.S3_ACCESS_KEY.name());
        secretKey = BPF.getConfigParam(BPF.ConfigKeys.S3_SECRET_KEY.name());
        bucketName = BPF.getConfigParam(BPF.ConfigKeys.S3_BUCKET_NAME.name());

        AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
        client = new AmazonS3Client(creds);

    }

    public void save(FileItem fileItem, ObjectId id) throws Exception{

        S3Object s3Object = null;
        ByteArrayInputStream inputStream = null;

        try {

            s3Object = new S3Object();

            ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentType(fileItem.getContentType());
            metaData.setContentLength(fileItem.getSize());
            //metaData.setHeader("filename", fileItem.getName());

            inputStream = new ByteArrayInputStream(fileItem.get());

            s3Object.setObjectContent(inputStream);
            client.putObject(new PutObjectRequest(bucketName, id.toString(), inputStream, metaData));

        } catch (AmazonServiceException ase) {
            throw ase;
        } catch (AmazonClientException ace) {
            throw ace;
        } catch (Exception e) {
           throw e;
        } finally {
            if (null != s3Object){
                s3Object.close();
            }
            if (null != inputStream){
                inputStream.close();
            }
        }

    }

}
