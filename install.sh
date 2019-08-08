mkdir elasticsearch
mkdir elasticsearch/data
chmod 777 elasticsearch/data
#git clone https://github.com/hisler34/log_stats.git
cd log_stats/log-stats
find . -maxdepth 1 -type d \( ! -name . \) -exec bash -c "cd '{}' && pwd && mvn -Dmaven.test.skip=true package" \;
