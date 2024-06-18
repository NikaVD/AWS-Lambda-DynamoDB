package com.veronica.aws.lambda.ass2;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.veronica.aws.lambda.ass2.dto.Customer;

public class CreateCustomer {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBAsyncClientBuilder.defaultClient());

    public APIGatewayProxyResponseEvent createCustomer(APIGatewayProxyRequestEvent request) throws JsonProcessingException {
        Customer customer = objectMapper.readValue(request.getBody(), Customer.class);

        Table table = dynamoDB.getTable(System.getenv("CUSTOMERS_TABLE"));
        Item item = new Item().withPrimaryKey("id", customer.id)
                .withString("firstName", customer.firstName)
                .withString("lastName", customer.lastName)
                .withInt("rewardPoints", customer.rewardPoints);
        table.putItem(item);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Order ID: " + customer.id);
    }
}
