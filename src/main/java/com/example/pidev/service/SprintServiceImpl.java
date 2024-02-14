package com.example.pidev.service;


import com.example.pidev.dto.SprintResponseDto;
import com.example.pidev.entities.Sprint;
import com.example.pidev.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SprintServiceImpl implements SprintService{
    @Autowired
    SprintRepository sprintRepository;
    //private String username = "firas.dhahri@esprit.tn";
    @Value("${nom.de.utlisateur}")
    private String username;

    @Value("${api.token}")
    private String password;
    //private String domain= "firasdhahri";
    @Value("${domain.name}")
    private String domain;

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

    @Override
    public Sprint createSprint(long boardId, String name, String startDate, String endDate,String goal) {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/agile/1.0/sprint";
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes();
            String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + base64Creds);

            // Construct the request body
            String requestBody = "{" +
                    "\"originBoardId\": \"" + boardId + "\", " +
                    "\"name\": \"" + name + "\", " +
                    "\"startDate\": \"" + startDate + "\", " +
                    "\"goal\": \"" + goal + "\", " +
                    "\"endDate\": \"" + endDate + "\"}";

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Sprint> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Sprint.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                Sprint sprint = response.getBody();
                if (sprint != null) {
                    return sprint;
                } else {
                    System.out.println("Created sprint response body is null");
                }
            } else {
                System.out.println("Failed to create sprint. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error creating sprint: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Sprint updateSprint(long sprintId, String name, String startDate, String endDate, String goal,Long boardId, String state,String createdDate) {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/agile/1.0/sprint/" + sprintId;
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes();
            String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + base64Creds);

            // Construct the request body
            String requestBody = "{" +
                    "\"originBoardId\": \"" + boardId + "\", " +
                    "\"self\": \"" + baseUrl + "\", " +
                    "\"state\": \"" + state + "\", " +
                    "\"id\": \"" + sprintId + "\", " +
                    "\"createdDate\": \"" + createdDate + "\", " +
                    "\"name\": \"" + name + "\", " +
                    "\"startDate\": \"" + startDate + "\", " +
                    "\"goal\": \"" + goal + "\", " +
                    "\"endDate\": \"" + endDate + "\"}";

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Sprint> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, entity, Sprint.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                Sprint sprint = response.getBody();
                if (sprint != null) {
                    return sprint;
                } else {
                    System.out.println("Updated sprint response body is null");
                }
            } else {
                System.out.println("Failed to update sprint. Status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.out.println("Error updating sprint: " + e.getMessage());
        }
        return null;

    }
    @Override
    public boolean deleteSprint(long sprintId) {
        try {
            String baseUrl = "https://" + domain + ".atlassian.net/rest/agile/1.0/sprint/" + sprintId;
            String auth = username + ":" + password;
            byte[] authBytes = auth.getBytes();
            String base64Creds = java.util.Base64.getEncoder().encodeToString(authBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + base64Creds);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, entity, Void.class);
            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                return  true;
            } else {
                return  false;
            }
        } catch (Exception e) {
            return  false;
        }

    }

}



