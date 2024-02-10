package com.example.pidev.service;


import com.example.pidev.entities.JiraResponse2;
import com.example.pidev.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SprintServiceImpl implements SprintService{
    @Autowired
    SprintRepository sprintRepository;
    private String username = "firas.dhahri@esprit.tn";

    private String password="ATATT3xFfGF0ustaCfCOnepC0FS2CHzjxgRD_KvC3JMhn9J9CviaWSR0Jy-nASRrdOJejMIj5P--xnqlAYkzWVnpgHM0NNcbFe5JxgMr3Ql67yADknuwxFcnrYyQCoWMk3NeJkzwcjsQp7BZ_NH916_o03abCS9T7yR3f7i2qDXSdbg74nnoj2s=5CA19B73";

    private String domain= "firasdhahri";
    @Override
    public JiraResponse2 getAllSprints(long boardId) {
            try  {
                String baseUrl = "https://" + domain + ".atlassian.net/rest/agile/1.0/board/" + boardId + "/sprint";
                String auth = username + ":" + password;
                byte[] authBytes = auth.getBytes();
                String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

                org.springframework.http.HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Authorization", "Basic " + base64Creds);


                HttpEntity<String> entity = new HttpEntity<>(headers);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<JiraResponse2> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity,  JiraResponse2.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    JiraResponse2 jiraResponse2 = response.getBody();
                    if (jiraResponse2 != null) {
                        return jiraResponse2;

                    } else {
                        System.out.println("Response body is null");
                    }
                } else {
                    System.out.println("Failed to retrieve issues. Status code: " + response.getStatusCodeValue());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        return null; // Return null if there's an error
    }

}

