# Desafio Técnico Muralis

#### A solução foi desenhada e definida em 6 seis módulos:

* Módulo de Captura de Dados: Sensores IoT para captura das leituras/pressões em tempo real, utilizando o protocolo MQTT para melhor performance;
* Módulo de Gerenciamento: Paraleliza o processamento, identificando possíveis anomalias notificando aos responsáveis, e redistribui a informação para persistência e histórico;
* Módulo de Notificações: Envia através dos canais disponíveis os eventos existentes, notificando os responsáveis;
* Módulo de Processamento: Paraleliza o fechamento de faturas e envio aos clientes;
* Módulo de Persistência: Persiste as capturas para base de dados, incluindo dados históricos;
* Módulo de Histórico: API para fornecimento dos dados capturados, incluindo dados históricos.

Na imagem é ilustrada a arquitetura desenhada com o fluxo a integração dos módulos!

![arch_challenge_muralis.png](assets%2Farch_challenge_muralis.png)


## MVP

#### Foi construído neste desafio o Módulo de Gerenciamento (fonte -> spring-rabbitMQ-stream-manager). Objetivo de identificar possíveis anomalias (notificando), e redirecionando a mensagem para persistência.

#### Pré-requisitos:

* Docker 
* Maven
* JDK 21 (Java)

1.  Para subir o rabbitMQ, use o comando:

```shell for windows
docker run -it --rm --hostname rabbitmq --name rabbit-mq -p 15672:15672 -p 5672:5672 rabbitmq:3-management
```

2. Iniciar a aplicação, executar o comando de dentro da pasta -> spring-rabbitMQ-stream-manager:

```shell for windows
mvn spring-boot:run
```

3. Acessar a tela de gerenciamento do Rabbit na Url -> http://localhost:15672 com as credenciais: guest / guest

4. Publicar mensagens na devida fila e verificar na console o resultado da execução e verificar a inclusão das mensagens nas filas de persistencia e notificações (se for o caso de anomalia)

Ex.:

![example_1.png](assets%2Fexample_1.png)

![example_2.png](assets%2Fexample_2.png)

![example_3.png](assets%2Fexample_3.png)

![example_4.png](assets%2Fexample_4.png)

![example_5.png](assets%2Fexample_5.png)


### Estimativa e prazo do projeto:

![est_challenge_muralis.png](assets%2Fest_challenge_muralis.png)

