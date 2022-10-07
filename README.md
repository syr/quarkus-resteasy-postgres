# quarkus-resteasy-postgres Project

setup:    quarkus app -> otel collector -> jaeger-aio
            
how to start:

- start otel collector and jaeger-aio: `src/main/resources: docker compose up`
- generate traces by using quarkus app: http://localhost:8080/test/
- check traces in jaeger ui: http://localhost:16686
  


    
