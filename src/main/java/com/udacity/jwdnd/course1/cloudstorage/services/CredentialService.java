package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.common.CloudStorageException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    CredentialMapper credentialMapper;
    EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }


    public List<Credential> getCredentials(Integer userId) {
        return credentialMapper.getCredentialList(userId);
    }


    public Integer addCredential(Credential credential) {

        setEncodedPassword(credential);
        return credentialMapper.createCredential(credential);
    }


    public Credential getCredential(Integer userId, Integer credentialId) {

        Credential credential = credentialMapper.getCredential(userId, credentialId);

        if (credential == null) {
            throw new CloudStorageException("Credential could not be accessed.");
        }

        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(decryptedPassword);

        return credential;

    }


    public Integer deleteCredential(Integer userId, Integer credentialId) {
        return credentialMapper.deleteCredential(userId, credentialId);
    }


    public void editCredential(Credential credential) {
        setEncodedPassword(credential);
        this.credentialMapper.updateCredential(credential);
    }


    private void setEncodedPassword(Credential credential)
    {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
    }

    public Integer getCredentialIdByUsername(Credential credential) {
        return credentialMapper.getCredentialIdByUsername(credential);
    }
}
