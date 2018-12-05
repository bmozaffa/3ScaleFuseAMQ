def version, mvnCmd = "mvn"
      pipeline {
        agent {
          label 'maven'
        }
        stages {
          stage('Build App') {
            steps {
              git branch: 'master', url: 'https://github.com/redhatHameed/3ScaleFuseAMQ.git'
              script {
		  sh "pwd && ls -la maingateway-service"
                  def pom = readMavenPom file: 'maingateway-service/pom.xml'
                  version = pom.version
              }
              sh "cd maingateway-service && ${mvnCmd} package "
            }
          }
 
	stage('Test') {
            steps {
              sh "cd maingateway-service && ${mvnCmd} test"
            }
          }
         
         
          stage('Build Image') {
            steps {
              sh "cd maingateway-service && rm -rf ocp && mkdir -p ocp/deployments"
              sh "cd maingateway-service && pwd && ls -la target "
              sh "cd maingateway-service && cp target/maingateway-*.jar ocp/deployments"
	      sh "cd maingateway-service && oc new-build -n ahameed --binary --name=maingateway-service -l app=maingateway-service || echo 'Build exists'"
	      sh "cd maingateway-service && oc start-build ${app} -n ahameed --from-dir=/maingateway-service --follow"


              script {
                openshift.withCluster() {
                  openshift.withProject(env.DEV_PROJECT) {
                    openshift.selector("bc", "maingateway-service").startBuild("--from-dir=./maingateway-service/ocp","--follow==true", "--wait=true")
                  }
                }
              }
            }
          }
          stage('Create DEV') {
            when {
              expression {
                openshift.withCluster() {
                  openshift.withProject(env.DEV_PROJECT) {
                    return !openshift.selector('dc', 'maingateway-service').exists()
                  }
                }
              }
            }
            steps {
              script {
                openshift.withCluster() {
                  openshift.withProject(env.DEV_PROJECT) {
                    def app = openshift.newApp("maingateway-service:latest")
                    app.narrow("svc").expose();

                    //http://localhost:8080/actuator/health
                    openshift.set("probe dc/maingateway-service --readiness --get-url=http://:8080/actuator/health --initial-delay-seconds=30 --failure-threshold=10 --period-seconds=10")
                    openshift.set("probe dc/maingateway-service --liveness  --get-url=http://:8080/actuator/health --initial-delay-seconds=180 --failure-threshold=10 --period-seconds=10")

                    def dc = openshift.selector("dc", "maingateway-service")
                    while (dc.object().spec.replicas != dc.object().status.availableReplicas) {
                        sleep 10
                    }
                    openshift.set("triggers", "dc/maingateway-service", "--manual")
                  }
                }
              }
            }
          }
          stage('Deploy DEV') {
            steps {
              script {
                openshift.withCluster() {
                  openshift.withProject(env.DEV_PROJECT) {
                    openshift.selector("dc", "maingateway-service").rollout().latest();
                  }
                }
              }
            }
          }
          stage('Promote to STAGE?') {
            steps {
              script {
                openshift.withCluster() {
                  openshift.tag("${env.DEV_PROJECT}/bookstore:latest", "${env.STAGE_PROJECT}/bookstore:${version}")
                }
              }
            }
          }
          stage('Deploy STAGE') {
            steps {
              script {
                openshift.withCluster() {
                  openshift.withProject(env.STAGE_PROJECT) {
                    if (openshift.selector('dc', 'bookstore').exists()) {
                      openshift.selector('dc', 'bookstore').delete()
                      openshift.selector('svc', 'bookstore').delete()
                      openshift.selector('route', 'bookstore').delete()
                    }

                    openshift.newApp("bookstore:${version}").narrow("svc").expose()
                    openshift.set("probe dc/bookstore --readiness --get-url=http://:8080/actuator/health --initial-delay-seconds=30 --failure-threshold=10 --period-seconds=10")
                    openshift.set("probe dc/bookstore --liveness  --get-url=http://:8080/actuator/health --initial-delay-seconds=180 --failure-threshold=10 --period-seconds=10")
                  }
                }
              }
            }
          }
        }
      }
