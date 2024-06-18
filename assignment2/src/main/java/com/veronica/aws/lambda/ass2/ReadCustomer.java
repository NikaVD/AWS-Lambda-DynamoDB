package com.veronica.aws.lambda.ass2;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.veronica.aws.lambda.ass2.dto.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class ReadCustomer {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBAsyncClientBuilder.defaultClient();
    public APIGatewayProxyResponseEvent readCustomer (APIGatewayProxyRequestEvent request) throws JsonProcessingException {

        ScanResult scanResult = amazonDynamoDB.scan(new ScanRequest().withTableName(System.getenv("CUSTOMERS_TABLE")));
        List<Customer> orderList = scanResult.getItems().stream()
                .map(item->new Customer(
                        Integer.parseInt(item.get("id").getN()),
                        item.get("firstName").getS(),
                        item.get("lastName").getS(),
                        Integer.parseInt(item.get("rewardPoints").getN())
                )).collect(Collectors.toList());


        String jsonOutput = objectMapper.writeValueAsString(orderList);

        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonOutput);
    }
}
