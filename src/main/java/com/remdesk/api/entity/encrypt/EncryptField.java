package com.remdesk.api.entity.encrypt;

import com.remdesk.api.exception.HttpInternalServerErrorException;
import com.remdesk.api.module.configuration.ConfigurationHandler;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class EncryptField implements AttributeConverter< String, String > {


    protected final static String               ALGORITHM = "AES";
    protected final        ConfigurationHandler configurationHandler;
    protected final        Cipher               cipherEncrypt;
    protected final        Cipher               cipherDecrypt;
    protected final        Key                  key;


    public EncryptField( ConfigurationHandler configurationHandler ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.configurationHandler = configurationHandler;
        cipherEncrypt             = Cipher.getInstance( ALGORITHM );
        cipherDecrypt             = Cipher.getInstance( ALGORITHM );
        key                       = new SecretKeySpec( configurationHandler.getDatabaseConfig().getEncryptionKey().getBytes(), ALGORITHM );
        cipherEncrypt.init( Cipher.ENCRYPT_MODE, key );
        cipherDecrypt.init( Cipher.DECRYPT_MODE, key );
    }


    @Override
    public String convertToDatabaseColumn( String data ) {

        try {
            return Base64.getEncoder().encodeToString( cipherEncrypt.doFinal( data.getBytes() ) );
        } catch ( BadPaddingException | IllegalBlockSizeException e ) {
            throw new HttpInternalServerErrorException( "An error occurred" );
        }
    }


    @Override
    public String convertToEntityAttribute( String data ) {

        try {
            return new String( cipherDecrypt.doFinal( Base64.getDecoder().decode( data ) ) );
        } catch ( BadPaddingException | IllegalBlockSizeException e ) {
            throw new HttpInternalServerErrorException( "An error occurred" );
        }
    }
}
