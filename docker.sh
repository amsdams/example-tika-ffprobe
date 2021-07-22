docker-machine ls
docker container ls
docker-machine ip
mvn clean package

docker build -t amsdams/filevalidation .
docker run -p 8080:8080 amsdams/filevalidation
