# Cisco DNAC

A project that leverages Ansible playbooks to retrieve device, user, and site details from the Cisco DNA Center (DNAC) website.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
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

Install the Python DNA Center SDK:

bash
Copy code
sudo pip install dnacentersdk

Install the Cisco DNAC Ansible collection:

bash
Copy code
ansible-galaxy collection install cisco.dnac

Folders
The project consists of the following three folders:

dnac_api
The dnac_api folder contains the necessary modules and utilities for interacting with the Cisco DNAC API. It provides a set of functions and classes that simplify the process of making API requests and handling responses. This folder serves as the core component of the project and acts as a bridge between the Cisco DNAC API and the other parts of the application.

playbooks
The playbooks folder contains a collection of Ansible playbooks that retrieve device, user, and site details from the Cisco DNAC website. Each playbook is accompanied by a README file that explains its purpose and provides usage instructions. These playbooks utilize the dnac_api module to interact with the Cisco DNAC API and retrieve the required information.

plugins
The plugins folder contains custom Ansible plugins written as Python functions. These plugins extend the functionality of Ansible by providing additional modules and features. The plugins in this folder are triggered from within the playbooks in the playbooks folder. They enhance the capabilities of Ansible by offering specialized functionality tailored to the Cisco DNAC API.
