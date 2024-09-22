package com.example;

import com.example.BfhlResource.InputData;
import com.example.BfhlResource.ResponseData;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BfhlResourceTest {

    @Test
    public void testProcessInputWithAlphabetsAndNumbers() {
        BfhlResource resource = new BfhlResource();
        InputData inputData = new InputData();
        inputData.setData(Arrays.asList("M", "1", "334", "4", "B", "Z", "a"));
        inputData.setFileB64("BASE_64_STRING");

        Response response = resource.processInput(inputData);
        ResponseData responseData = (ResponseData) response.getEntity();

        assertTrue(responseData.isSuccess());
        assertEquals("john_doe_17091999", responseData.getUserId());
        assertEquals("john@xyz.com", responseData.getEmail());
        assertEquals("ABCD123", responseData.getRollNumber());
        assertEquals(Arrays.asList("1", "334", "4"), responseData.getNumbers());
        assertEquals(Arrays.asList("M", "B", "Z", "a"), responseData.getAlphabets());
        assertEquals(Collections.singletonList("a"), responseData.getHighestLowercaseAlphabet());
        assertTrue(responseData.isFileValid());
        assertEquals("image/png", responseData.getFileMimeType());
        assertEquals(400, responseData.getFileSizeKb());
    }

    @Test
    public void testProcessInputWithOnlyNumbers() {
        BfhlResource resource = new BfhlResource();
        InputData inputData = new InputData();
        inputData.setData(Arrays.asList("2", "4", "5", "92"));
        inputData.setFileB64("BASE_64_STRING");

        Response response = resource.processInput(inputData);
        ResponseData responseData = (ResponseData) response.getEntity();

        assertTrue(responseData.isSuccess());
        assertEquals("john_doe_17091999", responseData.getUserId());
        assertEquals("john@xyz.com", responseData.getEmail());
        assertEquals("ABCD123", responseData.getRollNumber());
        assertEquals(Arrays.asList("2", "4", "5", "92"), responseData.getNumbers());
        assertTrue(responseData.getAlphabets().isEmpty());
        assertTrue(responseData.getHighestLowercaseAlphabet().isEmpty());
        assertTrue(responseData.isFileValid());
        assertEquals("image/png", responseData.getFileMimeType());
        assertEquals(400, responseData.getFileSizeKb());
    }

    @Test
    public void testProcessInputWithOnlyAlphabets() {
        BfhlResource resource = new BfhlResource();
        InputData inputData = new InputData();
        inputData.setData(Arrays.asList("A", "C", "Z", "c", "i"));

        Response response = resource.processInput(inputData);
        ResponseData responseData = (ResponseData) response.getEntity();

        assertTrue(responseData.isSuccess());
        assertEquals("john_doe_17091999", responseData.getUserId());
        assertEquals("john@xyz.com", responseData.getEmail());
        assertEquals("ABCD123", responseData.getRollNumber());
        assertTrue(responseData.getNumbers().isEmpty());
        assertEquals(Arrays.asList("A", "C", "Z", "c", "i"), responseData.getAlphabets());
        assertEquals(Collections.singletonList("i"), responseData.getHighestLowercaseAlphabet());
        assertTrue(!responseData.isFileValid());
    }

    @Test
    public void testGetOperationCode() {
        BfhlResource resource = new BfhlResource();
        Response response = resource.getOperationCode();
        BfhlResource.OperationCode operationCode = (BfhlResource.OperationCode) response.getEntity();

        assertEquals(1, operationCode.getOperationCode());
    }
}