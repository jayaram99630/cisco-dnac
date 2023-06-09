# Cisco DNAC PoC done to investigate DNAC modules against sandboxdnac portal

#This project leverages Ansible playbooks to retrieve device, user, and site details from the Cisco DNA Center (DNAC) website.

## Table of Contents

- [Installation](#installation)
- [Folders](#folders)
    - [dnac_api](#dnac_api)
    - [playbooks](#playbooks)
    - [plugins](#plugins)
- [Contributing](#contributing)

## Installation

To get started with Cisco DNAC, follow these steps:

1. Install Ansible:

    ```bash
    sudo pip install ansible
    ```

2. Install the Python DNA Center SDK:

    ```bash
    sudo pip install dnacentersdk
    ```

3. Install the Cisco DNAC Ansible collection:

    ```bash
    ansible-galaxy collection install cisco.dnac
    ```

## Folders

The project consists of the following three folders:

### dnac_api

The `dnac_api` folder contains the necessary modules and utilities for interacting with the Cisco DNAC API. It provides a set of functions and classes that simplify the process of making API requests and handling responses. This folder serves as the core component of the project and acts as a bridge between the Cisco DNAC API and the other parts of the application.

### playbooks

The `playbooks` folder contains a collection of Ansible playbooks that retrieve device, user, and site details from the Cisco DNAC website. Each playbook is accompanied by a README file that explains its purpose and provides usage instructions. These playbooks utilize the `dnac_api` module to interact with the Cisco DNAC API and retrieve the required information.

First, define a `credentials.yml` ([example](https://github.com/cisco-en-programmability/dnacenter-ansible/blob/main/playbooks/credentials.template)) file where you specify your DNA Center credentials as Ansible variables:
```
---
dnac_host: <A.B.C.D>
dnac_port: 443  # optional, defaults to 443
dnac_username: <username>
dnac_password: <password>
dnac_version: 2.3.5.3  # optional, defaults to 2.3.5.3. See the Compatibility matrix
dnac_verify: False  # optional, defaults to True
dnac_debug: False  # optional, defaults to False
```

Create a `hosts` file that uses `[dnac_servers]` with your Cisco DNA Center Settings:
```
[dnac_servers]
dnac_server
```

Then, create a playbook `myplaybook.yml` referencing the variables in your credentials.yml file and specifying the full namespace path to the module, plugin and/or role:
```
- hosts: dnac_servers
  vars_files:
    - credentials.yml
  gather_facts: false
  tasks:
  - name: Create tag with name "MyNewTag"
    cisco.dnac.tag:
      dnac_host: "{{dnac_host}}"
      dnac_username: "{{dnac_username}}"
      dnac_password: "{{dnac_password}}"
      dnac_verify: "{{dnac_verify}}"
      state: present
      description: My Tag
      name: MyNewTag
    register: result
```

Execute the playbook:
```
ansible-playbook -i hosts myplaybook.yml
```

### plugins

The `plugins` folder contains custom Ansible plugins written as Python functions. These plugins extend the functionality of Ansible by providing additional modules and features. The plugins in this folder are triggered from within the playbooks in the `playbooks` folder. They enhance the capabilities of Ansible by offering specialized functionality tailored to the Cisco DNAC API.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.
