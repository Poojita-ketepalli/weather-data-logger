The process which I have followed:
- first developed the code
- Added ci workflow through github actions

Steps to setup jenkins through docker:
- docker pull jenkins/jenkins:lts
- docker run -d --name jenkins -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts
- docker exec -it jenkins cat /var/jenkins_home/secrets/initialAdminPassword
- you will get the password which can be used to signin

Steps to setup EC2 instance ubuntu AMI:
- create instance on ec2 server with linux os
- mkdir -p ~/.ssh
- cp /mnt/e/pws/file-name.pem ~/.ssh/
- chmod 600 ~/.ssh/file-name.pem
- ssh -i ~/.ssh/file-name.pem ubuntu@public-ipv4-address


Steps to set up docker on cloud:
- Update system : sudo apt update && sudo apt upgrade -y
- Install Docker : curl -fsSL https://get.docker.com | sh
- Start Docker service : sudo systemctl start docker
- Enable docker service : sudo systemctl enable docker
- Verify Docker : docker --version
- Install Docker Compose : sudo apt install docker-compose -y
- Verify Docker Compose : docker-compose --version

Deploy step in github actions:
- ssh-keygen -t rsa -b 4096
- cat id_rsa.pub - copy the output
- ssh -i ~/.ssh/file-from-ec2.pem user@public-ipv4
- echo "public key" >> ~/.ssh/authorized_keys
- exit
- ssh -i ~/.ssh/id_rsa user@public-ipv4 - verify weather we are able to successfully login to the server or not
- exit
- chmod 700 ~/.ssh
- chmod 600 ~/.ssh/id_rsa
- chmod 644 ~/.ssh/id_rsa.pub
- ssh -i ~/.ssh/id_rsa ubuntu@43.204.140.179 - verify again
- chmod 700 ~/.ssh
- chmod 600 ~/.ssh/authorized_keys
- exit
- cat id_rsa - copy that save in the github secret variables


output of jenkins:
![image](https://github.com/user-attachments/assets/a51ff708-6155-44f7-9644-5df8fd1ed367)

