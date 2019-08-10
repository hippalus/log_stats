mkdir elasticsearch
mkdir elasticsearch/data
chmod 777 elasticsearch/data
#git clone https://github.com/hisler34/log_stats.git
cd log-stats/
find . -maxdepth 1 -type d \( ! -name . \) -exec bash -c "cd '{}' && pwd && mvn clean package" \;
cd log-flow/
cd ..
docker build -t combined-services .
cd log-stats-interface/
docker build -t log-stats .
cd ../../
docker-compose up

