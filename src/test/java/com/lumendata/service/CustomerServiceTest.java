package com.lumendata.service;

import com.lumendata.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    public void shouldSaveEmailWithIdentityMessage() throws IOException {
        customerService.processMessage(getTestData());
        Mockito.verify(customerRepository,Mockito.times(1))
                .findByPartyUid(Mockito.anyString());
        Mockito.verify(customerRepository,Mockito.times(1))
                .deleteAll(Mockito.any());
        Mockito.verify(customerRepository,Mockito.times(1))
                .saveAll(Mockito.any());
    }

    private String getTestData() {
        return "{\n" +
                "   \"guid\":\"guid-1299888\",\n" +
                "  \"emails\":[\n" +
                "      {\n" +
                "       \"email\" :\"srini@lumendata.com\",\n" +
                "       \"logicalDeleteFlg\": \"N\",\n" +
                "        \"useType\":\"Office\",\n" +
                "       \"sourceId\":\"9976544\",\n" +
                "       \"source\": \"XYZ\"\n" +
                "      }\n" +
                "  ],\n" +
                "  \"identifications\":[\n" +
                "      {\n" +
                "          \"nationalID\": \"Test-5027\",\n" +
                "          \"nationalIDType\": \"UCB_Identikey\"\n" +
                "      },\n" +
                "       {\n" +
                "          \"nationalID\": \"Test-5028\",\n" +
                "          \"nationalIDType\": \"UCB_UUID\"\n" +
                "      }\n" +
                "  ]\n" +
                "}";
    }

}
