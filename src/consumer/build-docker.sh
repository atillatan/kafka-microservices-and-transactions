sudo docker build -t consumer:latest . 
docker rm consumer1
sudo docker run --rm -d --name consumer1 -p 8082:8082 -t consumer:latest