# sometotest

>Learning is like rowing upstream: not to advance is to drop back.

A collection of test modules to test different small java frameworks

|modules                                        |
|:----------------------------------------------|
|[test-activiti](#test-activiti)                |
|[test-base64](#test-base64)                    |
|[test-eagent-client](#test-eagent-client)      |
|[test-eagent-server](#test-eagent-server)      |
|[test-httpClient](#test-httpclient)            |
|[test-jetty](#test-jetty)                      |
|[test-json](#test-json)                        |
|[test-module](#test-module)                    |
|[test-selenium](#test-selenium)                |
|[test-selenium2](#test-selenium2)              |
|[test-sikulix](#test-sikulix)                  |
|[test-tplan](#test-tplan)                      |



## test-activiti
tests business process engine activiti

## test-base64
tests java.util.base64 to
 1. read .png file
 2. encode file as base64 string
 3. write encoded text to file
 4. read encoded text from file
 5. decode text
 
## test-eagent-client
a combination of activiti, selenium & sikulix with jetty http Server and Client to recieve messages from test module test-eagent-server

## test-eagent-server
a gui with jetty http Server and Client to work with test module test-eagent-client

## test-httpClient
tests apache httpClient features
* sends .png file as multipartFormData
* sends .png file as base64 encoded string embedded in a json
* sends a string to a basic auth secured http endpoint
* sends a string to an ssl secured https endpoint accepts self signed
* sends a string to an ssl secured https endpoint accepts certs in truststore

all features can be tested by sending them to test module test-jetty

## test-jetty
tests jetty server different handlers:
* recieves and saves multipartFormData
* recieves json, extracts base64 encoded string, decodes and saves as .png file
* recieves a post request on a basic auth secured endpoint
* recieves a post request on an ssl secured endpoint (self signed)

all handlers can be tested with requests from test module test-httpClient 

## test-json
tests google gson framework

## test-module
tests java file writing and reading from resources and from external files

## test-selenium
tests selenium framework

## test-selenium2
tests selenium to attach to an open Browser window  
*attaching to an open browser not opened by selenium is not working currently*

## test-sikulix
tests sikulix framework

## test-tplan *not working without licence*
tests tplan framework


