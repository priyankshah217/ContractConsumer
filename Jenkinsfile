pipeline {
  agent any
  stages {
    stage('Fetch from GitHub') {
      steps {
        git(url: 'https://github.com/priyankshah217/ContractConsumer.git', branch: 'master', changelog: true, poll: true)
      }
    }
    stage('') {
      steps {
        sh './mvnw clean test pact:publish'
      }
    }
  }
}