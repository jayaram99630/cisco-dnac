pipeline {
  agent any
  
  environment {
    DNAC_CREDS = credentials('sample')
    }

  parameters {
    choice(name: 'List of Actions', choices: ['devices list', 'users list', 'site list'], description: 'Select an action')
  }

  stages {
    stage('Clone Code') {
      steps {
          sh """
            sshpass -p '$DNAC_CREDS_PSW' ssh -o StrictHostKeyChecking=no $DNAC_CREDS_USR@10.20.100.30 'git clone https://github.com/jayaram99630/cisco-dnac.git'
          """
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

          sshagent(['sample1']) {
            sh """
              sshpass -p '$DNAC_CREDS_PSW' ssh -o StrictHostKeyChecking=no $DNAC_CREDS_USR@10.20.100.30 ' source py3/bin/activate && 
              cd cisco-dnac && 
              export ANSIBLE_STDOUT_CALLBACK=debug && 
              ansible-playbook ${playbook}'
            """
          }
        }
      }
    }
  }
  post {
    always {
      sshagent(['sample1']) {
        sh """
          sshpass -p '$DNAC_CREDS_PSW' ssh -o StrictHostKeyChecking=no $DNAC_CREDS_USR@10.20.100.30 'rm -rf /home/devadmin/cisco-dnac'
        """
      }
    }
  }
}

