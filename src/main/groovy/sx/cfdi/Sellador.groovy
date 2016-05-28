package sx.cfdi

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.Signature


import org.bouncycastle.util.encoders.Base64
import org.apache.commons.lang.exception.ExceptionUtils

import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.spec.PKCS8EncodedKeySpec

/**
 * Created by rcancino on 25/05/16.
 */
class Sellador {

    PrivateKey privateKey

    X509Certificate certificado

    String sellar(byte[] llavePrivada, String cadenaOriginal){

        try {
            final byte[] input=cadenaOriginal.getBytes("UTF-8")
            Signature signature=Signature.getInstance('SHA1withRSA',"BC");
            signature.initSign(getPrivateKey(llavePrivada))
            signature.update(input)

            final byte[] signedData=signature.sign()
            final byte[] encoedeData=Base64.encode(signedData)
            return new String(encoedeData,"UTF-8")
        } catch (Exception e) {
            e.printStackTrace()
            String msg="Error generando sello digital: "+ExceptionUtils.getRootCauseMessage(e);
            throw new RuntimeException(msg,e)
        }
    }


    PrivateKey getPrivateKey(byte[] encodedKey){
        if(!privateKey){
            try {
                PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(encodedKey)
                final  KeyFactory keyFactory=KeyFactory.getInstance("RSA","BC")
                this.privateKey=keyFactory.generatePrivate(keySpec)
            } catch (Exception e) {
                e.printStackTrace()
                println 'Error tratando de leer llave privada :'+ExceptionUtils.getRootCauseMessage(e)
            }

        }
        return privateKey;
    }

    public X509Certificate getCertificado(byte[] encodedCertificado) {
        if(certificado==null){
            try{
                CertificateFactory fact= CertificateFactory.getInstance("X.509","BC");
                InputStream is=new ByteArrayInputStream(encodedCertificado);
                certificado = (X509Certificate)fact.generateCertificate(is);
                certificado.checkValidity();
                return certificado;
            }catch (Exception e) {
                String msg=ExceptionUtils.getRootCauseMessage(e);
                throw new RuntimeException("Error tratando de leer Certificado: "+msg,e);
            }
        }
        return certificado;

    }
}
