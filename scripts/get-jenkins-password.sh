#!/usr/bin/env bash


docker container exec -it air-quality-metrics_tqs-jenkins-blueocean_1 bash -c "cat /var/jenkins_home/secrets/initialAdminPassword"