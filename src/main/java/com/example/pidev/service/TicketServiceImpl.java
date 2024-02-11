package com.example.pidev.service;


import com.example.pidev.dto.TicketResponseDto;
import com.example.pidev.entities.Ticket;
import com.example.pidev.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TicketServiceImpl implements  TicketService {
    @Autowired
    TicketRepository ticketRepository;


    private String username = "firas.dhahri@esprit.tn";

    private String password="ATATT3xFfGF08ptwyWCsPIXe1lptgddAdwActSkgqU4K_frCy_6wiTElK3rJCw_40tWoVM6T7IaTPYgpCGptZltlDyCH5hxDlyVHZ9ZzXUXEN-NSkxgpKXJrXmxsVDcN00ZFwoWdh_I9uT3yrSWkfjmmMFa-sHGr0LblNABZfdQS9zlEnnrogaQ=9FBD91C7";

    private String domain= "firasdhahri";
    private String projectKey = "SPRING8";
    private String projectLead = "712020:286e61dc-4de8-4dd8-b55c-daee6fd45fb4";

    @Override
    public List<Ticket> getallticekts() {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/api/2/search";
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes();
            String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + base64Creds);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<TicketResponseDto> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, TicketResponseDto.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                TicketResponseDto ticketResponseDto = response.getBody();
                if (ticketResponseDto != null) {
                    List<Ticket> ticket = ticketResponseDto.getIssues();
                    ticketRepository.saveAll(ticketResponseDto.getIssues());
                    return ticket;

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



   /* @Override
    public Ticket createIssue(String key, String issueType, String summary, String description) {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/api/2/issue";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth(username, password);

            String requestBody = "{\"fields\": {" +
                    "\"project\": {\"key\": \"" + key + "\"}," +
                    "\"summary\": \"" + summary + "\"," +
                    "\"description\": \"" + description + "\"," +
                    "\"issuetype\": {\"name\": \"" + issueType + "\"}" +
                    "}}";

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Ticket> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, Ticket.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                Ticket createdIssue = response.getBody();
                if (createdIssue != null) {
                    return createdIssue;
                } else {
                    System.out.println("Created issue response body is null");
                }
            } else {
                System.out.println("Failed to create issue. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error creating issue: " + e.getMessage());
        }
        return null;
    }*/
   @Override
   public Ticket createIssue(String key, String issueType, String summary, String description) {
       try {
           String baseUrl = "https://" + domain + ".atlassian.net/rest/api/2/issue";

           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);
           headers.setBasicAuth(username, password);

           String requestBody = "{\"fields\": {" +
                   "\"project\": {\"key\": \"" + key + "\"}," +
                   "\"summary\": \"" + summary + "\"," +
                   "\"description\": \"" + description + "\"," +
                   "\"issuetype\": {\"name\": \"" + issueType + "\"}" +
                   "}}";

           HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

           RestTemplate restTemplate = new RestTemplate();
           ResponseEntity<Ticket> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, Ticket.class);
           if (response.getStatusCode() == HttpStatus.CREATED) {
               Ticket createdIssue = response.getBody();
               if (createdIssue != null) {
                   return createdIssue;
               } else {
                   System.out.println("Created issue response body is null");
               }
           } else {
               System.out.println("Failed to create issue. Status code: " + response.getStatusCodeValue());
           }
       } catch (Exception e) {
           System.out.println("Error creating issue: " + e.getMessage());
       }
       return null;
   }
    @Override
    public String createProject(String projectName) {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/api/3/project";
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes();
            String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + base64Creds);

            String requestBody = "{\"key\": \"" + projectKey + "\", " +
                    "\"name\": \"" + projectName + "\", " +
                    "\"projectTypeKey\": \"software\"," +
                    "\"leadAccountId\": \"" + projectLead + "\"}";

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                return projectKey; // Assuming project key is returned upon successful creation
            } else {
                System.out.println("Failed to create project. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error creating project: " + e.getMessage());
        }
        return null;
    }
}