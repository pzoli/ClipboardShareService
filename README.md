# Clipboard share spring service
This is a Spring boot microservice for share clipboard content.
Clients:
- https://github.com/pzoli/ClipboardShare

## Build

Build project with maven:

mvn package

## Usage

### Make an application.properties file

Content:
server.address=192.168.1.139
server.port=8082

Where server.address is the address of the service and server.port is the port where listening the service.
(See example in project /src/main/resources/application.properties)

### Start microservice with arguments

java -jar ClipboardShareService.jar --spring.config.location=[location]/application.properties

Then start your web browser and open QR code generator page like

- http://192.168.1.139:8082/clipboard/qrcode/get (for get clipboard content from PC to your android device)
- http://192.168.1.139:8082/clipboard/qrcode/post (for post content from your android clipboard to PC)

Then scann the image with your client program and enjoy the shared content.

# Swagger docs

https://medium.com/@hala3k/setting-up-swagger-3-with-spring-boot-2-a7c1c3151545

You can access Swagger docs of RestApi

- http://192.168.1.139:8082/v2/api-docs
- http://192.168.1.139:8082/swagger-ui/index.html

