package com.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/bfhl")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BfhlResource {

    // Example user details
    private final String fullName = "john_doe";
    private final String dob = "17091999";
    private final String email = "john@xyz.com";
    private final String rollNumber = "ABCD123";

    @POST
    public Response processInput(InputData inputData) {
        List<String> numbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        String highestLowercase = "";

        for (String item : inputData.getData()) {
            if (item.matches("\\d+")) {
                numbers.add(item);
            } else if (item.matches("[a-zA-Z]")) {
                alphabets.add(item);
                if (item.equals(item.toLowerCase()) && item.compareTo(highestLowercase) > 0) {
                    highestLowercase = item;
                }
            }
        }

        boolean fileValid = inputData.getFileB64() != null && !inputData.getFileB64().isEmpty();
        String fileMimeType = fileValid ? "image/png" : null; // Mocked MIME type
        int fileSizeKb = fileValid ? 400 : 0; // Mocked size

        ResponseData responseData = new ResponseData(
                true,
                String.format("%s_%s", fullName, dob),
                email,
                rollNumber,
                numbers,
                alphabets,
                highestLowercase.isEmpty() ? new ArrayList<>() : List.of(highestLowercase),
                fileValid,
                fileMimeType,
                fileSizeKb
        );

        return Response.ok(responseData).build();
    }

    @GET
    public Response getOperationCode() {
        return Response.ok(new OperationCode(1)).build();
    }

    // Input data class
    public static class InputData {
        private List<String> data;
        private String fileB64;

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public String getFileB64() {
            return fileB64;
        }

        public void setFileB64(String fileB64) {
            this.fileB64 = fileB64;
        }
    }

    // Response data class
    public static class ResponseData {
        private boolean isSuccess;
        private String userId;
        private String email;
        private String rollNumber;
        private List<String> numbers;
        private List<String> alphabets;
        private List<String> highestLowercaseAlphabet;
        private boolean fileValid;
        private String fileMimeType;
        private int fileSizeKb;

        public ResponseData(boolean isSuccess, String userId, String email, String rollNumber,
                            List<String> numbers, List<String> alphabets,
                            List<String> highestLowercaseAlphabet, boolean fileValid,
                            String fileMimeType, int fileSizeKb) {
            this.isSuccess = isSuccess;
            this.userId = userId;
            this.email = email;
            this.rollNumber = rollNumber;
            this.numbers = numbers;
            this.alphabets = alphabets;
            this.highestLowercaseAlphabet = highestLowercaseAlphabet;
            this.fileValid = fileValid;
            this.fileMimeType = fileMimeType;
            this.fileSizeKb = fileSizeKb;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }

        public String getRollNumber() {
            return rollNumber;
        }

        public List<String> getNumbers() {
            return numbers;
        }

        public List<String> getAlphabets() {
            return alphabets;
        }

        public List<String> getHighestLowercaseAlphabet() {
            return highestLowercaseAlphabet;
        }

        public boolean isFileValid() {
            return fileValid;
        }

        public String getFileMimeType() {
            return fileMimeType;
        }

        public int getFileSizeKb() {
            return fileSizeKb;
        }
    }

    // Operation code class
    public static class OperationCode {
        private int operationCode;

        public OperationCode(int operationCode) {
            this.operationCode = operationCode;
        }

        public int getOperationCode() {
            return operationCode;
        }
    }
}