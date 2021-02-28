FROM adoptopenjdk
COPY config /opt/app/config
COPY start.sh /opt/app
WORKDIR /opt/app
EXPOSE 2181 9092 9093 9094 
RUN apt-get update -y \
&& apt-get install -y iputils-ping \
&& apt-get install net-tools -y \
&& curl https://downloads.apache.org/kafka/2.7.0/kafka_2.13-2.7.0.tgz -O \
&& tar -xvf kafka_2.13-2.7.0.tgz -C . \
&& mv kafka_2.13-2.7.0 kafka \
&& cp config/* kafka/config/ \
&& chmod +x start.sh
ENTRYPOINT ["./start.sh"]
