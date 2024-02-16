package com.example.pidev.service;


import com.example.pidev.dto.TicketDto;
import com.example.pidev.dto.TicketResponseDto;
import com.example.pidev.entities.Ticket;
import com.example.pidev.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketRepository ticketRepository;


    @Value("${nom.de.utlisateur}")
    private String username;

    @Value("${api.token}")
    private String password;
    //private String domain= "firasdhahri";
    @Value("${domain.name}")
    private String domain;

    //private String projectKey = "SALAM";
    private String projectLead = "712020:286e61dc-4de8-4dd8-b55c-daee6fd45fb4";

    @Override
    public List<Ticket> getallticekts() {
        List<Ticket> tickets = new ArrayList<>();
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
                    List<Ticket> fetchedTickets = ticketResponseDto.getIssues();
                    for (Ticket ticket : fetchedTickets) {
                        // Check if the ticket already exists in the database based on its key
                        boolean exists = ticketRepository.existsByKey(ticket.getKey());
                        if (!exists) {
                            // If the ticket doesn't exist, save it
                            ticketRepository.save(ticket);
                        } else {
                            // If the ticket already exists, you can choose to update it or ignore it
                            // For now, let's just print a message
                            System.out.println("Ticket with key " + ticket.getKey() + " already exists in the database.");
                        }
                    }
                    tickets.addAll(fetchedTickets); // Add all fetched tickets to the list
                } else {
                    System.out.println("Response body is null");
                }
            } else {
                System.out.println("Failed to retrieve issues. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return tickets;
    }


    @Override
    public Ticket createIssue(TicketDto ticketDto) {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/api/2/issue";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth(username, password);
            // sprintname = ticketRepository.findTicketBySprints(name)
            String requestBody = "{\"fields\": {" +
                    "\"project\": {\"key\": \"" + ticketDto.getKey() + "\"}," +
                    "\"summary\": \"" + ticketDto.getFields().getSummary() + "\"," +
                    "\"description\": \"" + ticketDto.getFields().getDescription() + "\"," +
                    "\"issuetype\": {\"name\": \"" + ticketDto.getIssueType() + "\"}" +
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
    public String createProject(String projectKey, String projectName) {
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

    @Override
    public Ticket updateIssueByKey(String issueKey, String summary, String description) {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/api/2/issue/" + issueKey;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth(username, password);

            String requestBody = "{\"fields\": {" +
                    "\"summary\": \"" + summary + "\"," +
                    "\"description\": \"" + description + "\"" +
                    "}}";

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Ticket> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, Ticket.class);
            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                Ticket updatedIssue = response.getBody();
                if (updatedIssue != null) {
                    return updatedIssue;
                } else {
                    System.out.println("Updated issue response body is null");
                }
            } else {
                System.out.println("Failed to update issue. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error updating issue: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteIssue(String issueKey) {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/api/2/issue/" + issueKey;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth(username, password);

            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, requestEntity, Void.class);

            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                return true; // Issue deleted successfully
            } else {
                System.out.println("Failed to delete issue. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error deleting issue: " + e.getMessage());
        }
        return false;
    }
}



