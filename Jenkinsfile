pipeline {
  agent any
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
    stage('Archive Results') {
      steps {
        junit(allowEmptyResults: true, keepLongStdio: true, testResults: '**/target/surefire-reports/TEST-*xml')
      }
    }
  }
}