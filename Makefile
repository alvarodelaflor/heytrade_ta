java:
	mvn spring-boot:run

docker:
	mvn clean install -X
	sudo docker build .
	sudo docker-compose build
	sudo docker-compose up