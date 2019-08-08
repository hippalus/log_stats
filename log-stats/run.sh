sleep 60
java -jar log-generator-1.0.jar &
sleep 3
java -jar log-listener-producer-1.0.jar kafka:9092 cityLog-raw

