# ContractConsumer

**A Brief overview about contract testing**

Contract testing, is a testing carried out between two parties (Producer & Consumer).
It has more focus on any changes from producer side, which should not cause failure on consumer side.
**It is NEITHER "Functional Testing", It is NOR "API Testing"**

A general example of producer and consumer.
- A Inventory service which produce data for order service
- Order service which consume data from inventory service and create order based on that

Another example (This is example, came to mind and created services for that :) :
- A Tweet/comment API generally produce data about various topic.
- Entertainment service (imdb kind of) which basically uses comments and tweets and show it. 

**Produce side**
- Tweet/Comment service produce data in json format (GET- localhost:8181/tweet/)
`[
    {
        "userID": "1",
        "userName": "@Austin174",
        "tweetDescription": "#GameOfThrones:Why is it that when one man builds a wall, the next man immediately needs to know what's on the other side?"
    },
    {
        "userID": "2",
        "userName": "@Thanh157",
        "tweetDescription": "#Friends:Raspberries? Good. Ladyfingers? Good. Beef? GOOD!"
    }
]`
- To invoke producer service `mvn clean spring-boot:run`
**Note:** Default it runs on "8181", you can changed it by providing from command line.

**Consumer Side**
- Entertainment service create shows belong to various categories. (POST- localhost:8081/showlocalhost:8081/show)
`{
  "showID":"3",
  "categoryName":"Non-Fictions",
  "showName":"Friends"
}`
- Fetch all created shows (GET- localhost:8081/show)
- It fetches data from tweet services, and showed filtered tweet in th form of Posts (GET- localhost:8081/posts/{showID
})
- To Invoke consumer service `mvn clean spring-boot:run`

**Testing side of Produce and Consumer sides:**
***Consumer Side***
- Invoke `mvn clean test`, It will run all tests (UT/IT/Contract tests) at consumer side.
- Pact file will be stored in '/target/pact'
- Generated pact file to be shared with Publisher (Upload shared location github/s3) or use
cool way of it doing it using pact-broker (For further info Please refer [https://github.com/DiUS/pact_broker-docker]).
- To publish document on pact broker `mvn pact:publish`

***Publisher Side***
- Invoke `mvn clean test`, It will run all tests (UT/IT) at producer side.
- To invoke contract tests to producer side `mvn pact:verify` (Provided you share location either pactFile/pactUrl in
 maven-producer-plugin).
 
 **Further improvements**
 - Setting up webhooks to pact-broker, so it will shoot contract tests seamlessly between producer and consumer.  
 
 **More about contact testing:**
 - [https://docs.pact.io/][]:
 - [https://github.com/DiUS/pact-jvm/tree/master/consumer][]:
 - [https://github.com/DiUS/pact-jvm/tree/master/provider][]:
 - [https://github.com/thombergs/code-examples/tree/master/pact][]:
 - [https://github.com/bitweft/consumer-driven-contract-testing][]:
