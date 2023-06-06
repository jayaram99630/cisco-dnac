pipeline {
  agent any

  parameters {
    choice(name: 'List of Actions', choices: ['devices list', 'users list'], description: 'Select an action')
  }

  stages {
    stage('Clone Code') {
      steps {
        sshagent(['sample']) {
          sh '''
            ssh -o StrictHostKeyChecking=no ubuntu@13.53.52.161 "git clone https://github.com/jayaram99630/cisco-dnac.git"
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
          } else {
            error('Invalid choice')
          }

          sshagent(['sample']) {
            sh """
              ssh -o StrictHostKeyChecking=no ubuntu@13.53.52.161 "cd cisco-dnac && \
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
          ssh -o StrictHostKeyChecking=no ubuntu@13.53.52.161 "rm -rf /home/ubuntu/cisco-dnac"
        '''
      }
    }
  }
}

