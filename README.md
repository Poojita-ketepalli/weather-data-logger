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

