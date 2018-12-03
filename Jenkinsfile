podTemplate(
    inheritFrom: "maven", 
    label: "myJenkins", 
    cloud: "openshift", 
    ) {

    node("myJenkins") {

        @Library('github.com/redhatHameed/jenkins-library@master') _
        
        stage ('SCM checkout'){
            echo 'Checking out git repository'
            checkout scm
        }
    
        stage ('Maven build'){
            echo 'Building project'
            sh '''  
                    ls -last 
                    cd maingateway-service
                    mvn package
                '''
            
            
        }
    
        stage ('DEV - Image build'){
            echo 'Building docker image and deploying to Dev'
            buildApp('rh-dev', "maingateway-service")
            echo "This is the build number: ${env.BUILD_NUMBER}"
        }
    
        stage ('Automated tests'){
            echo 'This stage simulates automated tests'
            sh "mvn -B -Dmaven.test.failure.ignore test"
        }
    
        stage ('QA - Promote image'){
            echo 'Deploying to QA'
            promoteImage('helloworld-msa-dev', 'helloworld-msa-qa', 'aloha', 'latest')
        }
    
        stage ('Wait for approval'){
            input 'Approve to production?'
        }
    
        stage ('PRD - Promote image'){
            echo 'Deploying to production'
            promoteImage('helloworld-msa-qa', 'helloworld-msa', 'aloha', env.BUILD_NUMBER)
        }

        stage ('PRD - Canary Deploy'){
            echo 'Performing a canary deployment'
            canaryDeploy('helloworld-msa', 'aloha', env.BUILD_NUMBER)
        }

    }
}
