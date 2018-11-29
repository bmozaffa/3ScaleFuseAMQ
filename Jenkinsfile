import groovy.json.JsonOutput

def gitRepo = params.GIT_REPO
def gitBranch = params.GIT_BRANCH != null && params.GIT_BRANCH != "" ? params.GIT_BRANCH : "master"

node('nodejs') {
  // Get Source Code from SCM (Git) as configured in the Jenkins Project
  stage('Checkout Source') {
    // For Jenkinsfile from GIT
    checkout scm
    // for inline scripts
    //git url: gitRepo, branch: gitBranch
  }
     
     
        stage("Compile") {
            
                sh "cd fisuser-service"
                sh "mvn clean package -DskipTests "
                sh "cd .."
                
                
                sh "cd fisalert-service"
                sh "mvn clean package -DskipTests "
                sh "cd .."        
                
                sh "cd fisuser-service"
                sh "mvn clean package -DskipTests "
        
                
            }

            stage("Test") {
                sh "mvn test"
            }
            
            

}
