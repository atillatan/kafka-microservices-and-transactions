sudo docker build -t kafka1:latest -f ./Dockerfile --rm .  
sudo docker run -d --name kafka1 -h kafka1 -p 2181:2181 -p 9092:9092 -t kafka1:latest
