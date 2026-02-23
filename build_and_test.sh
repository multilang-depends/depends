#/usr/bin/bash
git submodule update --init utils
cd utils && mvn install && cd ..
./lib_install.sh
mvn package -Dtest='!depends.extractor.cpp.**'

java -jar target/depends-0.9.8-jar-with-dependencies.jar --help | head
