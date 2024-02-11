package com.example.pidev.service;


import com.example.pidev.dto.SprintResponseDto;
import com.example.pidev.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SprintServiceImpl implements SprintService{
    @Autowired
    SprintRepository sprintRepository;
    private String username = "firas.dhahri@esprit.tn";

    private String password="ATATT3xFfGF08ptwyWCsPIXe1lptgddAdwActSkgqU4K_frCy_6wiTElK3rJCw_40tWoVM6T7IaTPYgpCGptZltlDyCH5hxDlyVHZ9ZzXUXEN-NSkxgpKXJrXmxsVDcN00ZFwoWdh_I9uT3yrSWkfjmmMFa-sHGr0LblNABZfdQS9zlEnnrogaQ=9FBD91C7";

    private String domain= "firasdhahri";
    @Override
    public SprintResponseDto getAllSprints(long boardId) {
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
                ResponseEntity<SprintResponseDto> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity,  SprintResponseDto.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    SprintResponseDto sprintResponseDto = response.getBody();
                    if (sprintResponseDto != null) {
                        return sprintResponseDto;

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

