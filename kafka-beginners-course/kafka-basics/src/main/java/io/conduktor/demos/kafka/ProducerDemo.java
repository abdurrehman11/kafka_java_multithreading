package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Producing messages from Java Kafka");

        // create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        for (int i=0; i<10; i++) {

            // create a producer record
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>("java_demo", "hello world!" + i);

            // send the data - asynchronous
            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    // execute every time a record is successfully sent or an exception is thrown
                    if (exception == null) {
                        // record was successfully sent
                        log.info(
                                "Received new metadata/ \n" +
                                        "Topic: " + metadata.topic() + "\n" +
                                        "Partition: " + metadata.partition() + "\n" +
                                        "Offset: " + metadata.offset() + "\n" +
                                        "Timestamp: " + metadata.timestamp()
                        );
                    } else {
                        log.error("Error while producing: ", exception);
                    }
                }
            });

            // to fool the kafka that a full batch has been processed, give a delay of 1sec
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        // flush data - synchronous
        producer.flush();

        // flush and close producer
        producer.close();
    }
}
