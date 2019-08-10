#storm jar  log-flow-1.0.jar org.apache.storm.flux.Flux --remote src/main/config/flux/config.yml
##storm jar  log-flow-1.0.jar com.log.stats.logflow.LogFlowApplication
#docker run -it -v log-flow-1.0.jar:/log-flow-1.0.jar  storm storm jar /log-flow-1.0.jar  com.log.stats.logflow.LogFlowApplication kafka:9092 elasticsearch

docker run -it -v $(pwd)/log-flow-1.0.jar:/log-flow-1.0.jar  storm storm jar /log-flow-1.0.jar  com.log.stats.logflow.LogFlowApplication
