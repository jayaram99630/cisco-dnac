pipeline {
  agent any

  parameters {
    choice(name: 'List of Actions', choices: ['devices list', 'users list', 'site list'], description: 'Select an action')
  }

  stages {
    stage('Clone Code') {
      steps {
        sshagent(['sample']) {
          sh '''
            ssh -o StrictHostKeyChecking=no ubuntu@16.170.233.169 "git clone https://github.com/jayaram99630/cisco-dnac.git"
          '''
        }
      }
    }

    stage('Execute Ansible Playbook') {
      when {
        expression {
          params['List of Actions'] != null
        }
      }
      steps {
        script {
          def playbook

          if (params['List of Actions'] == 'devices list') {
            playbook = 'devicedetails.yaml'
          } else if (params['List of Actions'] == 'users list') {
            playbook = 'dnacuserlist.yaml'
          } else if (params['List of Actions'] == 'site list') {
            playbook = 'sitedetails.yaml'  
          } else {
            error('Invalid choice')
          }

          sshagent(['sample']) {
            sh """
              ssh -o StrictHostKeyChecking=no ubuntu@16.170.233.169 "cd cisco-dnac && \
              export ANSIBLE_STDOUT_CALLBACK=debug && \
              ansible-playbook ${playbook}"
            """
          }
        }
      }
    }
  }
  post {
    always {
      sshagent(['sample']) {
        sh '''
          ssh -o StrictHostKeyChecking=no ubuntu@16.170.233.169 "rm -rf /home/ubuntu/cisco-dnac"
        '''
      }
    }
  }
}

