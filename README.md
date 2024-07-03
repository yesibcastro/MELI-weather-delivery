# MELI-weather-delivery
application that informs if the delivery of an order is delayed due to weather

Application implemented with Java 17 and Junit 5 and Gradle

Steps to execute the project

1. Clone the repository https://github.com/yesibcastro/MELI-weather-delivery.git

2. Open the project with Gradle

3. Run the project, this mail with port 8085

4. The exposed services are

curl --location 'http://localhost:8085/meli/weather?email=yesibcastro%40gmail.com&delivery-location=6.1543519%2C-75.6076758'

curl --location 'http://localhost:8085/meli/buyer-notification?email=yesibcastro%40gmail.com'

5. Import curls into Postamn

6. Consume the exposed services

7. The unit test report is located in build/jacocoHtml

