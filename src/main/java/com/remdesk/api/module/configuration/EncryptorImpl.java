package com.remdesk.api.module.configuration;

import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.exception.HttpInternalServerErrorException;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class EncryptorImpl implements Encryptor {

    protected final static String               ALGORITHM = "AES";
    protected final        ConfigurationHandler configurationHandler;
    protected              Cipher               cipher;
    protected              Key                  key;


    public EncryptorImpl( ConfigurationHandler configurationHandler ) {
        this.configurationHandler = configurationHandler;
    }


    @Override
    public byte[] encrypt( byte[] content ) {
        try {
            load();
        } catch ( NoSuchPaddingException | NoSuchAlgorithmException | NullPointerException e ) {
            throw new HttpInternalServerErrorException( Message.DATABASE_CONFIGURATION_ENCRYPTION_KEY_NOT_SET );
        }

        try {
            cipher.init( Cipher.ENCRYPT_MODE, key );
            return cipher.doFinal( Base64.getEncoder().encode( content ) );
        } catch ( InvalidKeyException | BadPaddingException | IllegalBlockSizeException e ) {
            e.printStackTrace();
            throw new HttpInternalServerErrorException( Message.INTERNAL_SERVER_ERROR );
        }
    }


    @Override
    public String encrypt( String content ) {
        return new String( encrypt( content.getBytes() ) );
    }


    @Override
    public byte[] decrypt( byte[] content ) {
        try {
            load();
        } catch ( NoSuchPaddingException | NoSuchAlgorithmException | NullPointerException e ) {
            throw new HttpInternalServerErrorException( Message.DATABASE_CONFIGURATION_ENCRYPTION_KEY_NOT_SET );
        }


        try {
            cipher.init( Cipher.DECRYPT_MODE, key );
            return Base64.getDecoder().decode( cipher.doFinal( content ) );
        } catch ( InvalidKeyException | BadPaddingException | IllegalBlockSizeException e ) {
            e.printStackTrace();
            throw new HttpInternalServerErrorException( Message.INTERNAL_SERVER_ERROR );
        }
    }


    @Override
    public String decrypt( String content ) {
        return new String( decrypt( content.getBytes() ) );
    }


    private void load() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if ( cipher == null ) {
            cipher = Cipher.getInstance( ALGORITHM );
        }

        if ( key == null ) {
            key = new SecretKeySpec( configurationHandler.getDatabaseConfig().getEncryptionKey().getBytes(), ALGORITHM );
        }
    }
}
