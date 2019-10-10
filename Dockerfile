# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/executors.exoplanets*-jar-with-dependencies.jar exoplanets-catalog-analyzer.jar
ADD ./sample_data/*.json .

CMD java -jar exoplanets-catalog-analyzer.jar