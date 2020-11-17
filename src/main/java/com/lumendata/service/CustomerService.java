package com.lumendata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumendata.entity.EmailEntity;
import com.lumendata.model.Customer;
import com.lumendata.model.Email;
import com.lumendata.model.Identification;
import com.lumendata.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Component
@Slf4j
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    List<String> sourceList= Arrays.asList("CS","HR","ADVANCE");

    public void processMessage(String data) {
        String guid=null;
      try{
          ObjectMapper objectMapper=new ObjectMapper();
          objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
          Customer customer=objectMapper.readValue(data,Customer.class);
          List<EmailEntity> emailEntities=new ArrayList<>();
          List<EmailEntity> deleteEntity=new ArrayList<>();
          final Map<String,String> ucbMap= null!=customer.getIdentifications()?
                   customer.getIdentifications().stream().collect(Collectors.toMap(
                      Identification::getNationalIDType,Identification::getNationalID)):new HashMap<>();

          guid=customer.getGuid();
          customer.getEmails().forEach(email -> {
              if(!"Y".equalsIgnoreCase(email.getLogicalDeleteFlg())
                      && sourceList.contains(email.getSource()) && null!=ucbMap.get("UCB_UUID") &&
                      null!=ucbMap.get("UCB_Identikey")) {
                  EmailEntity customerEntity = getEmailEntity(customer, email);
                  customerEntity.setUcbId(ucbMap.get("UCB_UUID"));
                  customerEntity.setUcbIdentityKey(ucbMap.get("UCB_Identikey"));
                  emailEntities.add(customerEntity);
              }
          });
          customerRepository.deleteAll(customerRepository.findByPartyUid(guid));
          customerRepository.saveAll(emailEntities);
        }catch (Exception exception){
            log.error("Exception while saving customer data into mv-3==>customer table guid={}",guid,exception);
        }
    }

    private EmailEntity getEmailEntity(Customer customer, Email email) {
        EmailEntity customerEntity = new EmailEntity();
        customerEntity.setPartyUid(customer.getGuid());
        customerEntity.setSource(email.getSource());
        customerEntity.setSourceId(email.getSourceId());
        customerEntity.setEmailType(email.getUseType());
        customerEntity.setEmail(email.getEmail());
        return customerEntity;
    }
}
