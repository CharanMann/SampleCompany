# SampleCompany

*A Sample company application*

Disclaimer of Liability :
=========================
The sample code described herein is provided on an "as is" basis, without warranty of any kind, to the fullest extent permitted by law. ForgeRock does not warrant or guarantee the individual success developers may have in implementing the sample code on their development platforms or in production configurations.

ForgeRock does not warrant, guarantee or make any representations regarding the use, results of use, accuracy, timeliness or completeness of any data or information relating to the sample code. ForgeRock disclaims all warranties, expressed or implied, and in particular, disclaims all warranties of merchantability, and warranties related to the code, or any service or software related thereto.

ForgeRock shall not be liable for any direct, indirect or consequential damages or costs of any type arising out of any action taken by you or others related to the sample code.

Pre-requisites :
================
1. Install NPM and NodeJS. For Ubuntu refer these instructions (How To Install the Distro-Stable Version): https://www.digitalocean.com/community/tutorials/how-to-install-node-js-on-an-ubuntu-14-04-server
2. Verify that NPM and NodeJS has been installed successfully by running below commands: <br />
$ node -v <br />
v0.10.25 <br />
$ npm -v <br />
1.3.10 <br />  

SampleCompany installation :
===========================
1. Copy SampleCompany folder to /opt/forgerock/SampleCompany
2. Specify below local host enteries (both on server hosting and client accessing these applications): <br />
<IP Address>  employees.sc.com # Internal Employee App, Port:8002 <br />
<IP Address>  customers.sc.com  # External Customer App, Port:8004 <br />
<IP Address>  apis.sample.com # API server, Port:8010 <br />
<IP Address>  travel.sample.com  # Internal Travel App, Port:8012 <br />
<IP Address>  benefits.sample.com # Internal Benefits App, Port:8014 <br />
3. To start the services, execute /opt/forgerock/SampleCompany/services.sh start. Note that while starting these services for the first time you may need to execute 'services.sh start' twice as NPM needs to download the required dependencies.
4. To check the status of services, execute /opt/forgerock/SampleCompany/services.sh status
5. To stop the services, execute /opt/forgerock/SampleCompany/services.sh stop

SampleCompany URLs :
===========================
1. CommonServices direct: http://apis.sample.com:8010/history/emp1
2. EmployeeApp direct: http://employees.sc.com:8002/employeeApp/#/
3. CustomerApp direct: http://customers.sc.com:8004/customerApp/#/
4. TravelApp direct: http://travel.sample.com:8012/travelApp/#/
5. BenefitsApp direct: http://benefits.sample.com:8014/benefitsApp/#/

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
