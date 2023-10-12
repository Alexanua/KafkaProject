package org.example;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private static final String API_URL = "http://localhost:8081/api/v1/kafka/";

    public static void main(String[] args) {
        userMenu();
    }

    public static void userMenu() {
        Scanner scan = new Scanner(System.in);
        String userChoice;

        do {
            printMenu();

            System.out.print("Gör ditt val: ");
            userChoice = scan.nextLine();

            switch (userChoice) {
                case "1":
                    userInputForKafka("user");
                    break;
                case "2":
                    userInputForKafka("admin");
                    break;
                case "3":
                    userInputForKafka("employee");
                    break;
                case "4":
                    System.out.print("Enter User ID to fetch: ");
                    Long id = scan.nextLong();
                    fetchDataFromWebAPIById(API_URL, "user", id);
                    break;
                case "5":
                    System.out.print("Enter Admin ID to fetch: ");
                    Long adminId = scan.nextLong();
                    scan.nextLine(); // Lägg till denna rad för att konsumera newline-tecknet
                    fetchDataFromWebAPIById(API_URL, "admin", adminId);
                    break;
                case "6":
                    System.out.print("Enter Employee ID to fetch: ");
                    Long employeeId = scan.nextLong();
                    scan.nextLine(); // Lägg till denna rad för att konsumera newline-tecknet
                    fetchDataFromWebAPIById(API_URL, "employee", employeeId);
                    break;
                case "7":
                    consumeFromKafka("user");
                    break;
                case "8":
                    consumeFromKafka("admin");
                    break;
                case "9":
                    consumeFromKafka("employee");
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Felaktig input. Försök igen");
            }

        } while (!userChoice.equals("0"));
    }

    public static void printMenu() {
        System.out.println("Gör dit val!");
        System.out.println("------------");
        System.out.println("1. Skriv User data till Kafka Server via Spring Boot");
        System.out.println("2. Skriv Admin data till Kafka Server via Spring Boot");
        System.out.println("3. Skriv Employee data till Kafka Server via Spring Boot");
        System.out.println("4. Hämta User data från Kafka Server via Spring Boot");
        System.out.println("5. Hämta Admin data från Kafka Server via Spring Boot");
        System.out.println("6. Hämta Employee data från Kafka Server via Spring Boot");
        System.out.println("7. Konsumera User data från Kafka");
        System.out.println("8. Konsumera Admin data från Kafka");
        System.out.println("9. Konsumera Employee data från Kafka");
        System.out.println("0. Avsluta");
    }

    public static void userInputForKafka(String type) {
        Scanner scanner = new Scanner(System.in);
        JSONObject json = new JSONObject();

        System.out.print("Enter first name for " + type + ": ");
        String firstName = scanner.nextLine();
        json.put("firstName", firstName);

        System.out.print("Enter last name for " + type + ": ");
        String lastName = scanner.nextLine();
        json.put("lastName", lastName);

        if ("admin".equals(type)) {
            System.out.print("Enter name for Admin: ");
            String name = scanner.nextLine();
            json.put("name", name);

            System.out.print("Enter role for Admin: ");
            String role = scanner.nextLine();
            json.put("role", role);
        } else if ("employee".equals(type)) {
            System.out.print("Enter name for Employee: ");
            String name = scanner.nextLine();
            json.put("name", name);

            System.out.print("Enter department for Employee: ");
            String department = scanner.nextLine();
            json.put("department", department);
        }

        sendToWebAPI(API_URL + type, json);
    }

    public static void sendToWebAPI(String url, JSONObject payload) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(payload.toJSONString());
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getCode();
                System.out.println("Response Status: " + statusCode);

                if (statusCode == 200) {
                    String responseContent = EntityUtils.toString(response.getEntity());
                    System.out.println(responseContent);
                } else {
                    System.out.println("Unexpected response from the server.");
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error while communicating with the server.");
            e.printStackTrace();
        }
    }

    public static void fetchDataFromWebAPIById(String baseUrl, String type, Long id) {
        String url = baseUrl + type + "/byid?id=" + id;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getCode();
                System.out.println("Response Status: " + statusCode);

                if (statusCode == 200) {
                    String responseContent = EntityUtils.toString(response.getEntity());
                    System.out.println(responseContent);
                } else {
                    System.out.println("Unexpected response from the server.");
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error while communicating with the server.");
            e.printStackTrace();
        }
    }

    private static void consumeFromKafka(String topic) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-" + System.currentTimeMillis());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(topic));

            System.out.println("Listening to topic: " + topic);
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));

            records.forEach(record -> {
                System.out.printf("Consumed record with key %s and value %s%n", record.key(), record.value());
            });
        }
    }
}
