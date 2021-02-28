#!/bin/bash
cd kafka/bin
./zookeeper-server-start.sh ../config/zookeeper.properties &
./kafka-server-start.sh ../config/server.properties
