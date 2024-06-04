## TransitKt

### これは何
Yahooから乗り換え案内を取得してきます

### Install with docker
```yaml
services:
  app:
    image: docker.io/nexryai/transit:latest
    restart: always
    environment:
      - HOST=0.0.0.0
    ports:
      - 127.0.0.1:3000:8080
```