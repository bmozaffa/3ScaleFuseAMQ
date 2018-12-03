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


def buildApp(project, app){
    projectSet(project)
    sh ''' 
    cd ${app}
    pwd
    oc new-build -n ${project} --binary --name=${app} -l app=${app} || echo 'Build exists'
    oc start-build ${app} -n ${project} --from-dir=. --follow
    
    ''' 
    deployApp(project, app, 'latest')
}

def projectSet(project) {
    sh "oc new-project ${project} || echo 'Project exists'"
    sh "oc project ${project}"
    sh "oc policy add-role-to-user admin developer -n ${project}"
}


def deployApp(project, app, tag){
    sh "oc new-app --name=${app} -n ${project} -l app=${app},hystrix.enabled=true --image-stream=${project}/${app}:${tag} || echo 'Aplication already Exists'"
    sh "oc expose service ${app} -n ${project} || echo 'Service already exposed'"
    sh "oc set probe dc/${app} -n ${project} --readiness --get-url=http://:8080/api/health"
}


def promoteImage(origProject, project, app, tag){
    projectSet(project)
    sh "oc policy add-role-to-user system:image-puller system:serviceaccount:${project}:default -n ${origProject}"
    sh "oc tag ${origProject}/${app}:latest ${project}/${app}:${tag}"
    deployApp(project, app, tag)
}

def canaryDeploy(project, app, version){
    sh "oc new-app -n ${project} --name ${app}-${version} ${app}:${version} -l app=${app},deploymentconfig=${app}-${version},hystrix.enabled=true"
    sh "oc set probe -n ${project} dc/${app}-${version} --readiness --get-url=http://:8080/api/health"
    sh "oc patch svc/${app} -n ${project} -p '{\"spec\":{\"selector\":{\"app\": \"${app}\", \"deploymentconfig\": null}, \"sessionAffinity\":\"None\"}}' || echo 'Service ${app} already patched'"
    sh "oc delete service ${app}-${version} -n ${project}"
}
