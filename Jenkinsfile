pipeline {
  agent {
    node {
      label 'localNode'
    }

  }
  stages {
    stage('Fetch from GitHub') {
      steps {
        git(url: 'https://github.com/priyankshah217/ContractConsumer.git', branch: 'master', changelog: true, poll: true)
      }
    }
    stage('Run Tests') {
      steps {
        sh './mvnw clean test pact:publish'
      }
    }
  }
}