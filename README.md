# SampleCompany

*A Sample company application*

Disclaimer of Liability :
=========================
The sample code described herein is provided on an "as is" basis, without warranty of any kind, to the fullest extent permitted by law. ForgeRock does not warrant or guarantee the individual success developers may have in implementing the sample code on their development platforms or in production configurations.

ForgeRock does not warrant, guarantee or make any representations regarding the use, results of use, accuracy, timeliness or completeness of any data or information relating to the sample code. ForgeRock disclaims all warranties, expressed or implied, and in particular, disclaims all warranties of merchantability, and warranties related to the code, or any service or software related thereto.

ForgeRock shall not be liable for any direct, indirect or consequential damages or costs of any type arising out of any action taken by you or others related to the sample code.

Pre-requisites :
================
1. Create Linux Server/VM with 4 CPU, 8 GB RAM and 50 GB hard drive. Create user 'forgerock', this user shall be used for all operations in this guide.
2. Install Java 1.7
3. Install Maven, For Ubuntu refer: http://www.mkyong.com/maven/how-to-install-maven-in-ubuntu/ <br /> For CentOS refer: http://preilly.me/2013/05/10/how-to-install-maven-on-centos/
4. Install NPM and NodeJS. For Ubuntu refer: https://www.digitalocean.com/community/tutorials/how-to-install-node-js-on-an-ubuntu-14-04-server <br />
For CentOS refer: http://tecadmin.net/install-latest-nodejs-and-npm-on-centos/#
5. Verify that NPM and NodeJS has been installed successfully by running below commands: <br />
$ node -v <br />
v0.10.25 <br />
$ npm -v <br />
1.3.10
6. Specify below local host enteries (both on server hosting and client accessing these applications): <br />
[IP Address]  employees.example.com # Internal Employee App, Port:8002 <br />
[IP Address]  customers.example.com  # External Customer App, Port:8004 <br />
[IP Address]  apis.example.net # API server, Port:8010 <br />
[IP Address]  travel.example.net  # Internal Travel App, Port:8012 <br />
[IP Address]  benefits.example.com # Internal Benefits App, Port:8014 <br />

SampleCompany installation :
===========================
1. Copy SampleCompany folder to /opt/SampleCompany
2. Install services:
  * To install the services, execute /opt/SampleCompany/services.sh install. Note that this is required only once; NPM downloads the required dependencies.
3. Start/ Stop services:
  * To start the services, execute /opt/SampleCompany/services.sh start.
  * To check the status of services, execute /opt/SampleCompany/services.sh status
  * To stop the services, execute /opt/SampleCompany/services.sh stop
  * To restart the services, execute /opt/SampleCompany/services.sh restart

SampleCompany URLs :
===========================
1. CommonServices (APIs) direct: http://apis.example.net:8010/history/emp1
2. EmployeeApp direct: http://employees.example.com:8002/employeeApp/#/
3. CustomerApp direct: http://customers.example.com:8004/customerApp/#/
4. TravelApp direct: http://travel.example.net:8012/travelApp/#/
5. BenefitsApp direct: http://benefits.example.com:8014/benefitsApp/#/

* * *

The contents of this file are subject to the terms of the Common Development and
Distribution License (the License). You may not use this file except in compliance with the
License.

You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
specific language governing permission and limitations under the License.

When distributing Covered Software, include this CDDL Header Notice in each file and include
the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
Header, with the fields enclosed by brackets [] replaced by your own identifying
information: "Portions copyright [year] [name of copyright owner]".

Copyright 2016 Charan Mann
