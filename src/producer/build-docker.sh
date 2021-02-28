sudo docker build --tag producer:latest . 
docker rm producer1
sudo docker run -d --name producer1 -p 8081:8081 -t producer:latest