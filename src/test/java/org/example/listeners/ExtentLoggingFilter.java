package org.example.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ExtentLoggingFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test != null) {
            // Log request details
            test.log(Status.INFO, "Request Method: " + requestSpec.getMethod());
            test.log(Status.INFO, "Request URI: " + requestSpec.getURI());

            // Log request headers (mask sensitive ones)
            String maskedHeaders = maskSensitiveHeaders(requestSpec.getHeaders().toString());
            test.log(Status.INFO, "Request Headers: " + maskedHeaders);
            if (requestSpec.getBody() != null) {
                test.log(Status.INFO, "Request Body: " + requestSpec.getBody());
            }

            // Execute the request
            Response response = ctx.next(requestSpec, responseSpec);

            // Log response details
            test.log(Status.INFO, "Response Status: " + response.getStatusCode() + " " + response.getStatusLine());
            // Log response headers (mask sensitive ones)
            String maskedResponseHeaders = maskSensitiveHeaders(response.getHeaders().toString());
            test.log(Status.INFO, "Response Headers: " + maskedResponseHeaders);
            test.log(Status.INFO, "Response Body: " + response.getBody().asString());

            return response;
        } else {
            return ctx.next(requestSpec, responseSpec);
        }
    }

    private String maskSensitiveHeaders(String headers) {
        // Implement your header masking logic here
        // For example, you can use regex to mask values of specific headers
        return headers.replaceAll("(?i)(authorization|cookie|set-cookie):\\s?[^\\s]+", "$1: [PROTECTED]");
    }
}
