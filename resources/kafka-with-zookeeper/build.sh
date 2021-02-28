sudo docker build --tag kafka1:latest --file ./Dockerfile --rm .  
sudo docker stop kafka1
sudo docker rm -v kafka1
sudo docker run -d --name kafka1 -h kafka1 -p 2181:2181 -p 9092:9092 -t kafka1:latest --rm
