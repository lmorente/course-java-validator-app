package com.demo.validatorapp.service;

import com.demo.validatorapp.service.impl.UserDataValidatorServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserDataValidatorServiceTest {

    @InjectMocks
    UserDataValidatorServiceImpl userDataValidatorService;

    @Test
    public void correctMailAddresses() {
        List<String> mails = Arrays.asList("56_loop@dominio.com", "test@dominio.es", "user123-data@fake.com");
        mails.forEach( mail -> {
            Boolean result = userDataValidatorService.isAddressMail(mail);
            Assert.assertEquals(result, Boolean.TRUE);
        });
    }

    @Test
    public void incorrectMailAddresses() {
        List<String> mails = Arrays.asList("56.loop@dominiocom","test @dominio.es", "test", "user123-data@fake", "user.es");
        mails.forEach( mail -> {
            Boolean result = userDataValidatorService.isAddressMail(mail);
            Assert.assertEquals(result, Boolean.FALSE);
        });
    }

    @Test
    public void correctName() {
        List<String> names = Arrays.asList("María José Nuñez", "Amédée", "Châràctér", "Abälard", "Josèphe", "François", "D'hondt");
        names.forEach( name -> {
            Boolean result = userDataValidatorService.isName(name);
            Assert.assertEquals(result, Boolean.TRUE);
        });
    }

    @Test
    public void incorrectMailName() {
        List<String> mails = Arrays.asList("Juan_García", "Tom78", "Lewis.", "");
        mails.forEach( mail -> {
            Boolean result = userDataValidatorService.isAddressMail(mail);
            Assert.assertEquals(result, Boolean.FALSE);
        });
    }

}
